package com.eshilov.auth.token.operations.refresh;

import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.key.KeyService;
import com.eshilov.auth.token.operations.create.CreateTokenPairOperationExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenPairOperationExecutor {

    private final KeyService keyService;
    private final CreateTokenPairOperationExecutor createTokenPairOperationExecutor;

    public TokenPair execute(RefreshTokenPairParams params) {
        return RefreshTokenPairOperation.builder()
                .keyService(keyService)
                .createTokenPairOperationExecutor(createTokenPairOperationExecutor)
                .params(params)
                .build()
                .execute();
    }
}
