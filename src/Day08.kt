package d08

import readInput

data class Position(val x: Int, val y: Int)

fun part1(): Int {
    val input = readInput("Day08")

    val chars = mutableListOf<Pair<Char, Position>>()
    input.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c != '.') {
                chars.add(c to Position(x,y))
            }
        }
    }

    val grouped: Map<Char, List<Position>> = chars.groupBy { it.first }
        .mapValues { it.value.map { it.second } }

    val antinodes = mutableSetOf<Position>()
    grouped.values.forEach { positions ->
        positions.forEach { p1 ->
            (positions - p1).forEach { p2 ->
                val deltaX = p2.x - p1.x
                val deltaY = p2.y - p1.y
                antinodes += Position(p1.x - deltaX, p1.y - deltaY)
                antinodes += Position(p2.x + deltaX, p2.y + deltaY)
            }
        }
    }

    val valid = antinodes.filter {
        it.x in input[0].indices && it.y in input.indices
    }
    return valid.size
}

fun part2(): Int {
    val input = readInput("Day08")

    val chars = mutableListOf<Pair<Char, Position>>()
    input.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c != '.') {
                chars.add(c to Position(x,y))
            }
        }
    }

    val grouped: Map<Char, List<Position>> = chars.groupBy { it.first }
        .mapValues { it.value.map { it.second } }

    val xIndices = input[0].indices
    val yIndices = input.indices

    val antinodes = mutableSetOf<Position>()
    grouped.values.forEach { positions ->
        positions.forEach { p1 ->
            (positions - p1).forEach { p2 ->
                val deltaX = p2.x - p1.x
                val deltaY = p2.y - p1.y

                antinodes += p1
                antinodes += p2

                var targetX = p1.x - deltaX
                var targetY = p1.y - deltaY
                while (targetX in xIndices && targetY in yIndices) {
                    antinodes += Position(targetX, targetY)
                    targetX -= deltaX
                    targetY -= deltaY
                }

                targetX = p2.x + deltaX
                targetY = p2.y + deltaY
                while (targetX in xIndices && targetY in yIndices) {
                    antinodes += Position(targetX, targetY)
                    targetX += deltaX
                    targetY += deltaY
                }
            }
        }
    }
    return antinodes.size
}

fun main() {
    println(part1())
    println(part2())
}
