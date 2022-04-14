package benchmarks

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.annotations.Benchmark
import quicksort.ParallelQuicksort
import quicksort.SequentialQuicksort
import quicksort.Sort
import java.util.concurrent.TimeUnit


@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
open class Benchmark {
    @Param("1", "2", "4")
    open var workers: Int = 0

    @Param("0", "1")
    open var implIndex = 0

    private var impl: Sort = SequentialQuicksort()

    private val impls = listOf(
        SequentialQuicksort(),
        ParallelQuicksort()
    )

    @Setup(Level.Invocation)
    fun set() {
        impl = impls[implIndex]
    }

    @Benchmark
    fun run() {
        val array = IntArray(1_000_000) { (0..Int.MAX_VALUE).random() }
        impl.run(array)
    }
}