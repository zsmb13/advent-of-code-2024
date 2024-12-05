fun main() {
    val input = readInputString("Day05")

    fun <T> List<T>.toPair(): Pair<T, T> = Pair(this[0], this[1])

    val (r, u) = input.split("\n\n")
    val rules = r.lines()
        .map { it.split('|').map(String::toInt).toPair() }
    val updates = u.lines()
        .map { it.split(',').map(String::toInt).toMutableList() }

    val result1 = updates.sumOf { update ->
        val forbidden = mutableSetOf<Int>()
        for (page in update) {
            if (page in forbidden) return@sumOf 0
            forbidden += rules.filter { it.second == page }.map { it.first }
        }
        update[update.size / 2]
    }

    check(result1 == 7307)
    println(result1)


    fun <T> MutableList<T>.swap(i: Int, j: Int) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }

    val result2 = updates.sumOf { update ->
        var fixed = false
        for (i in update.indices) {
            for (j in i + 1 until update.size) {
                if ((update[j] to update[i]) in rules) {
                    update.swap(i, j)
                    fixed = true
                }
            }
        }
        if (fixed) update[update.size / 2] else 0
    }

    check(result2 == 4713)
    println(result2)
}

