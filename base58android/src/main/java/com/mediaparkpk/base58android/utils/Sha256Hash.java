package com.mediaparkpk.base58android.utils;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;


public class Sha256Hash implements Serializable, Comparable<Sha256Hash> {
    private static final long serialVersionUID = 1L;

    public static final int HASH_LENGTH = 32;

    final private byte[] _bytes;
    private int _hash;

    public Sha256Hash(byte[] bytes) {
        this._bytes = bytes;
        _hash = -1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Sha256Hash))
            return false;
        return Arrays.equals(_bytes, ((Sha256Hash) other)._bytes);
    }

    @Override
    public int hashCode() {
        if (_hash == -1) {
            final int offset = _bytes.length - 4;
            _hash = 0;
            for (int i = 0; i < 4; i++) {
                _hash <<= 8;
                _hash |= (((int) _bytes[offset + i]) & 0xFF);
            }
        }
        return _hash;
    }

    @Override
    public String toString() {
        return toHex();
    }

    public byte[] getBytes() {
        return _bytes;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int compareTo(Sha256Hash o) {
        for (int i = 0; i < HASH_LENGTH; i++) {
            byte myByte = _bytes[i];
            byte otherByte = o._bytes[i];

            final int compare = Integer.compare(myByte, otherByte);
            if (compare != 0)
                return compare;
        }
        return 0;
    }


    public String toHex() {
        return HexUtils.toHex(_bytes);
    }

}
