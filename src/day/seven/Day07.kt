package day.seven

import readInput
import readTestInput
import kotlin.math.abs

fun main() {
	val dayName  = "seven"

	fun part1(input: List<String>): Int {
		val crabs = input[0].split(",").map { it.toInt() }
		var fuel = Int.MAX_VALUE
		repeat(10000) { i ->
			fuel = crabs.sumOf { abs(it - i) }.coerceAtMost(fuel)
		}
		return fuel
	}

	fun part2(input: List<String>): Int {
		fun Long.sumTo() = this * (this + 1) / 2

		val crabs = input[0].split(",").map { it.toLong() }
		var fuel = Long.MAX_VALUE
		repeat(10000) { i ->
			fuel = crabs.sumOf { abs(it - i).sumTo() }.coerceAtMost(fuel)

		}
		return fuel.toInt()
	}


	// test if implementation meets criteria from the description, like:
	val testInput = readTestInput(dayName)
	check(part1(testInput) == 37)
	check(part2(testInput) == 168)

	val input = readInput(dayName)
	println(part1(input))
	println(part2(input))
}
