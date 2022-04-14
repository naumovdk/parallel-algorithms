package quicksort

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveAction

class ParallelQuicksort : Sort {
    override fun run(array: IntArray) {
        ForkJoinPool().invoke(RecursiveSort(array, 0, array.size - 1))
    }

    class RecursiveSort(private val array: IntArray, private val l: Int, private val r: Int) : RecursiveAction() {
        override fun compute() {
            if (r - l < 100) {
                SequentialQuicksort().sort(array, l, r)
                return
            }

            if (l < r) {
                val p = partition(array, l , r)
                val t1 = RecursiveSort(array, l, p - 1)
                val t2 = RecursiveSort(array, p + 1, r)
                t1.fork()
                t2.compute()
                t1.join()
            }
        }
    }
}