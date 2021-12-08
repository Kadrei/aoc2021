package day.eight

import readInput
import readTestInput

fun main() {
	val dayName = "eight"

	fun part1(input: List<String>): Int {
		return input.flatMap { line ->
			val (_, output) = line.split("|").map { it.trim().split(" ") }
			output
		}.count {
			listOf(2, 3, 4, 7).contains(it.length)
		}
	}

	fun part2(input: List<String>): Int {
		return input
			.map { line ->
				val (patterns, output) = line.split(" | ")
				listOf(patterns, output).map { it.trim().split(" ").map { it.toSet() } }
			}
			.sumOf { (patterns, output) ->
				val mappedDigits = mutableMapOf(
					1 to patterns.first { it.size == 2 },
					7 to patterns.first { it.size == 3 },
					4 to patterns.first { it.size == 4 },
					8 to patterns.first { it.size == 7 },
				)


				with(mappedDigits) {
					fun Set<Char>.hasPatternInCommon(with: Int, commonPartsCount: Int): Boolean = this.intersect(getValue(with)).size == commonPartsCount

					put(3, patterns.filter { it.size == 5 }.first { it.hasPatternInCommon(with = 1, commonPartsCount = 2) })
					put(2, patterns.filter { it.size == 5 }.first { it.hasPatternInCommon(with = 4, commonPartsCount = 2) })
					put(5, patterns.filter { it.size == 5 }.first { it !in values })
					put(6, patterns.filter { it.size == 6 }.first { it.hasPatternInCommon(with = 1, commonPartsCount = 1) })
					put(9, patterns.filter { it.size == 6 }.first { it.hasPatternInCommon(with = 4, commonPartsCount = 4) })
					put(0, patterns.filter { it.size == 6 }.first { it !in values })

				}

				val mappedPatterns = mappedDigits.entries.associateBy({ it.value }) { it.key }
				output.joinToString("") { mappedPatterns.getValue(it).toString() }.toInt()
			}
	}


	// test if implementation meets criteria from the description, like:
	val testInput = readTestInput(dayName)
	check(part1(testInput) == 26)
	check(part2(testInput) == 61229)

	val input = readInput(dayName)
	println(part1(input))
	println(part2(input))
}
