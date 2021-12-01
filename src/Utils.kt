import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from test-input txt file for the respective day.
 */
fun readInput(day: String) = readInputForDay(day, "input")

/**
 * Reads lines from test-input txt file for the respective day.
 */
fun readTestInput(day: String) = readInputForDay(day, "test_input")

/**
 * Reads lines from the given input txt file in given day folder.
 */
fun readInputForDay(day: String, name: String) = File("src/day/$day", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
