package day.tmpl

import readInput
import readTestInput

fun main() {
	val dayName  = "tmpl"

	fun part1(input: List<String>): Int {
		return input.size
	}

	fun part2(input: List<String>): Int {
		return input.size
	}


	// test if implementation meets criteria from the description, like:
	val testInput = readTestInput(dayName)
	check(part1(testInput) == 1)
	check(part2(testInput) == 1)

	val input = readInput(dayName)
	println(part1(input))
	println(part2(input))
}
