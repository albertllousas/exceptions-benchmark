# Benchmark exceptions

# Run
```shell
gradle jmh
```

Result:
```shell
Benchmark                                                     Mode  Cnt   Score    Error  Units
ExceptionsBenchmark.runWithoutAnyException                    avgt   10   0.040 ±  0.001  ms/op
ExceptionsBenchmark.throwAnException                          avgt   10  10.431 ±  0.213  ms/op
ExceptionsBenchmark.throwAnExceptionAndAccessStackTrace       avgt   10  68.536 ±  0.131  ms/op
ExceptionsBenchmark.throwAnExceptionWithoutFillingStackTrace  avgt   10   0.102 ±  0.001  ms/op
```
