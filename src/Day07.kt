fun main() {
    data class Hand(val cards: String, val bid: Int) : Comparable<Hand> {
        val groups = cards.groupBy { it }.map { it.value.size }.sortedDescending()
        override fun compareTo(other: Hand): Int {
            return compareBy({ it.groups[0] }, { it.groups[1] }, Hand::cards).compare(this, other)
        }
    }


    fun parseInput(input: List<String>): List<Hand> {
        return input.map { line ->
            val (cards, bidAmount) = line.split(" ")
            val betterCards = cards
                .replace('A', Char('9'.code + 5))
                .replace('K', Char('9'.code + 4))
                .replace('Q', Char('9'.code + 3))
                .replace('J', Char('9'.code + 2))
                .replace('T', Char('9'.code + 1))

            return@map Hand(betterCards, bidAmount.toInt())
        }
    }

    fun part1(input: List<String>): Int {
        return parseInput(input).sorted().mapIndexed() { index, hand ->
            hand.bid * (index + 1)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    /*check(part2(testInput) == 0)*/

    val input = readInput("Day07")
    part1(input).println() // 249726565
    /*part2(input).println()*/
}
