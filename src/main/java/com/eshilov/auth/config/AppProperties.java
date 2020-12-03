package com.eshilov.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String privateKeyPath;
    private String publicKeyPath;
    private Long accessTokenValiditySecs;
    private Long refreshTokenValiditySecs;
}
