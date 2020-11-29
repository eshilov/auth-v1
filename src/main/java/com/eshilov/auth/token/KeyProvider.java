package com.eshilov.auth.token;

import java.security.KeyPair;

public interface KeyProvider {

    KeyPair getKeys();
}
