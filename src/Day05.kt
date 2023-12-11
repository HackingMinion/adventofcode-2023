fun main() {
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

    fun getMaps(input: List<String>): List<List<Mapping>> {
        return input
                .drop(2)
                .joinToString("\n")
                .split("\n\n")
                .map { step ->
                    step
                            .split("\n")
                            .drop(1)
                            .map { line -> line.split(' ').map(String::toLong) }
                            .map {
                                Mapping(it[0], it[1], it[2])
                            }
                }
    }

    fun part1(input: List<String>): Long {
        val seeds = getSeeds(input)
        val mappings = getMaps(input)
        return mappings.fold(seeds) { acc, mapping ->
            acc.map {inputNumber ->
                mapping.find { inputNumber in it.sourceRange}?.destinationForSource(inputNumber) ?: inputNumber
            }
        }.min()
    }

    fun part2(input: List<String>): Long {
        val seedsAndRanges = getSeeds(input)
        val seedRanges = seedsAndRanges
                .windowed(2, 2)
                .map { it[0]..it[0] + it[1] }
        val mappings = getMaps(input)

        val reversedMaps = mappings.map {
            it.map { old ->
                Mapping(old.source, old.destination, old.length)
            }
        }.reversed()

        return generateSequence(0, Long::inc).first { location ->
            val seed = reversedMaps.fold(location) { acc, mapping ->
                mapping.find { acc in it.sourceRange}?.destinationForSource(acc) ?: acc
            }

            seedRanges.any { seedRange -> seed in seedRange }
        }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println() // 214922730
    part2(input).println() // 148041808
}

