package com.xinyuow.frame.controller.core;

import com.xinyuow.frame.VO.request.QrCodeLoginReconfirmReqVO;
import com.xinyuow.frame.VO.request.QrCodeLoginScanAuthReqVO;
import com.xinyuow.frame.common.controller.BaseController;
import com.xinyuow.frame.common.exception.InterfaceException;
import com.xinyuow.frame.common.exception.RESPONSE_CODE_ENUM;
import com.xinyuow.frame.service.core.QrCodeLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
     * 生成登录二维码
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

    /**
     * 获取二维码状态
     *
     * @param qrCodeId 登录二维码的ID
     * @return 操作结果
     */
    @ApiOperation(value = "获取登录二维码状态", notes = "获取登录二维码状态接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qrCodeId", value = "登录二维码的ID", dataType = "String", paramType = "query", required = true)
    })
    @PostMapping(value = "/get_status")
    public Map<String, Object> getQrCodeStatus(String qrCodeId) {
        if (StringUtils.isBlank(qrCodeId)) {
            throw new InterfaceException(RESPONSE_CODE_ENUM.REQUEST_PARAMS_ERROR);
        }
        return getResult(qrCodeLoginService.getQrCodeStatus(qrCodeId));
    }

    /**
     * 扫描认证
     *
     * @param qrCodeLoginScanAuthVO 扫描认证请求对象
     * @return 操作结果
     */
    @ApiOperation(value = "扫描认证", notes = "扫描二维码，认证身份接口")
    @PostMapping(value = "/scan_auth")
    public Map<String, Object> scanAuth(@Valid @RequestBody QrCodeLoginScanAuthReqVO qrCodeLoginScanAuthVO) {
        return getResult(qrCodeLoginService.scanAuth(qrCodeLoginScanAuthVO));
    }

    /**
     * 再次确认授权
     *
     * @param qrCodeLoginReconfirmReqVO 再次确认授权请求对象
     * @return 操作结果
     */
    @ApiOperation(value = "再次确认授权", notes = "再次确认(确认登录/取消登录)授权接口")
    @PostMapping(value = "/reconfirm")
    public Map<String, Object> reconfirm(@Valid @RequestBody QrCodeLoginReconfirmReqVO qrCodeLoginReconfirmReqVO) {
        qrCodeLoginService.reconfirm(qrCodeLoginReconfirmReqVO);
        return getSuccessResult();
    }
}
