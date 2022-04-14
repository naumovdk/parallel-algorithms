package bfs

import java.util.concurrent.RecursiveAction


fun parallelFor(l: Int, r: Int, task: (Int) -> Unit) {
    if (r - l < 1000) {
        for (i in l until r) {
            task.invoke(i)
        }
        return
    }

    val m = (l + r) / 2
    forkJoin(
        { parallelFor(l, m, task) },
        { parallelFor(m, r, task) }
    )
}

fun parallelMap(array: IntArray, mapper: (Int) -> Int) =
    parallelFor(0, array.size) { i: Int -> array[i] = mapper.invoke(array[i]) }

fun parallelScan(array: IntArray): IntArray {
    val n = array.size

    val result = IntArray(n + 1)
    val tree = IntArray(n)

    tree(array, 0, n, 0, tree)
    sums(array, 0, n, 0, tree, 0, result)
    result[n] = array.last() + result[n - 1]

    return result
}

fun parallelFilter(array: IntArray, predicate: (Int) -> Boolean): IntArray {
    val n = array.size

    val flags = IntArray(n) { 0 }
    parallelFor(0, n) { i: Int ->
        if (predicate.invoke(array[i])) {
            flags[i] = 1
        }
    }

    val sums = parallelScan(flags)

    val result = IntArray(sums.last())
    parallelFor(0, n) { i: Int ->
        if (flags[i] == 1) {
            result[sums[i]] = array[i]
        }
    }

    return result
}

private fun forkJoin(first: Runnable, second: Runnable) {
    val firstAction: RecursiveAction = object : RecursiveAction() {
        override fun compute() {
            first.run()
        }
    }
    firstAction.fork()
    val secondAction: RecursiveAction = object : RecursiveAction() {
        override fun compute() {
            second.run()
        }
    }
    secondAction.fork()
    firstAction.join()
    secondAction.join()
}

private fun tree(array: IntArray, l: Int, r: Int, id: Int, tree: IntArray) {
    if (r - l < 1000) {
        tree[id] = (l until r).sumOf { array[it] }
        return
    }

    val m = (l + r) / 2
    forkJoin(
        { tree(array, l, m, 2 * id + 1, tree) },
        { tree(array, m, r, 2 * id + 2, tree) }
    )
    tree[id] = tree[2 * id + 1] + tree[2 * id + 2]
}

private fun sums(data: IntArray, l: Int, r: Int, id: Int, tree: IntArray, x: Int, result: IntArray) {
    if (r - l < 1000) {
        var sum = x
        for (i in l until r) {
            result[i] = sum
            sum += data[i]
        }
        return
    }

    val m = (l + r) / 2
    forkJoin(
        { sums(data, l = l, r = m, id = 2 * id + 1, tree = tree, x = x, result) },
        { sums(data, l = m, r = r, id = 2 * id + 2, tree = tree, x = x + tree[2 * id + 1], result) },
    )
}