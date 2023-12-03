fun main() {
    fun part1(input: List<String>): Int {
        val listOfNumbers = mutableListOf<Int>();
        for (line in input) {
            val digits = line.toCharArray().filter { it.isDigit()}
            listOfNumbers.add("${digits[0]}${digits[digits.count()-1]}".toInt())
        }
        return listOfNumbers.sum();
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 137)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
