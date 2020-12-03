package com.eshilov.auth.keys;

import java.security.Key;

public interface KeyService {

    Key getPrivateKey();

    Key getPublicKey();
}
