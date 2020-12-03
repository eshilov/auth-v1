package com.eshilov.auth;

import static com.eshilov.auth.token.model.TokenConstants.TYPE_CLAIM_NAME;

import com.eshilov.auth.token.model.TokenType;
import io.jsonwebtoken.Jwts;
import java.security.KeyPair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestTokenHelper {

    private final KeyPair keyPair;

    public String createToken(TestTokenData testTokenData) {
        return Jwts.builder()
                .setSubject(testTokenData.getSubject())
                .setExpiration(testTokenData.getExpiration())
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public TestTokenData parseToken(String token) {
        var jwt =
                Jwts.parserBuilder()
                        .setSigningKey(keyPair.getPublic())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

        return TestTokenData.builder()
                .subject(jwt.getSubject())
                .expiration(jwt.getExpiration())
                .type(TokenType.valueOf(jwt.get(TYPE_CLAIM_NAME, String.class)))
                .build();
    }
}
