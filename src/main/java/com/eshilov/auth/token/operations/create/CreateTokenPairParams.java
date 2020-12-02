package com.eshilov.auth.token.operations.create;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateTokenPairParams {

    private final String subject;
}
