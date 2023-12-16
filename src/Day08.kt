fun main() {
    data class Node(val element: String, val left: String, val right: String)

    fun parseInput(input: List<String>): Pair<List<Char>, List<Node>> {
        val instructions = input
                .first()
                .toList()

        val nodes = input.drop(2).map { line ->
            val instruction = line.split(" = ")
            val (left, right) = instruction
                    .last()
                    .split("(", ")", ", ")
                    .filter(String::isNotBlank)
            Node(instruction.first(), left, right)
        }

        return Pair(instructions, nodes)
    }

    fun getInstruction(instructions: List<Char>, index: Int): Char {
        if (index >= instructions.size) {
            return instructions[(index % instructions.size)]
        }

        return instructions[index]
    }

    fun countSteps(start: String, until: (String) -> Boolean, nodes: List<Node>, instructions: List<Char>): Long {
        var element = start
        var steps = 0

        while (!until(element)) {
            val instruction = getInstruction(instructions, steps)
            val node = nodes.find { it.element == element }
            element = if (instruction == 'L') node?.left!! else node?.right!!
            steps++
        }

        return steps.toLong()
    }

    fun endingCondition(elements: List<String>): Boolean {
        return elements.all { it.last() == 'Z' }
    }

    fun part1(input: List<String>): Long {
        val (instructions, nodes) = parseInput(input)

        return countSteps(nodes.find { it.element == "AAA" }?.element!!, { it == "ZZZ" }, nodes, instructions)
    }

    fun part2(input: List<String>): Long {
        val (instructions, nodes) = parseInput(input)

        return nodes
                .filter { it.element.last() == 'A' }
                .map { entry -> countSteps(entry.element, { it.last() == 'Z' }, nodes, instructions) }
                .map(Long::toBigInteger)
                .reduce { acc, steps -> acc * steps / acc.gcd(steps) }
                .toLong()
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 2L)

    val test2Input = readInput("Day08_test2")
    check(part1(test2Input) == 6L)

    val test3Input = readInput("Day08_test3")
    check(part2(test3Input) == 6L)

    val input = readInput("Day08")
    part1(input).println() // 21797
    part2(input).println() // 23977527174353
}
