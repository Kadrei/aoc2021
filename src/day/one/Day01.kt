package day.one

import readInput
import readTestInput

fun main() {
	val dayName = "one"

	fun calcIncreases(input: List<Int>): Int {
		var result = 0
		var previousLine: Int? = null

		for (line in input) {
			if (previousLine != null && previousLine < line) {
				result = result.inc()
			}

			previousLine = line
		}
		return result
	}

	fun part1(input: List<String>): Int {
		val inputAsInts = input.map { line -> line.toInt() }
		return calcIncreases(inputAsInts)
	}

	fun part2(input: List<String>): Int {
		val inputAsInts = input.map { line -> line.toInt() }
		val windowsSum = inputAsInts.windowed(size = 3) { window ->
			window.sum()
		}
		return calcIncreases(windowsSum)
	}


	// test if implementation meets criteria from the description, like:
	val testInput = readTestInput(dayName)
	check(part1(testInput) == 7)
	check(part2(testInput) == 5)

	val input = readInput(dayName)
	println(part1(input))
	println(part2(input))
}
