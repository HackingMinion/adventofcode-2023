enum class Kind(val value: Int) {
    HighCard(0),
    OnePair(1),
    TwoPairs(2),
    ThreeOfAKind(3),
    FullHouse(4),
    FourOfAKind(5),
    FiveOfAKind(6),
}

fun main() {
    data class Card(val label: Char)
    val allCards = mutableListOf(
            Card('2'),
            Card('3'),
            Card('4'),
            Card('5'),
            Card('6'),
            Card('7'),
            Card('8'),
            Card('9'),
            Card('T'),
            Card('J'),
            Card('Q'),
            Card('K'),
            Card('A'),
    )

    data class Hand(val cards: List<Card>, val bid: Int) : Comparable<Hand> {
        fun isFiveOfAKind(): Boolean {
            return getLabelCountInHand().values.any { it == 5 }
        }

        fun isFourOfAKind(): Boolean {
            return getLabelCountInHand().values.any { it == 4 }
        }

        fun isFullHouse(): Boolean {
            return getLabelCountInHand().values.any { it == 3 } && getLabelCountInHand().values.any { it == 2 }
        }

        fun isThreeOfAKind(): Boolean {
            val charCount = getLabelCountInHand()

            val entry = charCount.entries.find { it.value == 3 }
            return entry != null && charCount.entries.all { (char, amount) -> amount == 1 || amount == 3 && entry.key == char }
        }

        fun isTwoPairs(): Boolean {
            val charCount = getLabelCountInHand()

            val (firstEntry, secondEntry) = charCount.entries
                    .filter { it.value == 2 }
                    .let { entries ->
                        entries.firstOrNull() to entries.firstOrNull { it.key != entries.firstOrNull()?.key }
                    }

            return firstEntry != null && secondEntry != null
        }

        fun isOnePair(): Boolean {
            val charCount = getLabelCountInHand()
            val firstEntry = charCount.entries.find { (_, amount) ->
                amount == 2
            }
            return charCount.entries.all { (char, amount) ->
                amount == 1 && char != firstEntry?.key || amount == 2 && char == firstEntry?.key
            } && firstEntry != null
        }

        fun getKind(): Kind {
            return when {
                isFiveOfAKind() -> Kind.FiveOfAKind
                isFourOfAKind() -> Kind.FourOfAKind
                isFullHouse() -> Kind.FullHouse
                isThreeOfAKind() -> Kind.ThreeOfAKind
                isTwoPairs() -> Kind.TwoPairs
                isOnePair() -> Kind.OnePair
                else -> Kind.HighCard
            }
        }

        fun getLabelCountInHand(): Map<Char, Int> {
            val charCountMap = mutableMapOf<Char, Int>()

            for (char in this.cards.map { it.label }) {
                charCountMap[char] = charCountMap.getOrDefault(char, 0) + 1
            }

            return charCountMap
        }

        fun compareByChar(other: List<Card>): Int {
            this.cards.forEachIndexed { index, card ->
                val thisIndex = allCards.indexOf(card)
                val otherIndex = allCards.indexOf(other[index])

                if (thisIndex > otherIndex) {
                    return 1
                } else if (thisIndex < otherIndex) {
                    return -1
                }
            }

            return 0
        }

        override fun compareTo(other: Hand): Int {
            val thisKind = this.getKind()
            val otherKind = other.getKind()
            if (thisKind != otherKind) {
                return thisKind.value - otherKind.value
            }
            return this.compareByChar(other.cards)
        }
    }


    fun parseInput(input: List<String>): List<Hand> {
        return input.map { line ->
            line.split(" ")

        }.map {
            Hand(it.first().map { card -> Card(card) }, it.last().toInt())
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
