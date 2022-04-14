package bfs

import kotlin.math.pow


typealias Graph = Array<IntArray>
typealias MutableGraph = Array<MutableList<Int>>

fun cubic(size: Int): Graph {
    val n = size.toDouble().pow(3).toInt()
    val mutableGraph = Array(n) { mutableListOf<Int>() }

    for (i in 0 until size) {
        for (j in 0 until size) {
            for (k in 0 until size) {
                mutableGraph.addEdge(size, i, j ,k, 1, 0, 0)
                mutableGraph.addEdge(size, i, j ,k, 0, 1, 0)
                mutableGraph.addEdge(size, i, j ,k, 0, 0, 1)
            }
        }
    }

    return Array(n) { i -> mutableGraph[i].toIntArray() }
}


fun MutableGraph.addEdge(size: Int, x: Int, y: Int, z: Int, dx: Int, dy: Int, dz: Int) {
    if (x + dx < 0 || y + dy < 0 || z + dz < 0 || x + dx >= size || y + dy >= size || z + dz >= size) {
        return
    }

    val i = x + y * size + z * size * size
    val j = x + dx + (y + dy) * size + (z + dz) * size * size

    this[i].add(j)
    this[j].add(i)
}