package com.eshilov.auth.token;

import com.eshilov.auth.generated.model.RefreshTokenRequest;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.token.operations.create.CreateTokenPairOperationExecutor;
import com.eshilov.auth.token.operations.create.CreateTokenPairParams;
import com.eshilov.auth.token.operations.refresh.RefreshTokenPairOperationExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final CreateTokenPairOperationExecutor createTokenPairOperationExecutor;
    private final RefreshTokenPairOperationExecutor refreshTokenPairOperationExecutor;

    @Override
    public TokenPair createTokenPair(CreateTokenPairParams params) {
        return createTokenPairOperationExecutor.execute(params);
    }

    @Override
    public TokenPair refreshTokenPair(RefreshTokenRequest params) {
        return refreshTokenPairOperationExecutor.execute(params);
    }
}
