package com.atguigu.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ldy
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "ali.pay")
public class PayProperties {

    private String appId;
    private String alipayPublicKey;
    private String charset;
    private String gatewayUrl;
    private String merchantPrivateKey;
    private String notifyUrl;
    private String returnUrl;
    private String signType;

}
