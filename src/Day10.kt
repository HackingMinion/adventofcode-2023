fun main() {
    data class Position(val x: Int, val y: Int) {
        fun getSymbol(input: List<String>): Char {
            return input[y][x]
        }
    }


    fun part1(input: List<String>): Int {
        val start = Position(
                x = input.first { it.contains("S") }.indexOf("S"),
                y = input.indexOfFirst { it.contains("S") }
        )

        var last = start
        var current = Position(start.x, start.y-1) // manually start in one direction

        var count = 0
        while (true) {
            val buffer = current
            val curSymbol = current.getSymbol(input)

            if(curSymbol == 'S' && count > 0){
                count++
                break
            }


            if (curSymbol in "S|LJ" && last.y != current.y - 1) {
                val newPosition = Position(current.x, current.y - 1)
                current = newPosition
            } else if (curSymbol in "S-LF" && last.x != current.x + 1) {
                val newPosition = Position(current.x + 1, current.y)
                current = newPosition
            } else if (curSymbol in "S|7F" && last.y != current.y + 1) {
                val newPosition = Position(current.x, current.y + 1)
                current = newPosition
            } else if (curSymbol in "S-J7" && last.x != current.x - 1) {
                val newPosition = Position(current.x - 1, current.y)
                current = newPosition
            }

            last = buffer
            count++
        }


        println(count)

        return count / 2
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day10_test")
    /*check(part1(testInput) == 4)*/

    val testInput2 = readInput("Day10_test2")
    /*check(part1(testInput2) == 8)*/

    val input = readInput("Day10")
    part1(input).println() // 6951
}
