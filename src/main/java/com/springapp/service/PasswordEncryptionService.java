package com.springapp.service;

import com.springapp.exception.EncryptionException;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptionService {

    private static String ENCRYPTION_METHOD = "SHA-512";

    public boolean isPasswordMatching(String plainPassword, String encryptedPassword){
        return StringUtils.equalsIgnoreCase(getEncryptedPassword(plainPassword),encryptedPassword);
    }

    public String getEncryptedPassword(String plainPassword) throws EncryptionException{
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(ENCRYPTION_METHOD);
            md.update(plainPassword.getBytes());
        }catch (NoSuchAlgorithmException ex){
            throw new EncryptionException("Error: Initiating Encryption Method: "+ENCRYPTION_METHOD+" "+ex.getMessage());
        }
        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
