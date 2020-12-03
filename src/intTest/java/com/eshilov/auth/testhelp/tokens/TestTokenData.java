package com.eshilov.auth.testhelp.tokens;

import com.eshilov.auth.tokens.model.TokenType;
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
