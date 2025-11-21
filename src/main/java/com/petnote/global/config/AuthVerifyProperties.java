package com.petnote.global.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.verify")
@Data
public class AuthVerifyProperties {
    private int codeTtlMin;
    private int finalTokenTtlMin;
}
