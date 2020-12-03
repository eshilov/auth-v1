package com.eshilov.auth.tokens.operations.create;

import static com.eshilov.auth.tokens.model.TokenConstants.TYPE_CLAIM_NAME;
import static com.eshilov.auth.tokens.model.TokenType.ACCESS;
import static com.eshilov.auth.tokens.model.TokenType.REFRESH;
import static com.eshilov.auth.utils.DateUtils.convertLocalDateTimeToDate;
import static java.time.LocalDateTime.now;

import com.eshilov.auth.config.AppProperties;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.keys.KeyService;
import com.eshilov.auth.tokens.model.TokenType;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTokensOperation {

    private final KeyService keyService;
    private final AppProperties appProperties;

    public TokenPair execute(CreateTokensRequest request) {
        String subject = request.getSubject();
        return TokenPair.builder()
                .access(createAccessToken(subject))
                .refresh(createRefreshToken(subject))
                .build();
    }

    private String createAccessToken(String subject) {
        return createToken(subject, ACCESS, appProperties::getAccessTokenValiditySecs);
    }

    private String createRefreshToken(String subject) {
        return createToken(subject, REFRESH, appProperties::getRefreshTokenValiditySecs);
    }

    private String createToken(
            String subject, TokenType type, Supplier<Long> validitySecsSupplier) {

        return Jwts.builder()
                .claim(TYPE_CLAIM_NAME, type.name())
                .setSubject(subject)
                .setExpiration(getTokenExpiration(validitySecsSupplier))
                .signWith(getSigningKey())
                .compact();
    }

    private Date getTokenExpiration(Supplier<Long> validitySecsSupplier) {
        var validitySecs = validitySecsSupplier.get();
        var expiration = now().plusSeconds(validitySecs);
        return convertLocalDateTimeToDate(expiration);
    }

    private Key getSigningKey() {
        return keyService.getPrivateKey();
    }
}
