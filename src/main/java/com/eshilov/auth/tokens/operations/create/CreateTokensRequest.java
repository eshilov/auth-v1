package com.eshilov.auth.tokens.operations.create;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateTokensRequest {

    private final String subject;
}
