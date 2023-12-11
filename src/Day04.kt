fun main() {
    data class Card (val id: Int, var amount: Int, val winningNumbers: List<Int>, val elfNumbers: List<Int>)

    fun assembleCardList(input: List<String>): List<Card> {
        val cards = mutableListOf<Card>()
        for (line in input) {
            val id = Regex("Card\\s+(\\d+)").find(line)!!.groupValues[1].toInt();
            val parts = line.split("|")
            val winMatches = Regex("\\d+").findAll(parts[0].split(':')[1].trim())
            val elfMatches = Regex("\\d+").findAll(parts[1].trim())

            val winNumbers = winMatches.map { it.value.toInt() }.toList()
            val elfNumbers = elfMatches.map { it.value.toInt() }.toList()

            cards.add(Card(id, 1, winNumbers, elfNumbers))
        }
        return cards
    }

    fun getAmountOfWinningNumbers(card: Card): Int {
        val commonNumbers = card.winningNumbers.intersect(card.elfNumbers.toSet())
        return commonNumbers.size
    }

    fun part1(input: List<String>): Int {
        val cards = assembleCardList(input)
        return cards.sumOf { card ->
            val amountOfWinningNumbers = getAmountOfWinningNumbers(card)

            if(amountOfWinningNumbers == 0) {
                return@sumOf 0
            }

            var sum = 1
            for (i in 1 until amountOfWinningNumbers) {
                sum *= 2
            }
            sum
        }
    }

    fun part2(input: List<String>): Int {
        val cards = assembleCardList(input)
        for (card in cards){
            for (j in 0 until card.amount){
                val amountOfWinningNumbers = getAmountOfWinningNumbers(card)
                for (i in card.id+1 .. card.id + amountOfWinningNumbers){
                    val nextCard = cards.find { it.id == i }
                    if(nextCard != null) {
                        nextCard.amount += 1
                    }
                }
            }
        }
        return cards.sumOf { it.amount }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println() // 21213
    part2(input).println() // 8549735
}

