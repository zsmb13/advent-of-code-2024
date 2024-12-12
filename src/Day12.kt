package day12

import readInput

data class Coords(val x: Int, val y: Int)

operator fun List<String>.get(x: Int, y: Int) = getOrNull(y)?.getOrNull(x)
operator fun List<String>.get(coords: Coords) = get(coords.x, coords.y)

fun findRegions(
    input: List<String>,
    onRegionFound: (plots: MutableSet<Coords>, perimeter: Int) -> Unit,
) {
    val xRange = input[0].indices
    val yRange = input.indices

    class MutableInt(var value: Int)

    fun visit(region: MutableSet<Coords>, perimeter: MutableInt, x: Int, y: Int, char: Char) {
        val current = Coords(x, y)
        if (current in region) {
            return
        }
        if (input[current] != char) {
            perimeter.value++
            return
        }
        region += current

        listOf(
            0 to -1,// up
            0 to 1, // down
            -1 to 0, // left
            1 to 0, // right
        ).forEach { (dx, dy) ->
            visit(region, perimeter, x + dx, y + dy, char)
        }
    }

    val visited = mutableSetOf<Coords>()
    for (y in yRange) {
        for (x in xRange) {
            if (Coords(x, y) !in visited) {
                val region = mutableSetOf<Coords>()
                val perimeter = MutableInt(0)
                visit(region, perimeter, x, y, input[x, y]!!)

                visited.addAll(region)

                onRegionFound(region, perimeter.value)
            }
        }
    }
}

fun part1(input: List<String>): Int {
    var sum = 0
    findRegions(input) { region, perimeter ->
        sum += region.size * perimeter
    }
    return sum
}

fun part2(input: List<String>): Int {
    fun Coords.corners(char: Char): Int {
        var c = 0
        listOf(
            -1 to -1, // top left
            +1 to -1, // top right
            -1 to +1, // bottom left
            +1 to +1, // bottom right
        ).forEach { (dx, dy) ->
            if (input[x + dx, y] != char && input[x, y + dy] != char)
                c++ // outer corner
            if (input[x + dx, y] == char && input[x, y + dy] == char && input[x + dx, y + dy] != char)
                c++ // inner corner
        }
        return c
    }

    var sum = 0
    findRegions(input) { region, _ ->
        val corners = region.sumOf { plot ->
            plot.corners(input[plot]!!)
        }
        sum += region.size * corners
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
