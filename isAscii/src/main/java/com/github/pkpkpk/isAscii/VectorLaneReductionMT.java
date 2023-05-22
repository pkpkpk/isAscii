package com.github.pkpkpk.isAscii;

import java.util.concurrent.*;
import jdk.incubator.vector.*;

public class VectorLaneReductionMT {
    private final ExecutorService executorService;

    public VectorLaneReductionMT() {
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
        VectorSpecies<Byte> species = VectorSpecies.ofLargestShape(byte.class);
        int i = start;
        for (; i < end - species.length(); i += species.length()) {
            ByteVector vector = ByteVector.fromArray(species, byteArray, i);
            if (vector.reduceLanes(VectorOperators.OR) < 0) {
                for (int j = 0; j < vector.length(); j++) {
                    if ((byteArray[i + j] & 0b10000000) != 0) {
                        return i + j;
                    }
                }
            }
        }

        // Check remaining elements that don't fit into a vector.
        for (; i < end; i++) {
            if ((byteArray[i] & 0b10000000) != 0) {
                return i;
            }
        }

        return -1;
    }
}
