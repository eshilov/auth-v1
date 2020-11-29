package com.eshilov.auth.token;

import com.eshilov.auth.token.model.TokenPair;
import com.eshilov.auth.token.operations.create.CreateTokenPairParams;
import com.eshilov.auth.token.operations.refresh.RefreshTokenPairParams;

public interface TokenService {

    TokenPair createTokenPair(CreateTokenPairParams params);

    TokenPair refreshTokenPair(RefreshTokenPairParams params);
}
