package com.github.pkpkpk.isAscii;

import java.util.concurrent.*;

public class ScalarMT {
    private final ExecutorService executorService;

    public ScalarMT() {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public int check(byte[] byteArray) {
        int chunkSize = byteArray.length / Runtime.getRuntime().availableProcessors();
        Future<Integer>[] futures = new Future[Runtime.getRuntime().availableProcessors()];

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            final int chunkStart = i * chunkSize;
            final int chunkEnd = i == Runtime.getRuntime().availableProcessors() - 1 ? byteArray.length : chunkStart + chunkSize;
            futures[i] = executorService.submit(() -> checkChunk(byteArray, chunkStart, chunkEnd));
        }

        try {
            for (Future<Integer> future : futures) {
                Integer result = future.get();
                if (result != -1) {
                    return result;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }

        return -1;
    }

    private Integer checkChunk(byte[] byteArray, int start, int end) {
        for (int i = start; i < end; i++) {
            if (byteArray[i] < 0) {
                return i;
            }
        }
        return -1;
    }
}
