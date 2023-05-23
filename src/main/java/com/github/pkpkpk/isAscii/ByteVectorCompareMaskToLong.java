package com.github.pkpkpk.isAscii;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class ByteVectorCompareMaskToLong implements AsciiChecker {

    public int check(byte[] byteArray) {
        VectorSpecies<Byte> SPECIES = ByteVector.SPECIES_PREFERRED;
        ByteVector zero = ByteVector.zero(SPECIES);
        int i = 0;

        for (; i <= byteArray.length - zero.length(); i += zero.length()) {
            ByteVector v = ByteVector.fromArray(SPECIES, byteArray, i);
            VectorMask<Byte> mask = v.compare(VectorOperators.UNSIGNED_GE, (byte) 0b10000000);
            if (mask.toLong() != 0L) {
                for (int j = 0; j < SPECIES.length(); j++) {
                    if (mask.laneIsSet(j)) {
                        return i + j;
                    }
                }
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
