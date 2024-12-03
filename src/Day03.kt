fun main() {
    val input = readInputString("Day03")

    val result1 = """mul\((\d+),(\d+)\)"""
        .toRegex()
        .findAll(input)
        .sumOf {
            val (_, x, y) = it.groupValues
            x.toInt() * y.toInt()
        }

    check(result1 == 196826776)
    println(result1)

    val result2 = """mul\((\d+),(\d+)\)|do\(\)|don't\(\)"""
        .toRegex()
        .findAll(input)
        .fold(1 to 0) { (enabled, sum), match ->
            val (op, x, y) = match.groupValues
            when (op) {
                "do()" -> 1 to sum
                "don't()" -> 0 to sum
                else -> enabled to (sum + enabled * (x.toInt() * y.toInt()))
            }
        }
        .second

    check(result2 == 106780429)
    println(result2)
}

// part2
//    val doyes = """do\(\)""".toRegex()
//    val dono = """don't\(\)""".toRegex()
//
//    val dos = doyes.findAll(input).map { it.range.start }
//    val donts = dono.findAll(input).map { it.range.start }
//
//    val result2 = res.sumOf {
//        val (_, _, x, y) = it.groupValues
//
//        var shouldDo = true
//        for (i in it.range.start downTo 0) {
//            if (i in dos) {
//                break
//            }
//            if (i in donts) {
//                shouldDo = false
//                break
//            }
//        }
//
//        if (shouldDo) {
//            x.toInt() * y.toInt()
//        } else 0
//    }
//    println("res2")
