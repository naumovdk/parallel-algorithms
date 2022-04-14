package bfs

import java.util.*

class SequentialBfs : Bfs {
    override fun run(graph: Graph, start: Int): Array<Int> {
        val distances = Array(graph.size) { if (it == start) 0 else -1 }
        val queue: Queue<Int> = LinkedList(listOf(start))

        while (queue.isNotEmpty()) {
            val cur = queue.poll()
            for (neighbor in graph[cur]) {
                if (distances[neighbor] == -1) {
                    distances[neighbor] = distances[cur] + 1
                    queue.add(neighbor)
                }
            }
        }

        return distances
    }
}