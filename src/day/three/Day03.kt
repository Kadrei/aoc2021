package day.three

import readInput
import readTestInput

fun main() {
	val dayName = "three"

	fun <E> transpose(matrix: List<List<E>>): List<List<E>> {
		fun <E> List<E>.head(): E = this.first()
		fun <E> List<E>.tail(): List<E> = this.drop(1)
		fun <E> E.append(xs: List<E>): List<E> = listOf(this).plus(xs)

		matrix.filter { it.isNotEmpty() }.let { ys ->
			return when (ys.isNotEmpty()) {
				true -> ys.map { it.head() }.append(transpose(ys.map { it.tail() }))
				else -> emptyList()
			}
		}
	}

	fun <E> findMostCommonElement(list: List<E>): E {
		val valuesWithCount = list.groupingBy { it }.eachCount()
		return valuesWithCount.maxByOrNull { it.value }!!.key
	}

	fun <E> findLeastCommonElement(list: List<E>): E {
		val valuesWithCount = list.groupingBy { it }.eachCount()
		return valuesWithCount.minByOrNull { it.value }!!.key
	}

	fun part1(input: List<String>): Int {
		val charMatrix: List<List<Char>> = input.map {
			it.toList()
		}

		val transposedMatrix = transpose(charMatrix)

		val (gamma, epsilon) = transposedMatrix.map {
			Pair(findMostCommonElement(it), findLeastCommonElement(it))
		}.fold(Pair("", "")) { acc, cur -> Pair(acc.first + cur.first, acc.second + cur.second) }


		return gamma.toInt(2) * epsilon.toInt(2)
	}

	fun part2(input: List<String>): Int {
		fun  keepOnlyRowsWith(inputMatrix: List<String>, index: Int, filter: (List<Char>) -> Char): List<String> {
			val charMatrix: List<List<Char>> = inputMatrix.map {
				it.toList()
			}

			val transposedMatrix = transpose(charMatrix)
			val focusedRow = transposedMatrix[index]
			val requiredChar = filter(focusedRow)

			return inputMatrix.filter { it[index] == requiredChar }
		}

		fun findMostMatchingRow(inputMatrix: List<String>, filter: (List<Char>) -> Char): String {
			var keptRows = inputMatrix
			var iteration = 0
			while (keptRows.size > 1) {
				keptRows = keepOnlyRowsWith(keptRows, iteration, filter)
				iteration++
			}

			return keptRows.joinToString("")
		}


		val oxygenRate = findMostMatchingRow(input){ findMostCommonElement (it.sortedDescending())}.toInt(2)
		val c02ScrubberRate = findMostMatchingRow(input) { findLeastCommonElement(it.sorted()) }.toInt(2)

		return oxygenRate * c02ScrubberRate
	}


	// test if implementation meets criteria from the description, like:
	val testInput = readTestInput(dayName)
	check(part1(testInput) == 198)
	check(part2(testInput) == 230)

	val input = readInput(dayName)
	println(part1(input))
	println(part2(input))
}
