package com.eshilov.auth.token.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenPair {

    private final String access;
    private final String refresh;
}
