fun main() {
    data class Race(val time: Int, val distance: Int) {
        fun getIfWouldWinRace(holdButtonTime: Int): Boolean {
            return holdButtonTime * (time - holdButtonTime) > distance
        }
    }

    fun parseInput(input: List<String>): List<Race> {
        val races = mutableListOf<Race>()
        val timeRegex = Regex("\\s*(\\d+)")
        val distanceRegex = Regex("\\s*(\\d+)")

        val timeMatches = timeRegex.findAll(input[0])
        val distanceMatches = distanceRegex.findAll(input[1])

        for ((timeMatch, distanceMatch) in timeMatches.zip(distanceMatches)) {
            val time = timeMatch.groupValues[1].toInt()
            val distance = distanceMatch.groupValues[1].toInt()
            races.add(Race(time, distance))
        }

        return races
    }


    fun part1(input: List<String>): Int {
        val races = parseInput(input)

        return races.fold(1) { acc, race ->
            val sum = (0..race.time).reduce { acc, i ->
                acc + if (race.getIfWouldWinRace(i)) 1 else 0
            }

            acc * sum
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    /*check(part2(testInput) == 0)*/

    val input = readInput("Day06")
    part1(input).println() // 252000
    /*part2(input).println()*/
}
