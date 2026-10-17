/*
 * WebBaseController.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-17
 */
package com.xinyuow.frame.common.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;

import java.io.Serial;

/**
 * Web端基础控制器
 *
 * @author xinyuow
 * @date 2026-10-17
 */
public abstract class WebBaseController extends BaseController {
    @Serial
    private static final long serialVersionUID = 624841049563451448L;

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
     * @param initPageSize 默认每页记录数
     * @return 指定类型的分页对象
     */
    protected <T> Page<T> getPage(long initPageSize) {
        long pageSize = initPageSize;
        long pageNumber = DEFAULT_PAGE_NUMBER;

        String pageSizeParameter = request.getParameter("pageSize");
        if (pageSizeParameter != null) {
            pageSize = Long.parseLong(pageSizeParameter);
        }

        String pageNumberParameter = request.getParameter("pageNumber");
        if (pageNumberParameter != null) {
            pageNumber = Long.parseLong(pageNumberParameter);
        }

        return new Page<>(pageNumber, pageSize);
    }
}
