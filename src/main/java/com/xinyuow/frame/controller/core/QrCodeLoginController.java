package com.xinyuow.frame.controller.core;

import com.xinyuow.frame.common.controller.BaseController;
import com.xinyuow.frame.service.core.QrCodeLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 二维码登录 控制层
 *
 * @author mxy
 * @date 2021/6/21
 */
@Api(tags = {"二维码登录操作接口"})
@RestController
@RequestMapping("/api/qr_code/")
public class QrCodeLoginController extends BaseController {
    private static final long serialVersionUID = -6701494121200193183L;

    @Resource
    private QrCodeLoginService qrCodeLoginService;

    /**
     * 生成登录二维码接口
     *
     * @param request  请求参数
     * @param response 响应参数
     * @throws IOException IO异常
     */
    @ApiOperation(value = "生成登录二维码", notes = "生成登录二维码接口")
    @PostMapping(value = "/generate")
    public void generateQrCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取请求的PC端信息
        String userAgent = request.getHeader("User-Agent");
        // 生成登录二维码
        qrCodeLoginService.generateQrCode(userAgent, response);
    }

    /*
        TODO：2.轮询获取二维码状态
                入参：二维码ID
                出参：二维码状态（初始、已被扫描、已被确认）、PC端使用的Token
     */

    /*
        TODO：3.认证接口
                入参：手机端存储的身份信息、二维码ID
                出参：临时的Token
     */

    /*
        TODO：4.确认接口
                入参：临时Token、确认/取消
                出参：无
     */
}
