package exceptions

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode.AverageTime
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.infra.Blackhole
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.random.Random


@Fork(1)
@Measurement(iterations = 10)
@BenchmarkMode(AverageTime)
@OutputTimeUnit(MILLISECONDS)
open class ExceptionsBenchmark {

    @Benchmark
    fun runWithoutAnyException(blackhole: Blackhole) {
        repeat(10_000) {
            blackhole.consume(justReturnAValue())
        }
    }


    @Benchmark
    fun throwAnExceptionWithoutFillingStackTrace(blackhole: Blackhole) {
        repeat(10_000) {
            try {
                blackhole.consume(throwAnExceptionWithoutFillInStackTrace())
            } catch (e: Exception) {
                blackhole.consume(e.message)
            }
        }
    }

    @Benchmark
    fun throwAnException(blackhole: Blackhole) {
        repeat(10_000) {
            try {
                throwAnException()
            } catch (e: Exception) {
                blackhole.consume(e.message)
            }
        }
    }

    @Benchmark
    fun throwAnExceptionAndAccessStackTrace(blackhole: Blackhole) {
        repeat(10_000) {
            try {
                throwAnException()
            } catch (e: Exception) {
                blackhole.consume(e.stackTrace)
            }
        }
    }

    private fun justReturnAValue(): Int = Random.nextInt(0, 100)


    private fun throwAnExceptionWithoutFillInStackTrace(): Int {
        throw ExceptionWithoutFillInStackTrace("Boom!")
    }

    private fun throwAnException(): Int {
        throw Exception("Boom!")
    }

    class ExceptionWithoutFillInStackTrace(msg: String) : Exception(msg) {
        fun fillInStackTrace(): Throwable = this
    }
}

fun main() {
    val opt = OptionsBuilder()
        .include(ExceptionsBenchmark::class.simpleName)
        .forks(1)
        .build();

    Runner(opt).run()
}
