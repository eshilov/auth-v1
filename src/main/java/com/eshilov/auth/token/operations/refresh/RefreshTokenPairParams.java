package com.eshilov.auth.token.operations.refresh;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RefreshTokenPairParams {

    private final String refreshToken;
}
