/*
 * UserListQueryReqVO.java
 * Copyright © 2026 xinyuow
 * All rights reserved.
 * --------------------------------------------------------
 * Created by xinyuow on 2026-10-19
 */
package com.xinyuow.frame.web.vo.req;

import com.xinyuow.frame.common.model.PageReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 用户列表查询请求VO类
 *
 * @author xinyuow
 * @date 2026-10-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户列表查询请求参数")
public class UserListQueryReqVO extends PageReqVO {
    @Serial
    private static final long serialVersionUID = -7093364777405258612L;

    @Size(max = 50, message = "用户名称不能超过50个字符")
    @Schema(name = "用户名称", description = "用户名称")
    private String username;
}
