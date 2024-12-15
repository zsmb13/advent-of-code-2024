package day15

import readInputString

fun main() {
    val input = readInputString("Day15")
    val (mapString, movesString) = input.split("\n\n")
    val mapLines = mapString.lines()
    val map = mutableMapOf<Pair<Int, Int>, Char>().withDefault { '.' }
    mapLines.forEachIndexed { y, l ->
        l.forEachIndexed { x, c ->
            map[x to y] = c
        }
    }

    fun move(pos: Pair<Int, Int>, dir: Char): Boolean {
        if (map[pos] == '.') return true
        if (map[pos] == '#') return false

        val targetX = pos.first + when (dir) {
            '<' -> -1
            '>' -> 1
            else -> 0
        }
        val targetY = pos.second + when (dir) {
            '^' -> -1
            'v' -> 1
            else -> 0
        }

        if (move(targetX to targetY, dir)) {
            map[targetX to targetY] = map[pos]!!
            map[pos] = '.'
            return true
        } else {
            return false
        }
    }

    movesString
        .filterNot(Char::isWhitespace)
        .forEach { dir ->
            val robot = map.entries.find { it.value == '@' }!!.key
            move(robot, dir)
        }

    val res = map.filter { it.value == 'O' }
        .toList()
        .sumOf { (coord, _) -> coord.first + coord.second * 100 }
    println(res)
}
