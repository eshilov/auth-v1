package com.eshilov.auth.token;

import com.eshilov.auth.generated.model.RefreshTokenRequest;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.token.operations.create.CreateTokenPairParams;

public interface TokenService {

    TokenPair createTokenPair(CreateTokenPairParams params);

    TokenPair refreshTokenPair(RefreshTokenRequest params);
}
