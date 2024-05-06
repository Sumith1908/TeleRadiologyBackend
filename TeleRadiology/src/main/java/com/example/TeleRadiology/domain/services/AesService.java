package com.example.TeleRadiology.domain.services;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AesService {
    @Value("${aes.key}")
    private String secretKey;
    private static final String ALGORITHM = "AES";

    public String encrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedValueBytes = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedValueBytes);
    }

    public String decrypt(String encryptedValue) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedValueBytes = Base64.getDecoder().decode(encryptedValue);
        byte[] decryptedValueBytes = cipher.doFinal(decodedValueBytes);
        return new String(decryptedValueBytes);
    }
}
