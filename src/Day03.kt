package day03

import println
import readInput

typealias Row = List<Element>

sealed class Element
data class Number(val value: Int, val xDir: IntRange, val row: Int) : Element() {
    val expandedColumn = xDir.first - 1..xDir.last + 1
    val expandedRow = row - 1..row + 1
}

private fun Number(number: String, start: Int, end: Int, row: Int): Number = Number(number.toInt(), start..end, row)

data class Symbol(val value: Char, val column: Int, val row: Int) : Element()

data class GearNumbers(val number1: Number, val number2: Number)


private fun extractElements(line: String, row: Int): List<Element> {
    val elements = mutableListOf<Element>()
    var numberStart = -1
    var currentNumber = ""
    for ((index: Int, c: Char) in line.withIndex()) {
        if (c.isDigit()) {
            currentNumber += c

            if (numberStart == -1) {
                numberStart = index
            }
        } else {
            if (c != '.') {
                elements.add(Symbol(c, index, row))
            }

            if (currentNumber.isNotEmpty()) {
                elements.add(Number(currentNumber, numberStart, index - 1, row))
                currentNumber = ""
                numberStart = -1
            }
        }
    }
    if(currentNumber.isNotEmpty()) {
        elements.add(Number(currentNumber, numberStart, line.length - 1, row))
    }

    return elements
}

private fun findParts(elementsList: List<Row>): MutableSet<Number> {
    val parts = mutableSetOf<Number>()
    elementsList.windowed(2).map{twoRows ->
        val symbols = twoRows.flatten().filterIsInstance<Symbol>()
        val numbers = twoRows.flatten().filterIsInstance<Number>()
        numbers.filter{n -> symbols.any{s -> s.column in n.expandedColumn}}.forEach{parts.add(it)}
    }
    return parts
}

private fun findGears(elementsList: List<Row>): MutableSet<GearNumbers> {
    val gears = mutableSetOf<GearNumbers>()
    elementsList.windowed(3).map{threeRows ->
        val symbols = threeRows.flatten().filterIsInstance<Symbol>().filter { it.value == '*' }
        val numbers = threeRows.flatten().filterIsInstance<Number>()
        for (symbol in symbols) {
            val adjacentNumbers = numbers.filter { n -> symbol.column in n.expandedColumn && symbol.row in n.expandedRow }
            if(adjacentNumbers.size == 2) {
                gears.add(GearNumbers(adjacentNumbers[0], adjacentNumbers[1]))
            }
        }
    }
    return gears
}

fun main() {
    fun part1(input: List<String>): Int {
        val elementsList: List<Row> = input.mapIndexed { i: Int, s: String -> extractElements(s, i) }
        val numbers = findParts(elementsList)
        return numbers.sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        val elementsList: List<Row> = input.mapIndexed { i: Int, s: String -> extractElements(s, i)}
        val gearNumbers = findGears(elementsList)
        println(gearNumbers)
        val sum = gearNumbers.sumOf { it.number1.value * it.number2.value }
        println(sum)
        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}


