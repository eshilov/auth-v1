package com.eshilov.auth.token;

import static org.springframework.http.HttpStatus.OK;

import com.eshilov.auth.generated.api.TokenApi;
import com.eshilov.auth.generated.model.RefreshTokenRequest;
import com.eshilov.auth.generated.model.TokenPair;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController implements TokenApi {

    private final TokenService tokenService;

    @Override
    public ResponseEntity<TokenPair> refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
        var response = tokenService.refreshTokenPair(refreshTokenRequest);
        return ResponseEntity.status(OK).body(response);
    }
}
