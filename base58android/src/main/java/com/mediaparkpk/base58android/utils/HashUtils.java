
package com.mediaparkpk.base58android.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private static final String SHA256 = "SHA-256";

    private static MessageDigest getSha256Digest() {
        try {
            return MessageDigest.getInstance(SHA256);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); //cannot happen
        }
    }

    public static Sha256Hash doubleSha256(byte[] data, int offset, int length) {
        MessageDigest digest;
        digest = getSha256Digest();
        digest.update(data, offset, length);
        return new Sha256Hash(digest.digest(digest.digest()));
    }


}