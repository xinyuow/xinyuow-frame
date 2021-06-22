package com.xinyuow.frame.VO.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 二维码登录-扫描认证响应VO类
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
@ApiModel(value = "二维码登录-扫描认证响应VO类", description = "二维码登录，扫描认证响应实体类")
public class QrCodeLoginScanAuthResVO implements Serializable {
    private static final long serialVersionUID = -555677053314971856L;

    /**
     * 临时Token
     */
    @JSONField(name = "temp_token")
    @ApiModelProperty("临时Token值")
    private String tempToken;
}
