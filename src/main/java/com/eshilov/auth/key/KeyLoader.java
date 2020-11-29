package com.eshilov.auth.key;

import com.eshilov.auth.AppProperties;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
@RequiredArgsConstructor
public class KeyLoader {

    private final AppProperties appProperties;
    private final KeyFactory keyFactory;

    public KeyPair load() throws Exception {
        var privateKey = loadPrivateKey();
        var publicKey = loadPublicKey();
        return new KeyPair(publicKey, privateKey);
    }

    private PrivateKey loadPrivateKey() throws IOException, InvalidKeySpecException {
        var specBytes = loadSpecBytes(appProperties::getPrivateKeyPath);
        var spec = new PKCS8EncodedKeySpec(specBytes);
        return keyFactory.generatePrivate(spec);
    }

    private PublicKey loadPublicKey() throws IOException, InvalidKeySpecException {
        var specBytes = loadSpecBytes(appProperties::getPublicKeyPath);
        var spec = new X509EncodedKeySpec(specBytes);
        return keyFactory.generatePublic(spec);
    }

    private byte[] loadSpecBytes(Supplier<String> specFilePathSupplier) throws IOException {
        var specFilePath = specFilePathSupplier.get();
        var specFile = ResourceUtils.getFile(specFilePath);
        var specPath = specFile.toPath();
        return Files.readAllBytes(specPath);
    }
}
