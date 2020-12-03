package com.eshilov.auth.token;

import com.eshilov.auth.generated.model.RefreshTokensRequest;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.token.operations.create.CreateTokensRequest;

public interface TokenService {

    TokenPair createTokens(CreateTokensRequest request);

    TokenPair refreshTokens(RefreshTokensRequest request);
}
