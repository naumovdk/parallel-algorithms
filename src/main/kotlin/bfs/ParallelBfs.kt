package bfs

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

class ParallelBfs : Bfs {
    override fun run(graph: Graph, start: Int): Array<Int> {
        val distances = arrayOfNulls<AtomicInteger>(graph.size)
        parallelFor(0, graph.size) { i ->
            distances[i] = AtomicInteger(-1)
        }
        distances[start]!!.set(0)

        val frontier = AtomicReference(IntArray(1) { start })
        while (frontier.get().isNotEmpty()) {
            var degrees = IntArray(frontier.get().size)
            parallelFor(0, degrees.size) { i ->
                degrees[i] = graph[frontier.get()[i]].size
            }
            degrees = parallelScan(degrees)

            val curFrontier = IntArray(degrees.last())
            parallelFor(0, curFrontier.size) { i ->
                curFrontier[i] = -1
            }

            parallelFor(0, frontier.get().size) { i ->
//            for (i in 0 until frontier.get().size) {
                val u = frontier.get()[i]

                if (u == -1) {
                    return@parallelFor
                }


                val distance = distances[u]!!.get()
                var myNextStart = degrees[i]
                graph[u]
                    .filter { distances[it]!!.compareAndSet(-1, distance + 1) }
                    .forEach { curFrontier[myNextStart++] = it }
            }

            val newFrontier = parallelFilter(curFrontier) {
                it != -1
            }
            frontier.set(newFrontier)
        }

        return distances.map { it!!.get() }.toTypedArray()
    }
}