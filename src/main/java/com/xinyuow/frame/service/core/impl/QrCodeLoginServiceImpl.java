package com.xinyuow.frame.service.core.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.alibaba.fastjson.JSON;
import com.xinyuow.frame.DTO.redis.QrCodeLoginDTO;
import com.xinyuow.frame.common.constant.InterfaceConstant;
import com.xinyuow.frame.common.enums.QrCodeStatusEnum;
import com.xinyuow.frame.service.core.QrCodeLoginService;
import lombok.extern.slf4j.Slf4j;
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
    }

}
