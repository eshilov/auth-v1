package com.eshilov.auth;

import java.security.KeyFactory;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfiguration {

    @Bean
    @SneakyThrows
    public KeyFactory keyFactory() {
        return KeyFactory.getInstance("RSA");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //noinspection deprecation
        return NoOpPasswordEncoder.getInstance();
    }
}
