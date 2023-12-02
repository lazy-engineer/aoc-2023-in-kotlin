package day02

import println
import readInput
import util.checkResult

data class Game(
    val number: Int,
    val subsets: List<Subset>
)

data class Subset(
    val red: Int,
    val blue: Int,
    val green: Int
)

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            parseGame(line).validate {
                var valid = true
                for (subset in it.subsets) {
                    if (subset.red > 12) valid = false
                    if (subset.green > 13) valid = false
                    if (subset.blue > 14) valid = false
                }
                valid
            }?.number ?: 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val maxSubset = parseGame(line).subsets.reduce { acc, subset ->
                var newSubset = acc
                if (acc.blue < subset.blue) newSubset = newSubset.copy(blue = subset.blue)
                if (acc.red < subset.red) newSubset = newSubset.copy(red = subset.red)
                if (acc.green < subset.green) newSubset = newSubset.copy(green = subset.green)
                newSubset
            }
            maxSubset.blue * maxSubset.red * maxSubset.green
        }
    }

    val testInputPart1 = readInput("day02/Day02_test")
    val part1Result = part1(testInputPart1)
    checkResult(8, part1Result)

    val testInputPart2 = readInput("day02/Day02_test")
    val part2Result = part2(testInputPart2)
    checkResult(2286, part2Result)

    val input = readInput("day02/Day02")
    part1(input).println()
    part2(input).println()
}

private fun parseGame(input: String): Game {
    val parts = input.substringAfter("Game ").split(":")
    val gameNumber = parts[0].trim().toInt()
    val subsets = parts[1].split(";")
        .filter { it.isNotBlank() }
        .map { subset ->
            val colors = subset.trim().split(",")
            var red = 0
            var blue = 0
            var green = 0

            colors.forEach { colorCount ->
                val colorParts = colorCount.trim().split(" ")
                val count = colorParts[0].toInt()
                when (colorParts[1]) {
                    "red" -> red = count
                    "blue" -> blue = count
                    "green" -> green = count
                }
            }

            Subset(red, blue, green)
        }

    return Game(gameNumber, subsets)
}

private fun Game.validate(predicate: (Game) -> Boolean): Game? {
    return if (predicate(this)) {
        this
    } else {
        null
    }
}
