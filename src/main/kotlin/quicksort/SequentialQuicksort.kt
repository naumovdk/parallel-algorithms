package quicksort


class SequentialQuicksort : Sort {
    override fun run(array: IntArray) = sort(array, 0, array.size - 1)

    fun sort(array: IntArray, l: Int, r: Int) {
        if (l < r) {
            val p = partition(array, l , r)
            sort(array, l, p - 1)
            sort(array, p + 1, r)
            return
        }
    }
}