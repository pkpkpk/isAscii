package com.github.pkpkpk.isAscii;

import java.nio.charset.StandardCharsets;
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

        Instant start, end;
        int result;
        long duration;


        Scalar scalarChecker = new Scalar();
        assert (-1 != scalarChecker.check(utfbytes));
        start = Instant.now();
        assert -1 ==  scalarChecker.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("Scalar duration: " + duration + " ms");

        VectorLaneReduction vectorLaneReduction = new VectorLaneReduction();
        assert (-1 != vectorLaneReduction.check(utfbytes));
        start = Instant.now();
        assert -1 == vectorLaneReduction.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorLaneReduction duration: " + duration + " ms");

        VectorCompareMaskAnyTrue vectorChecker = new VectorCompareMaskAnyTrue();
        assert (-1 != vectorChecker.check(utfbytes));
        start = Instant.now();
        assert -1 ==  vectorChecker.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorCompareMaskAnyTrue: " + duration + " ms");

        VectorCompareMaskToLong vectorCompareMaskToLong = new VectorCompareMaskToLong();
        assert (-1 != vectorCompareMaskToLong.check(utfbytes));
        start = Instant.now();
        assert -1 == vectorCompareMaskToLong.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorCompareMaskToLong: " + duration + " ms");

        ScalarMT scalarMT = new ScalarMT();
        start = Instant.now();
        assert -1 ==  scalarMT.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("ScalarMT duration: " + duration + " ms");

        VectorLaneReductionMT vectorLaneReductionMT = new VectorLaneReductionMT();
        start = Instant.now();
        assert -1 ==  vectorLaneReductionMT.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorLaneReductionMT duration: " + duration + " ms");

        VectorCompareMaskAnyTrueMT vectorCompareMaskAnyTrueMT = new VectorCompareMaskAnyTrueMT();
        start = Instant.now();
        assert -1 ==  vectorCompareMaskAnyTrueMT.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorCompareMaskAnyTrueMT duration: " + duration + " ms");

        VectorCompareMaskToLongMT vectorCompareMaskToLongMT = new VectorCompareMaskToLongMT();
        start = Instant.now();
        assert -1 ==  vectorCompareMaskToLongMT.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorCompareMaskToLongMT duration: " + duration + " ms");

    }
}
