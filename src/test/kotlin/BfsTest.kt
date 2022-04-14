import bfs.ParallelBfs
import bfs.SequentialBfs
import bfs.cubic
import org.junit.Test

class BfsTest {
    @Test
    fun test() {
        val graph = cubic(55)
        val seq = SequentialBfs().run(graph, 0)
        val par = ParallelBfs().run(graph, 0)

        assert(seq.size == par.size)

        for (i in seq.indices) {
            assert(seq[i] == par[i])
        }
    }
}