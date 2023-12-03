package day03

import println
import readInput
import util.checkResult

fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.toCharArray2D()
        var sum = 0
        val (rowCount, columnCount) = grid.dimensions()

        for (row in 0 until rowCount) {
            var column = 0
            while (column < columnCount) {
                var currentChar = grid[row][column]

                // Skip if the current character is not a digit
                if (currentChar.isDigit().not()) {
                    column++
                    continue
                }

                val startColumn = column
                var number = 0

                // Construct the number from consecutive digits
                while (currentChar.isDigit()) {
                    number = number * 10 + (currentChar - '0')
                    column++
                    if (column >= columnCount) break
                    currentChar = grid[row][column]
                }

                var hasAdjacentSymbol = false

                // Check if the number has an adjacent symbol (excluding periods)
                for (adjRow in row - 1..row + 1) {
                    for (adjColumn in startColumn - 1..column) {
                        if (adjRow in 0 until rowCount && adjColumn in 0 until columnCount) {
                            val adjChar = grid[adjRow][adjColumn]
                            if (adjChar != '.' && adjChar.isDigit().not()) hasAdjacentSymbol = true
                        }
                    }
                }

                if (hasAdjacentSymbol) sum += number
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val grid = input.toCharArray2D()
        val (rowCount, columnCount) = grid.dimensions()

        val gridCount = Array(rowCount) { IntArray(columnCount) }
        val gridResult = Array(rowCount) { IntArray(columnCount) { 1 } }

        // Process each character in the input array
        for (row in 0 until rowCount) {
            var column = 0
            while (column < columnCount) {
                var currentChar = grid[row][column]

                // Skip if the current character is not a digit
                if (currentChar.isDigit().not()) {
                    column++
                    continue
                }

                val startColumn = column
                var number = 0

                // Construct the number from consecutive digits
                while (currentChar.isDigit()) {
                    number = number * 10 + (currentChar - '0')
                    column++
                    if (column >= columnCount) break
                    currentChar = grid[row][column]
                }

                // Update grids based on the processed number
                for (adjRow in row - 1..row + 1) {
                    for (adjColumn in startColumn - 1..column) {
                        if (adjRow in 0 until rowCount && adjColumn in 0 until columnCount) {
                            val adjacentChar = grid[adjRow][adjColumn]
                            if (adjacentChar == '*') {
                                gridCount[adjRow][adjColumn]++
                                gridResult[adjRow][adjColumn] *= number
                            }
                        }
                    }
                }
            }
        }

        var sum = 0
        gridCount.forEachIndexed { row, column, count ->
            if (count == 2) sum += gridResult[row][column]
        }

        return sum
    }

    val testInputPart1 = readInput("day03/Day03_test")
    val part1Result = part1(testInputPart1)
    checkResult(4361, part1Result)

    val testInputPart2 = readInput("day03/Day03_test")
    val part2Result = part2(testInputPart2)
    checkResult(467835, part2Result)

    val input = readInput("day03/Day03")
    part1(input).println()
    part2(input).println()
}

fun List<String>.toCharArray2D() = Array(size) { get(it).toCharArray() }

fun Array<CharArray>.dimensions(): Pair<Int, Int> {
    val rowCount = size
    val columnCount = get(0).size
    return Pair(rowCount, columnCount)
}

inline fun Array<IntArray>.forEachIndexed(action: (row: Int, column: Int, value: Int) -> Unit) {
    for (row in indices) {
        val currentRow = get(row)
        for (column in currentRow.indices) {
            action(row, column, currentRow[column])
        }
    }
}
