fun main() {
    data class Race(val time: Long, val distance: Long) {
        fun getIfWouldWinRace(holdButtonTime: Long): Boolean {
            return holdButtonTime * (time - holdButtonTime) > distance
        }
    }

    fun parseInput(input: List<String>): List<Race> {
        val (times, records) = input.map { line ->
            line
                    .split(":")
                    .last()
                    .split(" ")
                    .filter(String::isNotBlank)
                    .map(String::toLong)

        }

        return times.zip(records).map { (time, record) -> Race(time, record) }
    }

    fun parseInputAsOneNumber(input: String): Long {
        return input
                .split(":")[1]
                .trim()
                .replace(" ", "")
                .toLong()
    }

    fun countPossibleWins(race: Race): Long {
        return (0..race.time).count {
            race.getIfWouldWinRace(it)
        }.toLong()
    }

    fun part1(input: List<String>): Long {
        val races = parseInput(input)

        return races.fold(1) { acc, race ->
            acc * countPossibleWins(race)
        }
    }

    fun part2(input: List<String>): Long {
        val time = parseInputAsOneNumber(input[0])
        val distance = parseInputAsOneNumber(input[1])
        val race = Race(time, distance)

        return countPossibleWins(race)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println() // 252000
    part2(input).println() // 36992486
}
