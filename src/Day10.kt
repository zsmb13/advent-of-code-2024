package day10

import readInput

// For part2, remove "data"
data class Position(val x: Int, val y: Int)

fun part1(input: List<String>): Int {
    val xRange = input[0].indices
    val yRange = input.indices

    fun Position.neighbours(): Sequence<Position> {
        return sequence {
            yield(Position(x, y - 1))
            yield(Position(x, y + 1))
            yield(Position(x - 1, y))
            yield(Position(x + 1, y))
        }.filter { it.x in xRange && it.y in yRange }
    }

    operator fun List<String>.get(position: Position) = this[position.y][position.x]

    var sum = 0
    for ((y, s) in input.withIndex()) {
        for ((x, c) in s.withIndex()) {
            if (c != '0') {
                continue
            }

            val visited = mutableSetOf(Position(x, y))
            val peaks = mutableSetOf<Position>()

            val toVisit = Position(x, y).neighbours()
                .filter { input[it] == '1' }
                .toMutableList()

            while (toVisit.isNotEmpty()) {
                val current = toVisit.removeFirst()
                visited += current

                val currentValue = input[current]
                if (currentValue == '9') {
                    peaks += current
                    continue
                }

                toVisit += current.neighbours()
                    .filter { input[it] - currentValue == 1 }
                    .filter { it !in visited }
            }

            sum += peaks.size
        }
    }
    return sum
}

fun main() {
    val input = readInput("Day10")

    println(part1(input))
}
