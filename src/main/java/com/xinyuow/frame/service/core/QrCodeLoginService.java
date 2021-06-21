package com.xinyuow.frame.service.core;

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

}
