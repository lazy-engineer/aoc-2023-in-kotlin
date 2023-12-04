package day04

import println
import readInput
import util.checkResult

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (winningNumbers, myNumbers) = parseLine(line)
            val count = countWinningNumbers(winningNumbers, myNumbers)
            calculatePoints(count)
        }
    }

    fun part2(input: List<String>): Int {
        val cardList = MutableList(input.size) { 1 }

        input.withIndex().forEach { (i, line) ->
            val (winningNumbers, myNumbers) = parseLine(line)
            val count = countWinningNumbers(winningNumbers, myNumbers)
            updateCardList(cardList, i, count)
        }

        return cardList.sum()
    }

    val testInputPart1 = readInput("day04/Day04_test")
    val part1Result = part1(testInputPart1)
    checkResult(13, part1Result)

    val testInputPart2 = readInput("day04/Day04_test")
    val part2Result = part2(testInputPart2)
    checkResult(30, part2Result)

    val input = readInput("day04/Day04")
    part1(input).println()
    part2(input).println()
}

private fun parseLine(line: String): Pair<List<Int>, List<Int>> {
    val (winningNumbers, myNumbers) = line.substringAfter(":")
        .trim()
        .split("|")
        .map {
            it.trim()
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toInt() }
        }
    return Pair(winningNumbers, myNumbers)
}

private fun countWinningNumbers(winningNumbers: List<Int>, myNumbers: List<Int>): Int {
    return myNumbers.count { winningNumbers.contains(it) }
}

private fun calculatePoints(count: Int): Int {
    return count.takeIf { it >= 1 }
        ?.let { 1 until it }
        ?.fold(1) { acc, _ -> acc * 2 }
        ?: 0
}

private fun updateCardList(cardList: MutableList<Int>, currentIndex: Int, count: Int) {
    for (i in currentIndex + 1 until currentIndex + count + 1) {
        cardList[i] += cardList[currentIndex]
    }
}
