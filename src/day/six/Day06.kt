package day.six

import readInput
import readTestInput

fun main() {
	val dayName = "six"

	fun nextDay(fishes: Map<Int, Long>): Map<Int, Long> {
		var afterTheDay = fishes.entries
			.map { entry -> Pair(entry.key.dec(), entry.value) }
			.toMap()

		afterTheDay = afterTheDay
			.plus(Pair(8, afterTheDay.getOrDefault(-1, 0)))
			.plus(Pair(6, afterTheDay.getOrDefault(-1, 0) + afterTheDay.getOrDefault(6, 0)))
			.filter { entry -> entry.key >= 0 && entry.value > 0 }

		return afterTheDay
	}

	fun part1(input: List<String>): Long {
		val inputFishes = input[0].split(",").groupingBy { it.toInt() }.eachCount()
		var currentFishes = inputFishes.map { Pair(it.key, it.value.toLong()) }.toMap()

		repeat(80) {
			currentFishes = nextDay(currentFishes)
		}

		return currentFishes.values.reduce { acc, cur ->
			acc + cur
		}.toLong()

	}

	fun part2(input: List<String>): Long {
		val inputFishes = input[0].split(",").groupingBy { it.toInt() }.eachCount()
		var currentFishes = inputFishes.map { Pair(it.key, it.value.toLong()) }.toMap()

		repeat(256) {
			currentFishes = nextDay(currentFishes)
		}

		return currentFishes.values.reduce { acc, cur ->
			acc + cur
		}.toLong()

	}


	// test if implementation meets criteria from the description, like:
	val testInput = readTestInput(dayName)
	check(part1(testInput) == 26L)
	check(part2(testInput) == 26984457539L)

	val input = readInput(dayName)
	println(part1(input))
	println(part2(input))
}
