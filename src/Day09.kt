fun main() {

    fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { line ->
            line.split(" ").map(String::toInt)
        }
    }

    fun getNextInRow(differenceRow: List<Int>): Int {
        if(differenceRow.all(0::equals)){
            return 0
        }

        val next = differenceRow.zipWithNext().map { it.second - it.first }

        return differenceRow.last() + getNextInRow(next)
    }

    fun part1(input: List<String>): Int {
        return parseInput(input).sumOf(::getNextInRow)
    }

    fun part2(input: List<String>): Int {
        return parseInput(input).map { it.reversed() }.sumOf(::getNextInRow)
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
