package com.github.pkpkpk.isAscii;

public class Scalar {

    public int check(byte[] byteArray) {
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] < 0) {
                return i; // Return the index where non-ASCII character occurs
            }
        }
        return -1; // No non-ASCII character found
    }
}
