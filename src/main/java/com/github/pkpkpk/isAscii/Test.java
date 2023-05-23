package com.github.pkpkpk.isAscii;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.Random;

public class Test {

    private static final Random random = new Random();
    private static final VectorSpecies<Byte> SPECIES = ByteVector.SPECIES_PREFERRED;
    private static final int PREFERRED_LENGTH = SPECIES.vectorByteSize();

    private static DataWithNonAscii createData(int length) {
        byte[] data = new byte[length];
        for (int i = 0; i < data.length; i++) {
            // Fill the array with ASCII characters
            data[i] = (byte) (random.nextInt(128));
        }

        // Randomly set one index to a non-ASCII byte
        int nonAsciiIndex = random.nextInt(data.length);
        data[nonAsciiIndex] = (byte) (128 + random.nextInt(128));

        return new DataWithNonAscii(data, nonAsciiIndex);
    }

    private static void testCheckers(String label, AsciiChecker[] checkers, DataWithNonAscii dataWithNonAscii) {
        for (AsciiChecker checker : checkers) {
            int result = checker.check(dataWithNonAscii.data);
            if (result != dataWithNonAscii.nonAsciiIndex) {
                throw new AssertionError(label + ": " + checker.getClass().getSimpleName() + " returned " + result + ", expected " + dataWithNonAscii.nonAsciiIndex);
            }
            System.out.println(label + " passed for " + checker.getClass().getSimpleName() + " with data length " + dataWithNonAscii.data.length);
        }
    }

    static class DataWithNonAscii {
        byte[] data;
        int nonAsciiIndex;

        DataWithNonAscii(byte[] data, int nonAsciiIndex) {
            this.data = data;
            this.nonAsciiIndex = nonAsciiIndex;
        }
    }

    public static void main(String[] args) {
        DataWithNonAscii shortData = createData(PREFERRED_LENGTH);
        DataWithNonAscii longData = createData(PREFERRED_LENGTH * 2 + random.nextInt(PREFERRED_LENGTH));

        // Create instances of your AsciiChecker implementations
        AsciiChecker[] checkers = new AsciiChecker[]{
                new Scalar(),
                new ByteVectorLaneReduction(),
                new ByteVectorCompareMaskAnyTrue(),
                new ByteVectorCompareMaskToLong(),
                new LongVectorOR(),
        };

        testCheckers("short data", checkers, shortData);
        testCheckers("long data", checkers, longData);
    }
}
