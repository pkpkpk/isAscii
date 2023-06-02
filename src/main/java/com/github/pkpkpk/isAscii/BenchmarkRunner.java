package com.github.pkpkpk.isAscii;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.Random;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 8)
@Fork(1)
@OperationsPerInvocation(BenchmarkRunner.N)

public class BenchmarkRunner {
    static final int N = 5;

    @State(Scope.Thread)
    public static class MyState {
        byte[] byteArray;
        double sizeInGB;

        @Setup(Level.Trial)
        public void doSetup() {
            int size = Integer.MAX_VALUE - 8;
            byteArray = new byte[size];
            sizeInGB = size / (double) (1 << 30);  // Convert bytes to GB
            Random random = new Random();
            for (int i = 0; i < size; i++) {
                byteArray[i] = (byte) (random.nextInt('~' - ' ' + 1) + ' ');
            }
        }
    }

    @Benchmark
    public void benchScalar(MyState state, Blackhole bh) {
        AsciiChecker checker = new Scalar();
        bh.consume(checker.check(state.byteArray));
    }

    @Benchmark
    public void benchByteVectorCompareMaskAnyTrue(MyState state, Blackhole bh) {
        AsciiChecker checker = new ByteVectorCompareMaskAnyTrue();
        bh.consume(checker.check(state.byteArray));
    }

    @Benchmark
    public void benchByteVectorCompareMaskToLong(MyState state, Blackhole bh) {
        AsciiChecker checker = new ByteVectorCompareMaskToLong();
        bh.consume(checker.check(state.byteArray));
    }

    @Benchmark
    public void benchByteVectorLaneReduction(MyState state, Blackhole bh) {
        AsciiChecker checker = new ByteVectorLaneReduction();
        bh.consume(checker.check(state.byteArray));
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
