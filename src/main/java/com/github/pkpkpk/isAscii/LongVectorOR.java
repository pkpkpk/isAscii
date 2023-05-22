package com.github.pkpkpk.isAscii;

import jdk.incubator.vector.*;

public class LongVectorOR implements AsciiChecker {
    private static final VectorSpecies<Byte> SPECIES_BYTE = ByteVector.SPECIES_PREFERRED;
    private static final VectorSpecies<Long> SPECIES_LONG = LongVector.SPECIES_PREFERRED;
    private static final long HIGH_BITS_SET = 0x8080808080808080L;

    public int check(byte[] array) {
        int i = 0;
        int upperBound = SPECIES_BYTE.loopBound(array.length);
        LongVector zero = LongVector.zero(SPECIES_LONG);
        LongVector highBitsSetVector = LongVector.broadcast(SPECIES_LONG, HIGH_BITS_SET);

        for (; i < upperBound; i += SPECIES_BYTE.length()) {
            var v = ByteVector.fromArray(SPECIES_BYTE, array, i);
            var vLong = v.reinterpretAsLongs();
            var mask = vLong.or(highBitsSetVector);
            if (mask.compare(VectorOperators.GT, zero).anyTrue()) {
                return i;
            }
        }

        for (; i < array.length; i++) {
            if (array[i] < 0) {
                return i;
            }
        }

        return -1;
    }
}
