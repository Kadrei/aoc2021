package day.four

import readInput
import readTestInput

fun main() {
	val dayName = "four"

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

	class BingoTable(val table: List<List<Int>>) {
		fun append(row: List<Int>): BingoTable {
			return BingoTable(table.plus(listOf(row)))
		}

		fun isBingo(drawnNumbers: List<Int>): Boolean {
			return table.any { drawnNumbers.size >= it.size && drawnNumbers.containsAll(it) } || transpose(table).any { drawnNumbers.size >= it.size && drawnNumbers.containsAll(it) }
		}

		fun score(drawnNumbers: List<Int>): Int {
			val sumOfUnmarked = table.fold(0) { acc, row ->
				acc + row.filter { !drawnNumbers.contains(it) }.fold(0) { acc1, cur -> acc1 + cur }
			}
			return sumOfUnmarked * drawnNumbers.last()
		}

		override fun toString(): String {
			return table.joinToString(separator = "\n") { it.joinToString(separator = " ") }
		}

		fun isEmpty(): Boolean {
			return table.isEmpty()
		}
	}

	fun parseDrawnNumbers(input: String): List<Int> {
		return input.split(",").map { it.toInt() }
	}

	fun parseBingoBoards(input: List<String>): List<BingoTable> {
		return input
			.map { row ->
				when (row.isNotEmpty()) {
					true -> row.split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }
					false -> emptyList()
				}
			}
			.fold(listOf(BingoTable(emptyList()))) { acc, curRow ->
				val curBingoTable = acc.last()
				when (curRow.isNotEmpty()) {
					true -> acc.dropLast(1).plus(curBingoTable.append(curRow))
					else -> acc.plus(BingoTable(listOf()))
				}
			}.filter { !it.isEmpty() }
	}

	fun part1(input: List<String>): Int {
		val drawnNumbers = parseDrawnNumbers(input[0])
		val bingoTables = parseBingoBoards(input.drop(1))

		for (window in drawnNumbers.reversed().windowed(size = drawnNumbers.size, partialWindows = true).reversed()) {
			val winningBingoTable = bingoTables.find {
				it.isBingo(window)
			}

			when (winningBingoTable != null) {
				true -> return winningBingoTable.score(window.reversed())
				false -> continue
			}
		}

		throw IllegalStateException("No winning table")
	}

	fun part2(input: List<String>): Int {
		val drawnNumbers = parseDrawnNumbers(input[0])
		val bingoTables = parseBingoBoards(input.drop(1))

		var losingBingoTables = bingoTables
		for (window in drawnNumbers.reversed().windowed(size = drawnNumbers.size, partialWindows = true).reversed()) {
			val filteredBingoTables = losingBingoTables.filter {
				!it.isBingo(window)
			}

			when (filteredBingoTables.isEmpty()) {
				true -> return losingBingoTables[0].score(window.reversed())
				false -> losingBingoTables = filteredBingoTables
			}
		}

		throw IllegalStateException("No last losing table")
	}


	// test if implementation meets criteria from the description, like:
	val testInput = readTestInput(dayName)
	check(part1(testInput) == 4512)
	check(part2(testInput) == 1924)

	val input = readInput(dayName)
	println(part1(input))
	println(part2(input))
}
