package com.xinyuow.frame.common.controller;

import com.xinyuow.frame.common.exception.GlobalExceptionAdvisor;
import com.xinyuow.frame.common.exception.RESPONSE_CODE_ENUM;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * controller 基础控制器
 *
 * @author mxy
 * @date 2020/11/12
 */
@Slf4j
public class BaseController implements Serializable {
    private static final long serialVersionUID = 624841049563451448L;

    @Resource
    protected HttpServletRequest request;

    /**
     * 默认起始页
     */
    private static final long DEFAULT_PAGE_NUMBER = 1;

    /**
     * 默认每页记录数
     */
    private static final long DEFAULT_PAGE_SIZE = 10;

    /**
     * 获取分页对象
     *
     * @return 指定类型的分页对象
     */
    protected <T> Page<T> getPage() {
        return getPage(DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取分页对象
     *
     * @param initPageSize 执行每页记录数
     * @return 指定类型的分页对象
     */
    protected  <T> Page<T> getPage(long initPageSize) {
        // 初始化当前页数、每页最大数量
        long pageSize = initPageSize;
        long pageNumber = DEFAULT_PAGE_NUMBER;
        // 判断前端是否传入分页参数
        if (null != request.getParameter("pageSize")) {
            pageSize = Long.parseLong(request.getParameter("pageSize"));
        }
        if (null != request.getParameter("pageNumber")) {
            pageNumber = Long.parseLong(request.getParameter("pageNumber"));
        }
        return new Page<>(pageNumber, pageSize);
    }

    /**
     * 添加数据到结果对象中
     *
     * @param obj 封装接口集合参数
     * @return map
     */
    protected Map<String, Object> getResult(Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalExceptionAdvisor.RESPONSE_CODE, RESPONSE_CODE_ENUM.REQUEST_SUCCESS.getCode());
        map.put(GlobalExceptionAdvisor.RESPONSE_MSG, RESPONSE_CODE_ENUM.REQUEST_SUCCESS.getMsg());
        map.put(GlobalExceptionAdvisor.RESPONSE_BODY, obj);
        return map;
    }

    /**
     * 返回成功
     *
     * @return map
     */
    protected Map<String, Object> getSuccessResult() {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalExceptionAdvisor.RESPONSE_CODE, RESPONSE_CODE_ENUM.REQUEST_SUCCESS.getCode());
        map.put(GlobalExceptionAdvisor.RESPONSE_MSG, RESPONSE_CODE_ENUM.REQUEST_SUCCESS.getMsg());
        return map;
    }

    /**
     * 返回失败
     *
     * @param responseCodeEnum 特定状态码枚举
     * @return map
     */
    protected Map<String, Object> getFailResult(RESPONSE_CODE_ENUM responseCodeEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalExceptionAdvisor.RESPONSE_CODE, responseCodeEnum.getCode());
        map.put(GlobalExceptionAdvisor.RESPONSE_MSG, responseCodeEnum.getMsg());
        return map;
    }

    /**
     * 返回失败
     *
     * @param code 状态码
     * @param msg  失败原因
     * @return map
     */
    protected Map<String, Object> getFailResult(String code, String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalExceptionAdvisor.RESPONSE_CODE, code);
        map.put(GlobalExceptionAdvisor.RESPONSE_MSG, msg);
        return map;
    }

    /**
     * 返回失败
     *
     * @return map
     */
    protected Map<String, Object> getFailResult() {
        Map<String, Object> map = new HashMap<>();
        map.put(GlobalExceptionAdvisor.RESPONSE_CODE, RESPONSE_CODE_ENUM.SERVER_ERROR.getCode());
        map.put(GlobalExceptionAdvisor.RESPONSE_MSG, RESPONSE_CODE_ENUM.SERVER_ERROR.getMsg());
        return map;
    }
}
