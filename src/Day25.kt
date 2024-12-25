fun main() {
    val input = readInputString("Day25")
    val groups = input.split("\n\n")
    val (lockStrings, keyStrings) = groups.partition { it.startsWith("#####") }

    fun parse(string: String): List<Int> {
        val lines = string.lines().drop(1).dropLast(1)
        return (1..5).map { lines.count { l -> l[it - 1] == '#' } }
    }

    val locks = lockStrings.map(::parse)
    val keys = keyStrings.map(::parse)

    val res = locks.sumOf { lock ->
        keys.count { key ->
            lock.zip(key) { a, b -> a + b }.all { it <= 5 }
        }
    }
    println(res)
}