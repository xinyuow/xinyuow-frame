package com.xinyuow.frame.controller;

import com.xinyuow.frame.common.controller.BaseController;
import com.xinyuow.frame.common.exception.RESPONSE_CODE_ENUM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 默认路径控制器
 *
 * @author mxy
 * @date 2020/11/12
 */
@Slf4j
@ApiIgnore()
@RestController
public class IndexController extends BaseController {
    private static final long serialVersionUID = 5658468340718803334L;

    /**
     * 默认路径接口
     *
     * @return 访问默认路径，返回响应信息
     */
    @RequestMapping("/")
    public Map<String, Object> index() {
        return getFailResult(RESPONSE_CODE_ENUM.REQUEST_ADDRESS_ERROR);
    }

}
