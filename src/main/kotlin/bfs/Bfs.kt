package bfs

interface Bfs {
    fun run(graph: Graph, start: Int) : Array<Int>
}