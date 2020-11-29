package com.eshilov.auth.token;

import com.eshilov.auth.AppProperties;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.function.Supplier;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
@RequiredArgsConstructor
public class FileKeyProvider implements KeyProvider {

    private final AppProperties appProperties;
    private final KeyFactory keyFactory;

    private KeyPair keys;

    @Override
    public KeyPair getKeys() {
        return keys;
    }

    @PostConstruct
    public void loadKeys() {
        var privateKey = loadPrivateKey();
        var publicKey = loadPublicKey();
        keys = new KeyPair(publicKey, privateKey);
    }

    @SneakyThrows
    private PrivateKey loadPrivateKey() {
        var specBytes = loadSpecBytes(appProperties::getPrivateKeyPath);
        var spec = new PKCS8EncodedKeySpec(specBytes);
        return keyFactory.generatePrivate(spec);
    }

    @SneakyThrows
    private PublicKey loadPublicKey() {
        var specBytes = loadSpecBytes(appProperties::getPublicKeyPath);
        var spec = new X509EncodedKeySpec(specBytes);
        return keyFactory.generatePublic(spec);
    }

    @SneakyThrows
    private byte[] loadSpecBytes(Supplier<String> specFilePathSupplier) {
        var specFilePath = specFilePathSupplier.get();
        var specFile = ResourceUtils.getFile(specFilePath);
        var specPath = specFile.toPath();
        return Files.readAllBytes(specPath);
    }
}
