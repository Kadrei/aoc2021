package day.five

import readInput
import readTestInput
import kotlin.math.sign

fun main() {
	val dayName = "five"

	data class Point(val x: Int, val y: Int) {
		override fun toString(): String {
			return "[$x, $y]"
		}
	}

	class Line(val start: Point, val endInclusive: Point) : Iterable<Point> {
		fun isHorizontal(): Boolean {
			return start.y == endInclusive.y
		}

		fun isVertical(): Boolean {
			return start.x == endInclusive.x
		}

		override fun toString(): String {
			return "$start...$endInclusive"
		}

		override fun iterator(): Iterator<Point> {
			return PointIterator(start, endInclusive)
		}

		inner class PointIterator(start: Point, endInclusive: Point) : Iterator<Point> {
			var curPoint = start

			fun range(x: Int, x2: Int): IntProgression {
				return when (val step = sign((x2-x).toDouble())) {
					-1.0 -> IntProgression.fromClosedRange(x, x2, step.toInt())
					1.0 -> IntProgression.fromClosedRange(x, x2, step.toInt())
					else -> IntProgression.fromClosedRange(x, x2, 1)
				}
			}

			val xIterator = range(start.x, endInclusive.x).iterator()
			val yIterator = range(start.y, endInclusive.y).iterator()

			override fun hasNext(): Boolean {
				return xIterator.hasNext() || yIterator.hasNext()
			}

			override fun next(): Point {
				val nextX = when (xIterator.hasNext()) {
					true -> xIterator.nextInt()
					false -> curPoint.x
				}

				val nextY = when (yIterator.hasNext()) {
					true -> yIterator.nextInt()
					false -> curPoint.y
				}

				val nextPoint = Point(nextX, nextY)
				curPoint = nextPoint
				return curPoint
			}
		}
	}

	fun solve(
		input: List<String>,
		predicate: (Line) -> Boolean,
	): Int {
		fun coordToPoint(inp: String): Point {
			val (x, y) = inp.split(",").map { it.toInt() }
			return Point(x, y)
		}

		val lines = input.map {
			val (start, end) = it.split(" -> ").map { coordToPoint(it) }
			Line(start, end)
		}.filter { predicate(it) }

		return lines
			.flatMap { line -> line.iterator().asSequence() }
			.groupingBy { it }
			.eachCount()
			.filter { (_, occ) -> occ > 1 }
			.size
	}


	fun part1(input: List<String>): Int {
		return solve(input) {
			it.isVertical() || it.isHorizontal()
		}
	}

	fun part2(input: List<String>): Int {
		return solve(input) {
			true
		}
	}


	// test if implementation meets criteria from the description, like:
	val testInput = readTestInput(dayName)
	check(part1(testInput) == 5)
	check(part2(testInput) == 12)

	val input = readInput(dayName)
	println(part1(input))
	println(part2(input))
}
