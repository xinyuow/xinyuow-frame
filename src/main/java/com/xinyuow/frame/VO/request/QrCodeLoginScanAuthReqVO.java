package com.xinyuow.frame.VO.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 二维码登录-扫描认证请求VO类
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
@ApiModel(value = "二维码登录-扫描认证请求VO类", description = "二维码登录，扫描认证请求实体类")
public class QrCodeLoginScanAuthReqVO implements Serializable {
    private static final long serialVersionUID = 8916686104832328536L;

    /**
     * 移动端存储的有效Token值，用作移动端的身份认证
     */
    @ApiModelProperty("移动端存储的有效Token，用作身份认证")
    @NotBlank(message = "Token值不能为空")
    private String token;

    /**
     * 二维码的ID
     */
    @JsonProperty("qr_code_id")
    @ApiModelProperty("二维码的ID")
    @NotBlank(message = "二维码的ID不能为空")
    private String qrCodeId;
}
