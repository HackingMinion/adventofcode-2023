fun main() {
    data class Step(val index: Long, val name: String)
    data class Mapping(val destination: Long, val source: Long, val length: Long) {
        val sourceRange
            get(): LongRange {
                return source until source + length
            }

        fun destinationForSource(inputNumber: Long): Long {
            return destination + (inputNumber - source)
        }
    }

    fun getSeeds(input: List<String>): List<Long> {
        return input
                .first()
                .removePrefix("seeds: ")
                .split(" ")
                .map(String::toLong)
    }

    fun parseMap(input: List<String>, mapName: String): List<Mapping> {
        val mapLines = input.dropWhile { it != mapName }.drop(1).takeWhile { it.isNotBlank() }
        return mapLines.map { line -> line.split(' ').map(String::toLong) }.map {
            Mapping(it[0], it[1], it[2])
        }
    }

    fun getDestinationForNextMap(inputNumber: Long, mapping: Mapping): Long? {
        val inputNumberInRange = inputNumber in mapping.sourceRange
        return if (inputNumberInRange) {
            mapping.destinationForSource(inputNumber)
        } else {
            null
        }
    }

    fun getNextInputNumber(inputNumber: Long, mappings: List<Mapping>): Long {
        var nextInputNumber: Long? = null
        for (mapping in mappings) {
            nextInputNumber = getDestinationForNextMap(inputNumber, mapping)

            if (nextInputNumber != null) {
                break
            }
        }
        if (nextInputNumber == null) {
            nextInputNumber = inputNumber
        }
        return nextInputNumber
    }

    fun part1(input: List<String>): Long {
        val seeds = getSeeds(input)
        val steps = mutableListOf(
                Step(1, "seed-to-soil map:"),
                Step(2, "soil-to-fertilizer map:"),
                Step(3, "fertilizer-to-water map:"),
                Step(4, "water-to-light map:"),
                Step(5, "light-to-temperature map:"),
                Step(6, "temperature-to-humidity map:"),
                Step(7, "humidity-to-location map:"),
        )
        val locations = mutableListOf<Long>()
        for (seed in seeds) {
            var nextInputNumber = seed
            for (i in 0 until steps.size) {
                val mappingsForStep = parseMap(input, steps[i].name)
                nextInputNumber = getNextInputNumber(nextInputNumber, mappingsForStep)

                if (steps[i].name == steps.last().name) {
                    locations.add(nextInputNumber)
                }
            }
        }

        return locations.min()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    /*check(part2(testInput) == 0)*/

    val input = readInput("Day05")
    part1(input).println()
    /*part2(input).println()*/
}
