import java.lang.Exception

fun main() {
    val input = readInput("Day07")

    fun part1(input: List<String>): Long {
        fun test(
            res: Long,
            values: List<Long>
        ): Boolean {
            if (values.size == 1) {
                return res == values[0]
            }

            val last = values.last()
            val added = test(
                res - last,
                values.dropLast(1)
            )
            val multiplied = res % last == 0L && test(
                res / last,
                values.dropLast(1)
            )

            return added || multiplied
        }

        return input.sumOf {
            val (x, y) = it.split(": ")
            val left = x.toLong()
            val right = y.split(" ").map { it.toLong() }

            if (test(left, right)) left else 0L
        }
    }

    fun part2(input: List<String>): Long {
        fun test(
            res: Long,
            values: List<Long>
        ): Boolean {
            if (values.size == 1) {
                return res == values[0]
            }

            val last = values.last()
            val added = test(
                res - last,
                values.dropLast(1)
            )
            val multiplied = res % last == 0L && test(
                res / last,
                values.dropLast(1)
            )
            val concated = res.toString().endsWith(values.last().toString()) &&
                    try {
                        test(
                            res.toString().removeSuffix(values.last().toString()).toLong(),
                            values.dropLast(1),
                        )
                    } catch (e: Exception) {
                        println("Error: $res, ${values.last()}")
                        false
                    }

            return added || multiplied || concated
        }

        return input.sumOf {
            val (x, y) = it.split(": ")
            val left = x.toLong()
            val right = y.split(" ").map { it.toLong() }

            if (test(left, right)) left else 0L
        }
    }

    println(part1(input))
    println(part2(input))
}