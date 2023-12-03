import java.util.*
enum class Number(val value: Int) {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9)
}

data class Digits (val number: Int, val index: Int)

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
        for (line in input) {
            val listOfDigits = mutableListOf<Digits>();
            for (number in Number.entries) {
                val stringIndex = line.indexOf(number.name.lowercase(Locale.getDefault()))
                if(stringIndex != -1) {
                    listOfDigits.add(Digits(number.value, stringIndex))
                }

                val numberIndex = line.indexOf(number.value.toString())
                if(numberIndex != -1) {
                    listOfDigits.add(Digits(number.value, numberIndex))
                }
            }
            listOfDigits.sortBy { it.index }
            println(listOfDigits.map{it.number})
        }
        return 1;
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 137)
    check(part2(testInput) == 0)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
