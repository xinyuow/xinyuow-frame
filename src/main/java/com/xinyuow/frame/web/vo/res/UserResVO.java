/*
 * UserResVO.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.web.vo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Web端用户信息响应VO类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Web端用户信息的响应参数")
public class UserResVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5081311689804072349L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "登录名称")
    private String loginName;

    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "用户状态;0:启动 1:禁用")
    private Integer status;

    @Schema(description = "是否锁定;1:是 0:否")
    private Boolean lockFlag;

    @Schema(description = "创建时间", example = "2026-10-21 10:00:00")
    private LocalDateTime createDate;
}
