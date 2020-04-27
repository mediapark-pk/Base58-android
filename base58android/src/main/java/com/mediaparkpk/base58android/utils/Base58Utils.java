package com.mediaparkpk.base58android.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.mediaparkpk.base58android.Base58.encode;

public class Base58Utils {
    public static byte[] doubleDigest(byte[] input) {
        return doubleDigest(input, 0, input.length);
    }

    public static byte[] doubleDigest(byte[] input, int offset, int length) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(input, offset, length);
            byte[] first = digest.digest();
            return digest.digest(first);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String encodeWithChecksum(byte[] input) {
        byte[] b = new byte[input.length + 4];
        System.arraycopy(input, 0, b, 0, input.length);
        Sha256Hash checkSum = HashUtils.doubleSha256(b, 0, input.length);
        System.arraycopy(checkSum.getBytes(), 0, b, input.length, 4);
        return encode(b);
    }

}
