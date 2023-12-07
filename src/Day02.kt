fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val id = Regex("Game (\\d+)").find(line)?.groupValues?.get(1);
            val matches = Regex("(\\d+) (red|blue|green)").findAll(line)

            val possible = matches.all { match ->
                val count = match.groupValues[1].toInt()
                val color = match.groupValues[2]
                when (color) {
                    "red" -> count <= 12
                    "green" -> count <= 13
                    "blue" -> count <= 14
                    else -> false
                }
            }

            possible.takeIf { it }?.let { id?.toInt() } ?: 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val matches = Regex("(\\d+) (red|blue|green)").findAll(line)
            val colorMap = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            matches.forEach { match ->
                val count = match.groupValues[1].toInt()
                val color = match.groupValues[2]

                colorMap[color]?.let {
                    colorMap[color] = maxOf(count, it)
                }
            }

            val sum = colorMap.values.fold(1) { acc, value -> acc * value }
            sum
        }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
