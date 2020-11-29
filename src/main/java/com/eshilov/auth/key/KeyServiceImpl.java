package com.eshilov.auth.key;

import java.security.Key;
import java.security.KeyPair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeyServiceImpl implements KeyService {

    private final KeyPair keyPair;

    @Override
    public Key getPrivateKey() {
        return keyPair.getPrivate();
    }

    @Override
    public Key getPublicKey() {
        return keyPair.getPublic();
    }
}
