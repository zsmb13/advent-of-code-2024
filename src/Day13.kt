package day13

import readInputString
import kotlin.math.roundToLong

private val regex = """.+?(\d+).+?(\d+)""".toRegex()

private fun tokenCountWithLoops(input: List<String>): Int {
    fun String.ints() = regex.find(this)!!.groupValues.drop(1).map { it.toInt() }

    val (ax, ay) = input[0].ints()
    val (bx, by) = input[1].ints()
    val (px, py) = input[2].ints()

    for (a in 0..100) {
        for (b in 0..100) {
            if (a * ax + b * bx == px && a * ay + b * by == py) {
                return 3 * a + b
            }
        }
    }
    return 0
}

private fun tokenCountWithMath(input: List<String>, offset: Long = 0): Long {
    fun String.longs() = regex.find(this)!!.groupValues.drop(1).map { it.toDouble() }

    val (ax, ay) = input[0].longs()
    val (bx, by) = input[1].longs()
    val (px, py) = input[2].longs().map { it + offset }

    // a * ax + b * bx == px
    // a == (px - b * bx) / ax

    // a * ay + b * by == py
    // ((px - b * bx) / ax) * ay + b * by == py
    // ((px*ay - b*bx*ay) / ax) + b * by == py
    // ((px*ay/ax - b*bx*ay/ax)) + b * by == py
    // (px*ay/ax - b*bx*ay/ax) + b * by == py
    // px*ay/ax + (by - bx*ay/ax) * b == py
    // (by - bx*ay/ax) * b == py - (px*ay/ax)
    // b == (py - (px*ay/ax)) / (by - bx*ay/ax)

    val b = ((py - ((px * ay) / ax)) / (by - (bx * ay) / ax)).roundToLong()
    val a = ((px - b * bx) / ax).roundToLong()

    return if (a * ax + b * bx == px && a * ay + b * by == py) {
        return 3 * a + b
    } else {
        0
    }
}

fun main() {
    val input = readInputString("Day13").split("\n\n")

    val part1v1 = input.sumOf { tokenCountWithLoops(it.lines()) }
    check(part1v1 == 40069)
    println(part1v1)

    val part1v2 = input.sumOf { tokenCountWithMath(it.lines()) }
    check(part1v2 == 40069L)
    println(part1v2)

    val part2 = input.sumOf { tokenCountWithMath(it.lines(), 10000000000000L) }
    check(part2 == 71493195288102L)
    println(part2)
}
