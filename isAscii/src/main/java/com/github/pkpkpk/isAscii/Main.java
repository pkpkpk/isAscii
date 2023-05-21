package com.github.pkpkpk.isAscii;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        // Close to maximum possible array size.
        int size = Integer.MAX_VALUE - 8;
        byte[] byteArray = new byte[size];

        // Fill the array with ASCII characters.
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            // Generate a random ASCII character between ' ' (32) and '~' (126).
            byteArray[i] = (byte) (random.nextInt('~' - ' ' + 1) + ' ');
        }

        VectorSingleThreadedChecker vectorChecker = new VectorSingleThreadedChecker();
        ScalarSingleThreadedChecker scalarChecker = new ScalarSingleThreadedChecker();

        Instant start, end;
        int result;
        long duration;

        System.out.println("Starting Vector checker...");
        start = Instant.now();
        result = vectorChecker.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("Vector checker result: " + result);
        System.out.println("Vector checker duration: " + duration + " ms");

        System.out.println("Starting Scalar checker...");
        start = Instant.now();
        result = scalarChecker.check(byteArray);
        end = Instant.now();
        duration = Duration.between(start, end).toMillis();
        System.out.println("Scalar checker result: " + result);
        System.out.println("Scalar checker duration: " + duration + " ms");
    }
}
