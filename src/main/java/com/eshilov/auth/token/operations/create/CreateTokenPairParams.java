package com.eshilov.auth.token.operations.create;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateTokenPairParams {

    private final String subject;
}
