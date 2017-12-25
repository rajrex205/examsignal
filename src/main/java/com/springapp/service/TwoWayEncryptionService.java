package com.springapp.service;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

public class TwoWayEncryptionService {
    private static final byte[] PRIVATE_KEY = new byte[] {'1','9','0','0','-','2','1','0','0','-','#','E','p','o','F','0'}; //1900-2100-#EpoF0
    private static final String ALGORITHM = "AES";

    public String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(PRIVATE_KEY, ALGORITHM);
        return key;
    }
}
