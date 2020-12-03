package com.eshilov.auth.token.operations.refresh;

import static com.eshilov.auth.token.model.TokenConstants.TYPE_CLAIM_NAME;
import static com.eshilov.auth.token.model.TokenType.REFRESH;
import static com.eshilov.auth.utils.ExceptionUtils.validationExceptionSupplier;

import com.eshilov.auth.generated.model.RefreshTokenRequest;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.key.KeyService;
import com.eshilov.auth.token.operations.create.CreateTokenPairOperationExecutor;
import com.eshilov.auth.token.operations.create.CreateTokenPairParams;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Builder
@RequiredArgsConstructor
public class RefreshTokenPairOperation {

    private final KeyService keyService;
    private final CreateTokenPairOperationExecutor createTokenPairOperationExecutor;

    private final RefreshTokenRequest params;

    public TokenPair execute() {
        var subject = parseRefreshTokenAndExtractSubject();
        return createNewTokenPairForSubject(subject);
    }

    private String parseRefreshTokenAndExtractSubject() {
        try {
            return Jwts.parserBuilder()
                    .require(TYPE_CLAIM_NAME, REFRESH.name())
                    .setSigningKey(keyService.getPublicKey())
                    .build()
                    .parseClaimsJws(params.getToken())
                    .getBody()
                    .getSubject();
        } catch (RuntimeException ex) {
            log.error(ex);
            throw validationExceptionSupplier("Bad refresh token provided").get();
        }
    }

    private TokenPair createNewTokenPairForSubject(String subject) {
        var createParams = CreateTokenPairParams.builder().subject(subject).build();
        return createTokenPairOperationExecutor.execute(createParams);
    }
}
