package com.eshilov.auth.token.operations.refresh;

import static com.eshilov.auth.token.model.TokenConstants.TYPE_CLAIM_NAME;
import static com.eshilov.auth.token.model.TokenType.REFRESH;

import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.key.KeyService;
import com.eshilov.auth.token.operations.create.CreateTokenPairOperationExecutor;
import com.eshilov.auth.token.operations.create.CreateTokenPairParams;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class RefreshTokenPairOperation {

    private final KeyService keyService;
    private final CreateTokenPairOperationExecutor createTokenPairOperationExecutor;

    private final RefreshTokenPairParams params;

    public TokenPair execute() {
        var subject = parseRefreshTokenAndExtractSubject();
        return createNewTokenPairForSubject(subject);
    }

    private String parseRefreshTokenAndExtractSubject() {
        return Jwts.parserBuilder()
                .require(TYPE_CLAIM_NAME, REFRESH)
                .setSigningKey(keyService.getPublicKey())
                .build()
                .parseClaimsJws(params.getRefreshToken())
                .getBody()
                .getSubject();
    }

    private TokenPair createNewTokenPairForSubject(String subject) {
        var createParams = new CreateTokenPairParams(subject);
        return createTokenPairOperationExecutor.execute(createParams);
    }
}
