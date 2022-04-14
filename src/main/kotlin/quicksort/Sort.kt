package quicksort

interface Sort {
    fun run(array: IntArray)
}

fun partition(array: IntArray, l: Int, r: Int): Int {
    val pivot = array[r]
    var i = l - 1
    for (j in l until r) {
        if (array[j] < pivot) {
            i += 1
            array[i] = array[j].also { array[j] = array[i] }
        }
    }
    array[i + 1] = array[r].also { array[r] = array[i + 1] }

    return i + 1
}