fun main() {
    val input = readInput("Day04")

    val height = input.size
    val width = input[0].length

    fun get(x: Int, y: Int): Char? {
        return input.getOrNull(y)?.getOrNull(x)
    }

    val deltas = listOf(
        -1 to -1,
        -1 to 0,
        -1 to 1,
        0 to -1,
        0 to 1,
        1 to -1,
        1 to 0,
        1 to 1,
    )

    var result1 = 0
    for (y in 0..<height) {
        for (x in 0..<width) {
            if (get(x, y) == 'X') {
                deltas.forEach { (dx, dy) ->
                    if (get(x + dx, y + dy) == 'M' &&
                        get(x + dx * 2, y + dy * 2) == 'A' &&
                        get(x + dx * 3, y + dy * 3) == 'S'
                    ) {
                        result1++
                    }
                }
            }
        }
    }
    check(result1 == 2447)
    println(result1)


    operator fun Char.plus(c: Char) = code + c.code

    var result2 = 0
    for (y in 0..<height) {
        for (x in 0..<width) {
            if (get(x, y) == 'A') {
                val tl = get(x - 1, y - 1) ?: continue
                val br = get(x + 1, y + 1) ?: continue
                val tr = get(x + 1, y - 1) ?: continue
                val bl = get(x - 1, y + 1) ?: continue

                if (tl + br == 'M' + 'S' && tr + bl == 'M' + 'S') {
                    result2++
                }
            }
        }
    }
    check(result2 == 1868)
    println(result2)
}
