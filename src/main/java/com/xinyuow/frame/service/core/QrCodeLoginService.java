package com.xinyuow.frame.service.core;

import com.xinyuow.frame.VO.request.QrCodeLoginReconfirmReqVO;
import com.xinyuow.frame.VO.request.QrCodeLoginScanAuthReqVO;
import com.xinyuow.frame.VO.response.QrCodeLoginScanAuthResVO;
import com.xinyuow.frame.VO.response.QrCodeLoginStatusResVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 二维码登录 业务接口类
 *
 * @author mxy
 * @date 2021/6/21
 */
public interface QrCodeLoginService {

    /**
     * 生成登录二维码
     *
     * @param userAgent 用户设备信息(用户信息)
     * @param response  响应参数
     * @throws IOException IO异常
     */
    void generateQrCode(String userAgent, HttpServletResponse response) throws IOException;

    /**
     * 获取二维码状态
     *
     * @param qrCodeId 登录二维码的ID
     * @return 操作结果
     */
    QrCodeLoginStatusResVO getQrCodeStatus(String qrCodeId);

    /**
     * 扫描认证
     *
     * @param qrCodeLoginScanAuth 扫描认证请求对象
     * @return 操作结果
     */
    QrCodeLoginScanAuthResVO scanAuth(QrCodeLoginScanAuthReqVO qrCodeLoginScanAuth);

    /**
     * 再次确认授权
     *
     * @param qrCodeLoginReconfirm 再次确认授权请求对象
     */
    void reconfirm(QrCodeLoginReconfirmReqVO qrCodeLoginReconfirm);
}
