package com.eshilov.auth;

import com.eshilov.auth.token.model.TokenType;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestTokenData {

    private final String subject;
    private final Date expiration;
    private final TokenType type;
}
