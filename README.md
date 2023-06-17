
### Icelake Intel(R) Xeon(R) Platinum 8375C CPU (AVX-512)

| Benchmark                    | Mode  | Cnt | Score   | Error | Units |
|------------------------------|-------|-----|---------|-------|-------|
| ByteVectorCompareMaskAnyTrue | thrpt | 8   | 31.860  | 0.432 | ops/s |
| ByteVectorCompareMaskToLong  | thrpt | 8   | 32.210  | 0.232 | ops/s |
| ByteVectorLaneReduction      | thrpt | 8   | 27.199  | 0.279 | ops/s |
| Scalar                       | thrpt | 8   | 11.941  | 0.011 | ops/s |

### Graviton3 (ARM NEON 128bit)

| Benchmark                    | Mode  | Cnt | Score   | Error | Units |
|------------------------------|-------|-----|---------|-------|-------|
| ByteVectorCompareMaskAnyTrue | thrpt | 8   | 40.023  | ±0.095| ops/s |
| ByteVectorCompareMaskToLong  | thrpt | 8   | 18.831  | ±0.010| ops/s |
| ByteVectorLaneReduction      | thrpt | 8   | 24.993  | ±0.020| ops/s |
| Scalar                       | thrpt | 8   | 10.669  | ±0.043| ops/s |


### Haswell Intel(R) Xeon(R) CPU E5-2666 v3 @ 2.90GHz (256bit)

| Benchmark                    | Mode  | Cnt | Score   | Error   | Units |
|------------------------------|-------|-----|---------|---------|-------|
| ByteVectorCompareMaskAnyTrue | thrpt | 8   | 17.622  | ± 0.216 | ops/s |
| ByteVectorCompareMaskToLong  | thrpt | 8   | 18.084  | ± 0.201 | ops/s |
| ByteVectorLaneReduction      | thrpt | 8   | 15.680  | ± 0.117 | ops/s |
| Scalar                       | thrpt | 8   | 9.381   | ± 0.020 | ops/s |
