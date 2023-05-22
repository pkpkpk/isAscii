package com.github.pkpkpk.isAscii;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        String str = "Hello, ASCII characters: abcdefghijklmnopqrstuvwxyz1234567890 and some UTF-8: öäüß";
        byte[] utfbytes =  str.getBytes(StandardCharsets.UTF_8);

        // Close to maximum possible array size.
        int size = Integer.MAX_VALUE - 8;
        byte[] byteArray = new byte[size];

        // Fill the array with ASCII characters.
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            // Generate a random ASCII character between ' ' (32) and '~' (126).
            byteArray[i] = (byte) (random.nextInt('~' - ' ' + 1) + ' ');
        }

        // Prepare the list of checkers
        List<AsciiChecker> checkers = Arrays.asList(
                new Scalar(),
                new LongVectorORCheck(),
                new ByteVectorLaneReduction(),
                new ByteVectorCompareMaskAnyTrue(),
                new ByteVectorCompareMaskToLong()
                // new ScalarMT(),
                // new VectorLaneReductionMT(),
                // new VectorCompareMaskAnyTrueMT(),
                // new VectorCompareMaskToLongMT(),
        );

        for (AsciiChecker checker : checkers) {
            assert (-1 != checker.check(utfbytes));

            Instant start = Instant.now();
            assert -1 == checker.check(byteArray);
            Instant end = Instant.now();

            long duration = Duration.between(start, end).toMillis();
            System.out.println(checker.getClass().getSimpleName() + " duration: " + duration + " ms");
        }
    }
}
