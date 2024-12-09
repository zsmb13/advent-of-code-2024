package day09

import readInput
import readInputString

fun part1(input: String): Long {
    val res = input.flatMapIndexed { index, c ->
        val digit = c.digitToInt()
        if (index % 2 != 0) {
            List(digit) { '.' }
        } else {
            val value = index / 2
            List(digit) { value }
        }
    }

    var i = 0
    var j = res.lastIndex
    var sum = 0L
    while (i <= j) {
        when {
            res[i] is Int -> sum += i * (res[i++] as Int)
            res[j] is Int -> sum += i++ * (res[j--] as Int)
            else -> j--
        }
    }
    return sum
}

fun part2(input: String): Long {
    val files = mutableListOf<Pair<Int, Int>>()
    val magicOffset = 1000

    var res = buildString {
        input.forEachIndexed { index, c ->
            val digit = c.digitToInt()
            if (index % 2 != 0) {
                repeat(digit) {
                    append('.')
                }
            } else {
                val value = magicOffset + index / 2
                files += length to length + digit
                repeat(digit) {
                    append(Char(code = value))
                }
            }
        }
    }

    files.asReversed().forEach { (from, until) ->
        val filestr = res.substring(from, until)
        val target = ".".repeat(until - from)
        if (res.subSequence(0, from).contains(target)) {
            res = res.replaceFirst(target, filestr).replaceRange(from, until, target)
        }
    }

    return res.foldIndexed(0L) { index, acc, char ->
        if (char == '.') {
            acc
        } else {
            acc + index.toLong() * (char.code - magicOffset)
        }
    }
}

fun main() {
    val input = readInputString("Day09")

    val result1 = part1(input)
    check(result1 == 6201130364722)
    println(result1)

    val result2 = part2(input)
    check(result2 == 6221662795602)
    println(result2)
}
