package com.eshilov.auth.key;

import java.security.KeyPair;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;

@RequiredArgsConstructor
public class KeyFactoryBean implements FactoryBean<KeyPair> {

    private final KeyLoader loader;

    @Override
    public KeyPair getObject() throws Exception {
        return loader.load();
    }

    @Override
    public Class<?> getObjectType() {
        return KeyPair.class;
    }
}
