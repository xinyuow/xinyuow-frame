package com.xinyuow.frame.VO.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 二维码登录-再次确认授权请求VO类
 *
 * @author mxy
 * @date 2021/6/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "二维码登录-再次确认授权请求VO类", description = "二维码登录，再次确认授权请求实体类")
public class QrCodeLoginReconfirmReqVO implements Serializable {
    private static final long serialVersionUID = 441903320866195387L;

    /**
     * 临时Token
     */
    @ApiModelProperty("扫描认证接口返回的临时Token，用作确保扫描认证和再次确认是同一个账号")
    @NotBlank(message = "临时Token值不能为空")
    private String tempToken;

    /**
     * 确认标识，true为确认、false为取消
     */
    @ApiModelProperty("确认标识。True为确认，False为取消")
    @NotNull(message = "确认标识不能为空")
    private Boolean confirmFlag;
}
