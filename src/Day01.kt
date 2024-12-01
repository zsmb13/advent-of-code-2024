import kotlin.math.abs

fun main() {
    val input = readInput("Day01")

    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()
    input.forEach {
        val (l, r) = it.split("   ")
        left += l.toInt()
        right += r.toInt()
    }

    val result1 = left.sorted().zip(right.sorted())
        .sumOf { (l, r) -> abs(l - r) }
    check(result1 == 1834060)
    println(result1)

    val result2 = left.fold(0) { acc, i ->
        acc + i * right.count { it == i }
    }
    check(result2 == 21607792)
    println(result2)
}
