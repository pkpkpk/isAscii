package com.github.pkpkpk.isAscii;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class VectorCompareMaskToLong implements AsciiChecker {

    public int check(byte[] byteArray) {
        ByteVector zero = ByteVector.zero(VectorSpecies.ofLargestShape(byte.class));
        int i = 0;

        // Process in chunks of vector length
        for (; i <= byteArray.length - zero.length(); i += zero.length()) {
            ByteVector v = ByteVector.fromArray(zero.species(), byteArray, i);
            if (v.compare(VectorOperators.UNSIGNED_GE, (byte) 0b10000000).toLong() != 0) {
                return i; // Return the start index of the vector where non-ASCII character occurs
            }
        }

        // Process remaining elements
        for (; i < byteArray.length; i++) {
            if (byteArray[i] < 0) {
                return i; // Return the index where non-ASCII character occurs
            }
        }

        return -1; // No non-ASCII character found
    }
}
