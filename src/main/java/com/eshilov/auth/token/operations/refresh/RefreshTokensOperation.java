package com.eshilov.auth.token.operations.refresh;

import static com.eshilov.auth.token.model.TokenConstants.TYPE_CLAIM_NAME;
import static com.eshilov.auth.token.model.TokenType.REFRESH;
import static com.eshilov.auth.utils.ExceptionUtils.validationExceptionSupplier;

import com.eshilov.auth.generated.model.RefreshTokensRequest;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.key.KeyService;
import com.eshilov.auth.token.operations.create.CreateTokensOperation;
import com.eshilov.auth.token.operations.create.CreateTokensRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class RefreshTokensOperation {

    private final KeyService keyService;
    private final CreateTokensOperation createTokensOperation;

    public TokenPair execute(RefreshTokensRequest request) {
        var subject = extractSubjectFromRefreshToken(request.getRefreshToken());
        return createTokensForSubject(subject);
    }

    private String extractSubjectFromRefreshToken(String refreshToken) {
        var tokenClaims = parseRefreshTokenSafely(refreshToken);
        return tokenClaims.getSubject();
    }

    private Claims parseRefreshTokenSafely(String refreshToken) {
        try {
            return parseRefreshToken(refreshToken);
        } catch (RuntimeException ex) {
            log.warn("Token parsing resulted in an error", ex);
            throw validationExceptionSupplier("Bad refresh token provided").get();
        }
    }

    private Claims parseRefreshToken(String refreshToken) {
        return Jwts.parserBuilder()
                .require(TYPE_CLAIM_NAME, REFRESH.name())
                .setSigningKey(keyService.getPublicKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
    }

    private TokenPair createTokensForSubject(String subject) {
        var createParams = CreateTokensRequest.builder().subject(subject).build();
        return createTokensOperation.execute(createParams);
    }
}
