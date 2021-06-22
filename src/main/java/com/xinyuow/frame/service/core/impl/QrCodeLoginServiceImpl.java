package com.xinyuow.frame.service.core.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.alibaba.fastjson.JSON;
import com.xinyuow.frame.DTO.redis.QrCodeLoginDTO;
import com.xinyuow.frame.VO.request.QrCodeLoginScanAuthReqVO;
import com.xinyuow.frame.VO.response.QrCodeLoginScanAuthResVO;
import com.xinyuow.frame.VO.response.QrCodeLoginStatusResVO;
import com.xinyuow.frame.common.constant.InterfaceConstant;
import com.xinyuow.frame.common.constant.ShiroConstant;
import com.xinyuow.frame.common.enums.QrCodeStatusEnum;
import com.xinyuow.frame.common.exception.InterfaceException;
import com.xinyuow.frame.common.exception.RESPONSE_CODE_ENUM;
import com.xinyuow.frame.service.core.QrCodeLoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * 二维码登录 业务实现类
 *
 * @author mxy
 * @date 2021/6/21
 */
@Slf4j
@Service
public class QrCodeLoginServiceImpl implements QrCodeLoginService {

    /**
     * 引入logo_icon，作为二维码的中心图标
     */
    @Value("classpath:static/icon/qr_code_login_center_icon.jpg")
    private File iconFile;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 生成登录二维码
     *
     * @param userAgent 用户设备信息(用户信息)
     * @param response  响应参数
     * @throws IOException IO异常
     */
    @Override
    public void generateQrCode(String userAgent, HttpServletResponse response) throws IOException {
        // 初始化二维码信息
        QrCodeLoginDTO qrCodeLoginDTO = QrCodeLoginDTO.builder()
                .uuid(UUID.randomUUID().toString())
                .userAgent(userAgent)
                .qrCodeStatus(QrCodeStatusEnum.CREATED.name())
                .build();

        // 设置禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 将图片输出给浏览器
        BufferedImage image = QrCodeUtil.generate(qrCodeLoginDTO.getUuid(),
                QrConfig.create().setWidth(InterfaceConstant.LOGIN_INFO_QR_CODE_WIDTH)
                        .setHeight(InterfaceConstant.LOGIN_INFO_QR_CODE_HEIGHT).setImg(iconFile));
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);

        // 将二维码信息存入到Redis里
        redisTemplate.opsForValue().set(InterfaceConstant.LOGIN_INFO_QR_CODE_REDIS_KEY_NAME + qrCodeLoginDTO.getUuid(),
                JSON.toJSONString(qrCodeLoginDTO), InterfaceConstant.LOGIN_INFO_QR_CODE_REDIS_EXPIRE, TimeUnit.SECONDS);
        // 将二维码ID存在Redis里，方便获取二维码状态是判断二维码ID是否有效
        redisTemplate.opsForSet().add(InterfaceConstant.LOGIN_INFO_QR_CODE_ID_SET_REDIS_KEY_NAME, qrCodeLoginDTO.getUuid());
    }

    /**
     * 获取二维码状态
     *
     * @param qrCodeId 登录二维码的ID
     * @return 操作结果
     */
    @Override
    public QrCodeLoginStatusResVO getQrCodeStatus(String qrCodeId) {
        // 初始化返回结果
        QrCodeLoginStatusResVO result = QrCodeLoginStatusResVO.builder().uuid(qrCodeId).build();
        // 判断二维码ID是否存在
        Boolean existFlag = redisTemplate.opsForSet().isMember(InterfaceConstant.LOGIN_INFO_QR_CODE_ID_SET_REDIS_KEY_NAME, qrCodeId);
        if (null == existFlag || !existFlag) {
            result.setQrCodeStatus(QrCodeStatusEnum.INVALID.name());
            return result;
        }
        // 判断二维码ID是否过期
        Boolean validFlag = redisTemplate.hasKey(InterfaceConstant.LOGIN_INFO_QR_CODE_REDIS_KEY_NAME + qrCodeId);
        if (null == validFlag || !validFlag) {
            // 二维码已经过期，将其从二维码ID集合中移除
            redisTemplate.opsForSet().remove(InterfaceConstant.LOGIN_INFO_QR_CODE_ID_SET_REDIS_KEY_NAME, qrCodeId);
            result.setQrCodeStatus(QrCodeStatusEnum.TIMED_OUT.name());
            return result;
        }
        // 从Redis中获取二维码信息
        QrCodeLoginDTO qrCodeLoginDTO = (QrCodeLoginDTO) redisTemplate.opsForValue().get(InterfaceConstant.LOGIN_INFO_QR_CODE_REDIS_KEY_NAME + qrCodeId);
        if (null == qrCodeLoginDTO) {
            // 二维码信息不存在，将其从二维码ID集合中移除
            redisTemplate.opsForSet().remove(InterfaceConstant.LOGIN_INFO_QR_CODE_ID_SET_REDIS_KEY_NAME, qrCodeId);
            throw new InterfaceException(RESPONSE_CODE_ENUM.QR_CODE_IS_TIMED_OUT);
        }
        // 判断二维码的状态是否为已确认
        if (StringUtils.equals(QrCodeStatusEnum.CONFIRMED.name(), qrCodeLoginDTO.getQrCodeStatus())) {
            if (StringUtils.isBlank(qrCodeLoginDTO.getRealToken())) {
                throw new InterfaceException(RESPONSE_CODE_ENUM.QR_CODE_LOGIN_ERROR);
            }
            result.setToken(qrCodeLoginDTO.getRealToken());
            // 删除二维码信息
            redisTemplate.delete(InterfaceConstant.LOGIN_INFO_QR_CODE_REDIS_KEY_NAME + qrCodeId);
            // 删除二维码ID
            redisTemplate.opsForSet().remove(InterfaceConstant.LOGIN_INFO_QR_CODE_ID_SET_REDIS_KEY_NAME, qrCodeId);
        }
        result.setQrCodeStatus(qrCodeLoginDTO.getQrCodeStatus());
        return result;
    }

    /**
     * 扫描认证
     *
     * @param qrCodeLoginScanAuth 扫描认证请求对象
     * @return 操作结果
     */
    @Override
    public QrCodeLoginScanAuthResVO scanAuth(QrCodeLoginScanAuthReqVO qrCodeLoginScanAuth) {
        // 判断移动端的Token是否有效
        Boolean existTokenFlag = redisTemplate.hasKey(ShiroConstant.SHIRO_REDIS_SESSION_KEY_NAME + qrCodeLoginScanAuth.getToken());
        if (null == existTokenFlag || !existTokenFlag) {
            throw new InterfaceException(RESPONSE_CODE_ENUM.ACCOUNT_AUTHENTICATION_FAILED);
        }
        // 判断二维码ID是否存在
        Boolean existQrCodeFlag = redisTemplate.opsForSet().isMember(InterfaceConstant.LOGIN_INFO_QR_CODE_ID_SET_REDIS_KEY_NAME, qrCodeLoginScanAuth.getQrCodeId());
        if (null == existQrCodeFlag || !existQrCodeFlag) {
            throw new InterfaceException(RESPONSE_CODE_ENUM.QR_CODE_IS_INVALID);
        }
        // 判断二维码ID是否过期
        Boolean validQrCodeFlag = redisTemplate.hasKey(InterfaceConstant.LOGIN_INFO_QR_CODE_REDIS_KEY_NAME + qrCodeLoginScanAuth.getQrCodeId());
        if (null == validQrCodeFlag || !validQrCodeFlag) {
            // 二维码已经过期，将其从二维码ID集合中移除
            redisTemplate.opsForSet().remove(InterfaceConstant.LOGIN_INFO_QR_CODE_ID_SET_REDIS_KEY_NAME, qrCodeLoginScanAuth.getQrCodeId());
            throw new InterfaceException(RESPONSE_CODE_ENUM.QR_CODE_IS_TIMED_OUT);
        }
        // 从Redis中获取二维码信息
        QrCodeLoginDTO qrCodeLoginDTO = (QrCodeLoginDTO) redisTemplate.opsForValue().get(InterfaceConstant.LOGIN_INFO_QR_CODE_REDIS_KEY_NAME + qrCodeLoginScanAuth.getQrCodeId());
        if (null == qrCodeLoginDTO) {
            // 二维码信息不存在，将其从二维码ID集合中移除
            redisTemplate.opsForSet().remove(InterfaceConstant.LOGIN_INFO_QR_CODE_ID_SET_REDIS_KEY_NAME, qrCodeLoginScanAuth.getQrCodeId());
            throw new InterfaceException(RESPONSE_CODE_ENUM.QR_CODE_IS_TIMED_OUT);
        }
        // 重置二维码登录信息
        qrCodeLoginDTO.setQrCodeStatus(QrCodeStatusEnum.SCANNED.name());
        qrCodeLoginDTO.setRealToken(qrCodeLoginScanAuth.getToken());
        qrCodeLoginDTO.setTempToken(UUID.randomUUID().toString());
        // 更新Redis里的二维码登录信息
        redisTemplate.opsForValue().set(InterfaceConstant.LOGIN_INFO_QR_CODE_REDIS_KEY_NAME + qrCodeLoginDTO.getUuid(),
                JSON.toJSONString(qrCodeLoginDTO), InterfaceConstant.LOGIN_INFO_QR_CODE_REDIS_EXPIRE, TimeUnit.SECONDS);
        // 返回结果
        return QrCodeLoginScanAuthResVO.builder().tempToken(qrCodeLoginDTO.getTempToken()).build();
    }

}
