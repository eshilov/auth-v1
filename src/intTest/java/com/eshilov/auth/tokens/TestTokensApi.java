package com.eshilov.auth.tokens;

import com.eshilov.auth.generated.api.TokensApi;
import com.eshilov.auth.generated.model.RefreshTokensRequest;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.testhelp.ApiOperations;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestTokensApi implements TokensApi {

    private final ApiOperations apiOperations;

    @Override
    public ResponseEntity<TokenPair> refreshTokens(
            @Valid RefreshTokensRequest refreshTokensRequest) {
        return apiOperations.postForResponseEntity(
                refreshTokensPath, refreshTokensRequest, TokenPair.class);
    }
}
