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


        System.out.println("Starting Scalar Single Threaded checker...");
        Scalar scalarChecker = new Scalar();
        assert (-1 != scalarChecker.check(utfbytes));
        start = Instant.now();
        result = scalarChecker.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("Scalar result: " + result);
        System.out.println("Scalar duration: " + duration + " ms");

        System.out.println("Starting VectorCompareMaskAnyTrue...");
        VectorCompareMaskAnyTrue vectorChecker = new VectorCompareMaskAnyTrue();
        assert (-1 != vectorChecker.check(utfbytes));
        start = Instant.now();
        result = vectorChecker.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorCompareMaskAnyTrue: " + result);
        System.out.println("VectorCompareMaskAnyTrue: " + duration + " ms");

        System.out.println("Starting VectorCompareMaskToLong");
        VectorCompareMaskToLong vectorCompareMaskToLong = new VectorCompareMaskToLong();
        assert (-1 != vectorCompareMaskToLong.check(utfbytes));
        start = Instant.now();
        result = vectorCompareMaskToLong.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorCompareMaskToLong: " + result);
        System.out.println("VectorCompareMaskToLong: " + duration + " ms");

        System.out.println("Starting VectorLaneReduction");
        VectorLaneReduction vectorLaneReduction = new VectorLaneReduction();
        assert (-1 != vectorLaneReduction.check(utfbytes));
        start = Instant.now();
        result = vectorLaneReduction.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorLaneReduction result: " + result);
        System.out.println("VectorLaneReduction duration: " + duration + " ms");

        System.out.println("Starting ScalarMT");
        ScalarMT scalarMT = new ScalarMT();
        start = Instant.now();
        result = scalarMT.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("ScalarMT result: " + result);
        System.out.println("ScalarMT duration: " + duration + " ms");

        System.out.println("Starting VectorCompareMaskAnyTrueMT");
        VectorCompareMaskAnyTrueMT vectorCompareMaskAnyTrueMT = new VectorCompareMaskAnyTrueMT();
        start = Instant.now();
        result = vectorCompareMaskAnyTrueMT.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorCompareMaskAnyTrueMT result: " + result);
        System.out.println("VectorCompareMaskAnyTrueMT duration: " + duration + " ms");

        System.out.println("Starting VectorLaneReductionMT");
        VectorLaneReductionMT vectorLaneReductionMT = new VectorLaneReductionMT();
        start = Instant.now();
        result = vectorLaneReductionMT.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorLaneReductionMT result: " + result);
        System.out.println("VectorLaneReductionMT duration: " + duration + " ms");

        System.out.println("Starting VectorCompareMaskToLongMT");
        VectorCompareMaskToLongMT vectorCompareMaskToLongMT = new VectorCompareMaskToLongMT();
        start = Instant.now();
        result = vectorCompareMaskToLongMT.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("VectorCompareMaskToLongMT result: " + result);
        System.out.println("VectorCompareMaskToLongMT duration: " + duration + " ms");

    }
}
