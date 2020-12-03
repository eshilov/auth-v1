package com.eshilov.auth.tokens;

import static org.springframework.http.HttpStatus.OK;

import com.eshilov.auth.generated.api.TokensApi;
import com.eshilov.auth.generated.model.RefreshTokensRequest;
import com.eshilov.auth.generated.model.TokenPair;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController implements TokensApi {

    private final TokenService tokenService;

    @Override
    public ResponseEntity<TokenPair> refreshTokens(
            @Valid RefreshTokensRequest refreshTokensRequest) {
        var response = tokenService.refreshTokens(refreshTokensRequest);
        return ResponseEntity.status(OK).body(response);
    }
}
