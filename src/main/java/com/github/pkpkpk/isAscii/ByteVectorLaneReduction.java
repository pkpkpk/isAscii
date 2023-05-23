package com.github.pkpkpk.isAscii;

import jdk.incubator.vector.*;

public class ByteVectorLaneReduction implements AsciiChecker {
    public int check(byte[] byteArray) {
        VectorSpecies<Byte> SPECIES = ByteVector.SPECIES_PREFERRED;
        int i = 0;
        for (; i < byteArray.length - SPECIES.length(); i += SPECIES.length()) {
            ByteVector vector = ByteVector.fromArray(SPECIES, byteArray, i);
            if (vector.reduceLanes(VectorOperators.OR) < 0) {
                for (int j = 0; j < vector.length(); j++) {
                    if ((byteArray[i + j] & 0b10000000) != 0) {
                        return i + j;
                    }
                }
            }
        }

        for (; i < byteArray.length; i++) {
            if ((byteArray[i] & 0b10000000) != 0) {
                return i;
            }
        }

        return -1;
    }
}
