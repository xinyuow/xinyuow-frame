package com.xinyuow.frame.VO.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 登录二维码状态响应VO类
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
@ApiModel(value = "登录二维码状态响应VO类", description = "登录二维码状态的响应实体类")
public class QrCodeLoginStatusResVO implements Serializable {
    private static final long serialVersionUID = -7743047114194722921L;

    /**
     * 二维码ID
     */
    @JSONField(name = "id")
    @ApiModelProperty("二维码的ID")
    private String uuid;

    /**
     * 登录后的Token
     */
    @JSONField(name = "token")
    @ApiModelProperty("如果二维码的状态为CONFIRMED(已确认)，则该值存在。其他状态该值为空")
    private String token;

    /**
     * 二维码状态
     * INVALID：已无效
     * SCANNED：已扫描
     * CONFIRMED：已确认
     * TIMED_OUT：已超时
     */
    @JSONField(name = "qr_code_status")
    @ApiModelProperty("二维码状态：INVALID(无效)、SCANNED(已扫描)、CONFIRMED(已确认)、TIMED_OUT(已超时)")
    private String qrCodeStatus;
}
