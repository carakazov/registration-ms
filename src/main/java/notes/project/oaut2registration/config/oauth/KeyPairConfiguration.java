package notes.project.oaut2registration.config.oauth;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyPairConfiguration {
    private static final String PUBLIC_KEY_FILE = "public.key";
    private static final String PRIVATE_KEY_FILE = "private.key";
    private static final String ALGORITHM = "RSA";

    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {

        if(Files.exists(Path.of(PRIVATE_KEY_FILE)) && Files.exists(Path.of(PUBLIC_KEY_FILE))) {
            File publicKeyFile = new File(PUBLIC_KEY_FILE);
            File privateKeyFile = new File(PRIVATE_KEY_FILE);

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            return new KeyPair(publicKey, privateKey);
        }

        KeyPair keyPair = generateKeyPair();
        Path privatePath = Files.createFile(Path.of(PRIVATE_KEY_FILE));
        Files.write(privatePath, keyPair.getPrivate().getEncoded());
        Path publicPath = Files.createFile(Path.of(PUBLIC_KEY_FILE));
        Files.write(publicPath, keyPair.getPrivate().getEncoded());

        return keyPair;
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        return gen.generateKeyPair();
    }
}
