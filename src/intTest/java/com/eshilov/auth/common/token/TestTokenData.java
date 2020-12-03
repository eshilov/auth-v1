package com.eshilov.auth.common.token;

import com.eshilov.auth.token.model.TokenType;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@Builder
public class TestTokenData {

    private final String subject;
    @With private final Date expiration;
    private final TokenType type;
}
