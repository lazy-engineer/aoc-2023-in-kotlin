package day05

import println
import readInput
import util.checkResult
import util.parts

data class Range(val destination: Long, val source: Long, val length: Long)
data class Destination(val name: String, val ranges: List<Range>)
data class SeedRange(val start: Long, val end: Long)

fun main() {
    fun part1(input: List<String>): Long {
        val seeds = parseSeeds(input)
        val destinationMap = buildDestinationMap(input)

        return seeds.minOf { seed ->
            var currentSourceName = "seed"
            var currentValue = seed

            // Continue converting until we reach the last destination "location"
            while (true) {
                val conversionResult = destinationMap.followMap(currentSourceName, currentValue) ?: break
                currentSourceName = conversionResult.first
                currentValue = conversionResult.second
            }

            // Ensure that the final destination is "location"
            check(currentSourceName == "location")
            currentValue
        }
    }

    fun part2(input: List<String>): Long {
        val seeds = parseSeeds(input)
        val destinationMap = buildDestinationMap(input)
        return findMinimumStartingLocation(seeds, destinationMap)
    }

    val testInputPart1 = readInput("day05/Day05_test")
    val part1Result = part1(testInputPart1)
    checkResult(35L, part1Result)

    val testInputPart2 = readInput("day05/Day05_test")
    val part2Result = part2(testInputPart2)
    checkResult(46L, part2Result)

    val input = readInput("day05/Day05")
    part1(input).println()
    part2(input).println()
}

private fun parseSeeds(input: List<String>): List<Long> {
    return input.first()
        .split(":", " ")
        .drop(2)
        .map { it.toLong() }
}

private fun buildDestinationMap(input: List<String>): Map<String, Destination> {
    val destinationMap = mutableMapOf<String, Destination>()

    input.drop(2).parts { part ->
        val (sourceName, destinationName) = part[0].split(" ").first().split("-to-")

        val ranges = part.drop(1).map { rangeString ->
            val (destination, source, length) = rangeString.split(" ").map { it.toLong() }
            Range(destination, source, length)
        }

        // Store the destination information in the map
        destinationMap[sourceName] = Destination(destinationName, ranges)
    }

    return destinationMap
}

private fun Map<String, Destination>.followMap(sourceName: String, value: Long): Pair<String, Long>? {
    val destination = this[sourceName] ?: return null
    for (range in destination.ranges) {
        if (value in range.source until (range.source + range.length))
            return destination.name to (value - range.source + range.destination)
    }
    return destination.name to value
}

private fun findMinimumStartingLocation(seeds: List<Long>, destinationMap: Map<String, Destination>): Long {
    var currentDestinationName = "seed"
    val currentPosition = seeds[0]

    val initialSeedRange = SeedRange(currentPosition, currentPosition + seeds[1] - 1)
    var currentSeedRanges = mutableListOf(initialSeedRange)

    while (true) {
        val destination = destinationMap[currentDestinationName] ?: break
        currentDestinationName = destination.name

        val nextSeedRanges = mutableListOf<SeedRange>()
        for (seedRange in currentSeedRanges) {
            val convertedSeedRanges = destination.ranges.toSeedRanges(seedRange.start, seedRange.end)
            nextSeedRanges.addAll(convertedSeedRanges)
        }

        currentSeedRanges = nextSeedRanges
    }

    return currentSeedRanges.minOf { it.start }
}

private fun List<Range>.toSeedRanges(start: Long, end: Long): List<SeedRange> {
    val seedRanges = mutableListOf<SeedRange>()
    val result = mutableListOf<SeedRange>()

    for (range in this) {
        val sourceStart = range.source
        val sourceEnd = range.source + range.length - 1

        val intersectionStart = maxOf(start, sourceStart)
        val intersectionEnd = minOf(end, sourceEnd)

        if (intersectionStart <= intersectionEnd) {
            seedRanges.add(SeedRange(intersectionStart, intersectionEnd))
            result.add(
                SeedRange(
                    intersectionStart - sourceStart + range.destination,
                    intersectionEnd - sourceStart + range.destination
                )
            )
        }
    }

    seedRanges.sortBy { it.start }
    var current = start

    for ((rangeStart, rangeEnd) in seedRanges) {
        if (rangeStart > current) {
            result.add(SeedRange(current, rangeStart - 1))
        }
        current = rangeEnd + 1
    }

    if (current <= end) {
        result.add(SeedRange(current, end))
    }

    return result
}
