package com.mediaparkpk.base58android;

import com.mediaparkpk.base58android.exceptions.Base58Exception;
import com.mediaparkpk.base58android.utils.HashUtils;
import com.mediaparkpk.base58android.utils.Sha256Hash;

import java.math.BigInteger;
import java.util.Arrays;

import static com.mediaparkpk.base58android.utils.Base58Utils.doubleDigest;

public class Base58 {
    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final BigInteger BASE = BigInteger.valueOf(58);

    public static String encode(byte[] input) {
        BigInteger bi = new BigInteger(1, input);
        StringBuffer s = new StringBuffer();
        while (bi.compareTo(BASE) >= 0) {
            BigInteger mod = bi.mod(BASE);
            s.insert(0, ALPHABET.charAt(mod.intValue()));
            bi = bi.subtract(mod).divide(BASE);
        }
        s.insert(0, ALPHABET.charAt(bi.intValue()));
        for (byte anInput : input) {
            if (anInput == 0)
                s.insert(0, ALPHABET.charAt(0));
            else
                break;
        }
        return s.toString();
    }

    public static byte[] decode(String input) throws Base58Exception {
        byte[] bytes = decodeToBigInteger(input).toByteArray();
        boolean stripSignByte = bytes.length > 1 && bytes[0] == 0 && bytes[1] < 0;
        int leadingZeros = 0;
        for (int i = 0; input.charAt(i) == ALPHABET.charAt(0); i++) {
            leadingZeros++;
        }
        byte[] tmp = new byte[bytes.length - (stripSignByte ? 1 : 0) + leadingZeros];
        System.arraycopy(bytes, stripSignByte ? 1 : 0, tmp, leadingZeros, tmp.length - leadingZeros);
        return tmp;
    }

    public static BigInteger decodeToBigInteger(String input) throws Base58Exception {
        BigInteger bi = BigInteger.valueOf(0);
        for (int i = input.length() - 1; i >= 0; i--) {
            int alphaIndex = ALPHABET.indexOf(input.charAt(i));
            if (alphaIndex == -1) {
                 throw new Base58Exception("Illegal character " + input.charAt(i) + " at " + i);
            }
            bi = bi.add(BigInteger.valueOf(alphaIndex).multiply(BASE.pow(input.length() - 1 - i)));
        }
        return bi;
    }
    public static byte[] decodeChecked(String input) throws Base58Exception {
        byte[] tmp = decode(input);
        if (tmp.length < 4)
          throw new Base58Exception("Input too short");
        byte[] checksum = new byte[4];
        System.arraycopy(tmp, tmp.length - 4, checksum, 0, 4);
        byte[] bytes = new byte[tmp.length - 4];
        System.arraycopy(tmp, 0, bytes, 0, tmp.length - 4);
        tmp = doubleDigest(bytes);
        byte[] hash = new byte[4];
        System.arraycopy(tmp, 0, hash, 0, 4);
        if (!Arrays.equals(hash, checksum))
            throw new Base58Exception("Checksum does not validate");
        return bytes;
    }
    public static String Base58encodeWithChecksum(byte[] input) {
        byte[] b = new byte[input.length + 4];
        System.arraycopy(input, 0, b, 0, input.length);
        Sha256Hash checkSum = HashUtils.doubleSha256(b, 0, input.length);
        System.arraycopy(checkSum.getBytes(), 0, b, input.length, 4);
        return encode(b);
    }


}
