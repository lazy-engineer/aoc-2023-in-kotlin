package day01

import println
import readInput
import java.util.NoSuchElementException

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.firstAndLastDigit()
        }
    }

    fun part2(input: List<String>): Int {
        val digitMap = mapOf(
            "one" to "1one",
            "two" to "2two",
            "three" to "3three",
            "four" to "4four",
            "five" to "5five",
            "six" to "6six",
            "seven" to "7seven",
            "eight" to "8eight",
            "nine" to "9nine",
        )

        return input.sumOf { line ->
            var newLine = line

            for (i in 0..line.length) {
                digitMap.forEach {
                    if(line.substring(i).startsWith(it.key)) {
                        newLine = try {
                            newLine.replace(it.key, it.value)
                        } catch (e: NoSuchElementException) {
                            newLine
                        }
                    }
                }
            }

            newLine.firstAndLastDigit()
        }
    }

    val testInputPart1 = readInput("day01/Day01_testPart1")
    val part1Result = part1(testInputPart1)
    checkResult(142, part1Result)

    val testInputPart2 = readInput("day01/Day01_testPart2")
    val part2Result = part2(testInputPart2)
    checkResult(281, part2Result)

    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}

private fun String.firstAndLastDigit(): Int {
    return "${this.first { it.isDigit() }}${this.last { it.isDigit() }}".toInt()
}

fun checkResult(expected: Any, result: Any) {
    check(result == expected) {
        println("Expected $expected, but was $result")
    }
}
