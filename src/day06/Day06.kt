package day06

import println
import readInput
import util.checkResult

fun main() {
    fun part1(input: List<String>): Long {
        val timeList = input.first().parseRecords("Time:")
        val distanceList = input.last().parseRecords("Distance:")

        val races = timeList.mapIndexed { index: Int, time: Int ->
            time to distanceList[index]
        }

        val possibleWins = races.map { (time, distance) ->
            (0 until time).filter { holdMilli ->
                (time - holdMilli) * holdMilli.toLong() > distance
            }.map { it.toLong() }
        }

        return possibleWins.map {
            it.size.toLong()
        }.reduce { acc, numberOfWins -> acc * numberOfWins }
    }


    fun part2(input: List<String>): Long {
        val time = input.first().parseRecords("Time:").joinToString("").toInt()
        val distance = input.last().parseRecords("Distance:").joinToString("").toLong()

        return (0 until time)
            .count { holdMilli -> (time - holdMilli) * holdMilli.toLong() > distance }
            .toLong()
    }

    val testInputPart1 = readInput("day06/Day06_test")
    val part1Result = part1(testInputPart1)
    checkResult(288L, part1Result)

    val testInputPart2 = readInput("day06/Day06_test")
    val part2Result = part2(testInputPart2)
    checkResult(71503L, part2Result)

    val input = readInput("day06/Day06")
    part1(input).println()
    part2(input).println()
}

private fun String.parseRecords(delimiter: String) =
    this.substringAfter(delimiter)
        .split(" ")
        .filterNot { it.isEmpty() }
        .map { it.toInt() }