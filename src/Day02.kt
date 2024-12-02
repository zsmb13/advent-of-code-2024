import kotlin.math.abs

fun main() {
    val reports = readInput("Day02").map { line -> line.split(" ").map(String::toInt) }
    val safeCount = reports.count { report ->
        val win = report.windowed(2)
        win.all { (x, y) -> abs(x - y) in 1..3 } &&
                (win.all { (x, y) -> x > y } || win.all { (x, y) -> x < y })
    }
    println(safeCount)
    check(safeCount == 559)

    val safeCount2 = reports.count { report ->
        report.indices.any { badIndex ->
            val win = report.toMutableList()
                .apply { removeAt(badIndex) }
                .windowed(2)
            win.all { (x, y) -> abs(x - y) in 1..3 } &&
                    (win.all { (x, y) -> x > y } || win.all { (x, y) -> x < y })
        }
    }
    println(safeCount2)
    check(safeCount2 == 601)
}
