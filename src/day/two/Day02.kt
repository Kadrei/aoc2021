package day.two

import readInput
import readTestInput

class Point(val horizontal: Int, val depth: Int, val aim: Int) {
	fun movePart1(direction: Direction, change: Int): Point {
		return when (direction) {
			Direction.FORWARD -> Point(horizontal + change, depth, aim)
			Direction.UP -> Point(horizontal, depth - change, aim)
			Direction.DOWN -> Point(horizontal, depth + change, aim)
		}
	}

	fun movePart2(direction: Direction, change: Int): Point {
		return when (direction) {
			Direction.FORWARD -> Point(horizontal + change, depth + aim * change, aim)
			Direction.UP -> Point(horizontal, depth, aim - change)
			Direction.DOWN -> Point(horizontal, depth, aim + change)
		}
	}

	fun score() = horizontal * depth
}

enum class Direction {
	FORWARD,
	UP,
	DOWN
}

fun main() {
	val dayName = "two"
	val initialPosition = Point(0, 0, 0)

	fun part1(input: List<String>): Int {
		val endPoint = input.map {
			val (direction, change) = it.split(" ")
			Pair(Direction.valueOf(direction.uppercase()), change.toInt())
		}.fold(initialPosition) { acc, cur -> acc.movePart1(cur.first, cur.second) }

		return endPoint.score()
	}

	fun part2(input: List<String>): Int {
		val endPoint = input.map {
			val (direction, change) = it.split(" ")
			Pair(Direction.valueOf(direction.uppercase()), change.toInt())
		}.fold(initialPosition) { acc, cur -> acc.movePart2(cur.first, cur.second) }

		return endPoint.score()
	}


	// test if implementation meets criteria from the description, like:
	val testInput = readTestInput(dayName)
	check(part1(testInput) == 150)
	check(part2(testInput) == 900)

	val input = readInput(dayName)
	println(part1(input))
	println(part2(input))
}

