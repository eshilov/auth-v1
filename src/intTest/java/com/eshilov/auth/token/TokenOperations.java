package com.eshilov.auth.token;

import com.eshilov.auth.common.ApiOperations;
import com.eshilov.auth.generated.api.TokenApi;
import com.eshilov.auth.generated.model.RefreshTokenRequest;
import com.eshilov.auth.generated.model.TokenPair;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenOperations implements TokenApi {

    private final ApiOperations apiOperations;

    @Override
    public ResponseEntity<TokenPair> refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
        return apiOperations.postForResponseEntity(
                refreshTokenPath, refreshTokenRequest, TokenPair.class);
    }
}
