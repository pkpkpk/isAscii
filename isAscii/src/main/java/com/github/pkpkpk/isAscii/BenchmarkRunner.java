package com.github.pkpkpk.isAscii;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 8)
@Fork(1)
@OperationsPerInvocation(BenchmarkRunner.N)

public class BenchmarkRunner {
    static final int N = 5;
    @State(Scope.Thread)
    public static class MyState {
        byte[] byteArray;

        @Setup(Level.Trial)
        public void doSetup() {
            int size = Integer.MAX_VALUE - 8;
            byteArray = new byte[size];
            Random random = new Random();
            for (int i = 0; i < size; i++) {
                byteArray[i] = (byte) (random.nextInt('~' - ' ' + 1) + ' ');
            }
        }
    }

    @Benchmark
    public void benchScalar(MyState state) {
        AsciiChecker checker = new Scalar();
        assert -1 == checker.check(state.byteArray);
    }

    @Benchmark
    public void benchLongVectorORCheck(MyState state) {
        AsciiChecker checker = new LongVectorORCheck();
        assert -1 == checker.check(state.byteArray);
    }

    @Benchmark
    public void benchByteVectorCompareMaskAnyTrue(MyState state) {
        AsciiChecker checker = new ByteVectorCompareMaskAnyTrue();
        assert -1 == checker.check(state.byteArray);
    }

    @Benchmark
    public void benchByteVectorCompareMaskToLong(MyState state) {
        AsciiChecker checker = new ByteVectorCompareMaskToLong();
        assert -1 == checker.check(state.byteArray);
    }

    @Benchmark
    public void benchByteVectorLaneReduction(MyState state) {
        AsciiChecker checker = new ByteVectorLaneReduction();
        assert -1 == checker.check(state.byteArray);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
