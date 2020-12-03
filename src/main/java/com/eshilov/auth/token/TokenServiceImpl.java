package com.eshilov.auth.token;

import com.eshilov.auth.generated.model.RefreshTokensRequest;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.token.operations.create.CreateTokensOperation;
import com.eshilov.auth.token.operations.create.CreateTokensRequest;
import com.eshilov.auth.token.operations.refresh.RefreshTokensOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final CreateTokensOperation createTokensOperation;
    private final RefreshTokensOperation refreshTokensOperation;

    @Override
    public TokenPair createTokens(CreateTokensRequest request) {
        return createTokensOperation.execute(request);
    }

    @Override
    public TokenPair refreshTokens(RefreshTokensRequest request) {
        return refreshTokensOperation.execute(request);
    }
}
