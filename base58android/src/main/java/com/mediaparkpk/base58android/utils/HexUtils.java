package com.mediaparkpk.base58android.utils;

public class HexUtils {

    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes the array of bytes to encode
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes) {
        return toHex(bytes, null);
    }

    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes     the array of bytes to encode
     * @param separator the separator to use between two bytes, can be null
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes, String separator) {
        return toHex(bytes, 0, bytes.length, separator);
    }
    /**
     * Encodes an array of bytes as hex symbols.
     *
     * @param bytes     the array of bytes to encode
     * @param offset    the start offset in the array of bytes
     * @param length    the number of bytes to encode
     * @param separator the separator to use between two bytes, can be null
     * @return the resulting hex string
     */
    public static String toHex(byte[] bytes, int offset, int length, String separator) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int unsignedByte = bytes[i + offset] & 0xff;

            if (unsignedByte < 16) {
                result.append("0");
            }

            result.append(Integer.toHexString(unsignedByte));
            if (separator != null && i + 1 < length) {
                result.append(separator);
            }
        }
        return result.toString();
    }
}