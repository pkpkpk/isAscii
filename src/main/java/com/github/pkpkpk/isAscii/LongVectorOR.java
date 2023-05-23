package com.github.pkpkpk.isAscii;

import jdk.incubator.vector.*;

public class LongVectorOR implements AsciiChecker {
    private static final VectorSpecies<Byte> SPECIES_BYTE = ByteVector.SPECIES_PREFERRED;
    private static final VectorSpecies<Long> SPECIES_LONG = LongVector.SPECIES_PREFERRED;
    private static final long HIGH_BITS = 0x8080808080808080L;

    public int check(byte[] array) {
        int i = 0;
        LongVector highBitsSetVector = LongVector.broadcast(SPECIES_LONG, HIGH_BITS);
        LongVector zeroVector = LongVector.zero(SPECIES_LONG);

        for (; i <= array.length - SPECIES_BYTE.length(); i += SPECIES_BYTE.length()) {
            ByteVector v = ByteVector.fromArray(SPECIES_BYTE, array, i);
            LongVector vLong = v.reinterpretAsLongs();
            var mask = vLong.or(highBitsSetVector);
            if (mask.compare(VectorOperators.NE, zeroVector).anyTrue()) {
                for (int j = 0; j < v.length(); j++) {
                    if ((v.lane(j) & (byte) 0x80) != 0) {
                        return i + j;
                    }
                }
            }
        }

        for (; i < array.length; i++) {
            if ((array[i] & 0x80) != 0) {
                return i;
            }
        }

        return -1;
    }
}
