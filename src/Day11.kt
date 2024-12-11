package day11

import readInputString

fun blinkAtStones(input: List<Long>, count: Int): Long {
    fun transform(stone: Long): List<Long> {
        if (stone == 0L) return listOf(1)

        val str = stone.toString()
        if (str.length % 2 == 0) {
            return listOf(
                str.substring(0, str.length / 2).toLong(),
                str.substring(str.length / 2).toLong()
            )
        }

        return listOf(stone * 2024)
    }

    fun transform(stones: Map<Long, Long>): Map<Long, Long> {
        val res = mutableMapOf<Long, Long>().withDefault { 0 }
        stones.forEach { (value, count) ->
            transform(value).forEach { newStone ->
                res[newStone] = res.getValue(newStone) + count
            }
        }
        return res
    }

    var stones: Map<Long, Long> = input.associateWith { 1L }
    repeat(count) {
        stones = transform(stones)
    }
    return stones.values.sum()
}

fun main() {
    val input = readInputString("Day11").split(" ").map { it.toLong() }

    println(blinkAtStones(input, 25))
    println(blinkAtStones(input, 75))
}
