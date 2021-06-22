package com.xinyuow.frame.DTO.redis;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 二维码登录DTO类
 *
 * @author mxy
 * @date 2021/6/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class QrCodeLoginDTO implements Serializable {
    private static final long serialVersionUID = -8360663729368776058L;

    /**
     * 二维码ID
     */
    @JSONField(name = "id")
    private String uuid;

    /**
     * 用户信息(设备信息)
     */
    @JSONField(name = "user_agent")
    private String userAgent;

    /**
     * 临时Token
     */
    @JSONField(name = "temp_token")
    private String tempToken;

    /**
     * 二维码状态
     * CREATED：已创建
     * SCANNED：已扫描
     * CONFIRMED：已确认
     */
    @JSONField(name = "qr_code_status")
    private String qrCodeStatus;

    /**
     * 用户的真实Token
     */
    @JSONField(name = "qr_code_status")
    private String realToken;
}
