package com.eshilov.auth.testhelp.tokens;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class TestKeyFactory implements FactoryBean<KeyPair> {

    private final KeyPair keyPair;

    @SneakyThrows
    public TestKeyFactory() {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);
        keyPair = keyGenerator.generateKeyPair();
    }

    @Override
    public KeyPair getObject() {
        return keyPair;
    }

    @Override
    public Class<?> getObjectType() {
        return KeyPair.class;
    }
}
