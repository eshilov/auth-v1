package com.eshilov.auth.key;

import java.security.Key;

public interface KeyService {

    Key getPrivateKey();

    Key getPublicKey();
}
