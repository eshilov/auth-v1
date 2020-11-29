package com.eshilov.auth.token.operations.create;

import static com.eshilov.auth.token.model.TokenType.ACCESS;
import static com.eshilov.auth.token.model.TokenType.REFRESH;
import static com.eshilov.utils.DateUtils.convertLocalDateTimeToDate;
import static java.time.LocalDateTime.now;

import com.eshilov.auth.AppProperties;
import com.eshilov.auth.key.KeyService;
import com.eshilov.auth.token.model.TokenPair;
import com.eshilov.auth.token.model.TokenType;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.function.Supplier;
import lombok.Builder;

@Builder
public class CreateTokenPairOperation {

    private static final String TYPE_CLAIM_NAME = "typ";

    private final KeyService keyService;
    private final AppProperties appProperties;

    private final CreateTokenPairParams params;

    public TokenPair execute() {
        return TokenPair.builder()
                .access(createAccessToken())
                .refresh(createRefreshToken())
                .build();
    }

    private String createAccessToken() {
        return createToken(ACCESS, appProperties::getAccessTokenValiditySecs);
    }

    private String createRefreshToken() {
        return createToken(REFRESH, appProperties::getRefreshTokenValiditySecs);
    }

    private Key getKey() {
        return keyService.getPrivateKey();
    }

    private String createToken(TokenType type, Supplier<Long> validitySecsSupplier) {
        return Jwts.builder()
                .claim(TYPE_CLAIM_NAME, type)
                .setSubject(params.getSubject())
                .setExpiration(getTokenExpiration(validitySecsSupplier))
                .signWith(getKey())
                .compact();
    }

    private Date getTokenExpiration(Supplier<Long> validitySecsSupplier) {
        var validitySecs = validitySecsSupplier.get();
        var expiration = now().plusSeconds(validitySecs);
        return convertLocalDateTimeToDate(expiration);
    }
}
