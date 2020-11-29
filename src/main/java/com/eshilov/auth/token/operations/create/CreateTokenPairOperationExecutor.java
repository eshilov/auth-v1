package com.eshilov.auth.token.operations.create;

import com.eshilov.auth.AppProperties;
import com.eshilov.auth.generated.model.TokenPair;
import com.eshilov.auth.key.KeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTokenPairOperationExecutor {

    private final KeyService keyService;
    private final AppProperties appProperties;

    public TokenPair execute(CreateTokenPairParams params) {
        return CreateTokenPairOperation.builder()
                .keyService(keyService)
                .appProperties(appProperties)
                .params(params)
                .build()
                .execute();
    }
}
