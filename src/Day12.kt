package day12

import readInput

data class Coords(val x: Int, val y: Int)

fun part1(input: List<String>): Int {
    val xRange = input[0].indices
    val yRange = input.indices

    fun get(x: Int, y: Int) = input[y][x]

    fun visit(visited: MutableSet<Coords>, x: Int, y: Int, char: Char): Int {
        val dis = Coords(x, y)
        if (dis in visited) {
            return 0
        }
        if (x !in xRange || y !in yRange || get(x, y) != char) {
            return 1
        }
        visited += Coords(x, y)

        val offsets = listOf(
            0 to 1,
            0 to -1,
            1 to 0,
            -1 to 0,
        )
        return offsets.sumOf { (dx, dy) ->
            visit(visited, x + dx, y + dy, char)
        }
    }

    val allVisited = mutableSetOf<Coords>()
    var sum = 0
    for (y in yRange) {
        for (x in xRange) {
            if (Coords(x, y) !in allVisited) {
                val visited = mutableSetOf<Coords>()
                val perimeter = visit(visited, x, y, input[y][x])

                sum += visited.size * perimeter

                allVisited.addAll(visited)
            }
        }
    }
    return sum
}

fun part2(input: List<String>): Int {
    val xRange = input[0].indices
    val yRange = input.indices

    fun get(x: Int, y: Int) = if (x !in xRange || y !in yRange) null else input[y][x]

    fun visit(visited: MutableSet<Coords>, x: Int, y: Int, char: Char) {
        val dis = Coords(x, y)
        if (dis in visited) {
            return
        }
        if (x !in xRange || y !in yRange || get(x, y) != char) {
            return
        }
        visited += Coords(x, y)

        val offsets = listOf(
            0 to 1,
            0 to -1,
            1 to 0,
            -1 to 0,
        )
        offsets.forEach { (dx, dy) ->
            visit(visited, x + dx, y + dy, char)
        }
    }

    fun Coords.corners(char: Char): Int {
        var c = 0

        // top left, outer then inner
        if (get(x - 1, y) != char && get(x, y - 1) != char)
            c++
        if (get(x - 1, y) == char && get(x, y - 1) == char && get(x - 1, y - 1) != char)
            c++

        // top right, outer then inner
        if (get(x + 1, y) != char && get(x, y - 1) != char)
            c++
        if (get(x + 1, y) == char && get(x, y - 1) == char && get(x + 1, y - 1) != char)
            c++

        // bottom left, outer then inner
        if (get(x - 1, y) != char && get(x, y + 1) != char)
            c++
        if (get(x - 1, y) == char && get(x, y + 1) == char && get(x - 1, y + 1) != char)
            c++

        // bottom right, outer then inner
        if (get(x + 1, y) != char && get(x, y + 1) != char)
            c++
        if (get(x + 1, y) == char && get(x, y + 1) == char && get(x + 1, y + 1) != char)
            c++

        return c
    }

    val allVisited = mutableSetOf<Coords>()
    var sum = 0
    for (y in yRange) {
        for (x in xRange) {
            if (Coords(x, y) !in allVisited) {
                val visited = mutableSetOf<Coords>()
                visit(visited, x, y, get(x, y)!!)

                val corners = visited.sumOf { it.corners(get(x, y)!!) }

                sum += visited.size * corners

                allVisited.addAll(visited)
            }
        }
    }
    return sum
}


fun main() {
    val input = readInput("Day12")

    check(part1(input) == 1477924)
    println(part1(input))

    check(part2(input) == 841934)
    println(part2(input))
}
