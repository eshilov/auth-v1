package com.eshilov.auth;

import java.security.KeyFactory;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    @SneakyThrows
    public KeyFactory keyFactory() {
        return KeyFactory.getInstance("RSA");
    }
}
