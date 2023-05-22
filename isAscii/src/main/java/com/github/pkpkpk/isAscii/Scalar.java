package com.github.pkpkpk.isAscii;

public class Scalar implements AsciiChecker {

    public int check(byte[] byteArray) {
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] < 0) {
                return i;
            }
        }
        return -1;
    }
}
