package com.github.pkpkpk.isAscii;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class ByteVectorCompareMaskToLong implements AsciiChecker {

    public int check(byte[] byteArray) {
        ByteVector zero = ByteVector.zero(VectorSpecies.ofPreferred(byte.class));
        int i = 0;

        for (; i <= byteArray.length - zero.length(); i += zero.length()) {
            ByteVector v = ByteVector.fromArray(zero.species(), byteArray, i);
            if (v.compare(VectorOperators.UNSIGNED_GE, (byte) 0b10000000).toLong() != 0) {
                return i;
            }
        }

        for (; i < byteArray.length; i++) {
            if (byteArray[i] < 0) {
                return i;
            }
        }

        return -1;
    }
}
