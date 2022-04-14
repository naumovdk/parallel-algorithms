import bfs.ParallelBfs
import bfs.SequentialBfs
import bfs.cubic
import quicksort.ParallelQuicksort
import quicksort.SequentialQuicksort
import java.lang.System.nanoTime


private fun benchmark(function: () -> Unit): Double {
    val start: Long = nanoTime()
    function.invoke()
    val time = (nanoTime() - start) / 1e+6
    println(time)

    return time
}

private fun benchmarkAgainst(name: String, functions: List<() -> Unit>) {
    println(name)
    val (t1, t2) = functions.map {
        val totalTime = Array(5) { _ ->
            benchmark { it.invoke() }
        }.sum()
        println()
        println(totalTime)
        println()

        totalTime
    }

    val speedup = t1 / t2
    println("speedup = $speedup")
    println()
}


fun main() {
    val graph = cubic(220)
    benchmarkAgainst("BFS", listOf(
        { SequentialBfs().run(graph, 0) },
        { ParallelBfs().run(graph, 0) }
    ))

    val array = IntArray(100_000_000) { (0..Int.MAX_VALUE).random() }
    val copy = array.clone()
    benchmarkAgainst("Quicksort", listOf(
        { SequentialQuicksort().run(array) },
        { ParallelQuicksort().run(copy) }
    ))
}