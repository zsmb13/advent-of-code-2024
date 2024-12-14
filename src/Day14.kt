package day14

import readInput
import java.io.File
import java.io.PrintStream

fun part1(input: List<String>, width: Int, height: Int): Int {
    var q1 = 0
    var q2 = 0
    var q3 = 0
    var q4 = 0

    val regex = """p=(.*),(.*) v=(.*),(.*)""".toRegex()
    fun String.ints() = regex.find(this)!!.groupValues.drop(1).map { it.toInt() }

    val mx = width / 2
    val my = height / 2

    input.forEach { line ->
        val (px, py, vx, vy) = line.ints()
        val rx = (px + 100 * vx).mod(width)
        val ry = (py + 100 * vy).mod(height)

        if (rx < mx && ry < my) q1++
        if (rx > mx && ry < my) q2++
        if (rx < mx && ry > my) q3++
        if (rx > mx && ry > my) q4++
    }

    return q1 * q2 * q3 * q4
}

fun part2(input: List<String>, width: Int, height: Int) {
    val regex = """p=(.*),(.*) v=(.*),(.*)""".toRegex()
    fun String.ints() = regex.find(this)!!.groupValues.drop(1).map { it.toInt() }

    var robots = input.map { line -> line.ints().toMutableList() }

    val safetyScores = mutableListOf<Int>()

    repeat(10000) {
        val iteration = it + 1

        var q1 = 0
        var q2 = 0
        var q3 = 0
        var q4 = 0
        val mx = width / 2
        val my = height / 2

        robots.forEach { r ->
            r[0] = (r[0] + r[2]).mod(width)
            r[1] = (r[1] + r[3]).mod(height)

            val rx = r[0]
            val ry = r[1]
            if (rx < mx && ry < my) q1++
            if (rx > mx && ry < my) q2++
            if (rx < mx && ry > my) q3++
            if (rx > mx && ry > my) q4++
        }

        val safetyScore = q1 * q2 * q3 * q4
        if (safetyScore < safetyScores.average() / 3) {
            println("Iteration $iteration")
            println("$q1\t$q2\t$q3\t$q4\t\t${q1 * q2 * q3 * q4}")

            repeat(height) { h ->
                repeat(width) { w ->
                    if (robots.any { it[0] == w && it[1] == h }) {
                        print('X')
                    } else {
                        print(' ')
                    }
                }
                println()
            }
            println()
        }
        safetyScores += safetyScore
    }
}

fun main() {
    val input = readInput("Day14")

    println(part1(input, 101, 103))

    System.setOut(PrintStream(File("out.txt")))
    part2(input, 101, 103)
}
