package day09

import java.io.InputStream
import java.io.File

fun main(args: Array<String>) {
	val stream: InputStream = File("input.txt").inputStream()
	val input = stream.bufferedReader().use { it.readText() }
	partA(input)
	partB(input)

}

fun partA(input : String) {
	assert(processA("{}") == 1)
	assert(processA("{{{}}}") == 6)
	assert(processA("{{},{}}") == 5)
	assert(processA("{{{},{},{{}}}}") == 16)
	assert(processA("{<a>,<a>,<a>,<a>}") == 1)
	assert(processA("{{<ab>},{<ab>},{<ab>},{<ab>}}") == 9)
	assert(processA("{{<!!>},{<!!>},{<!!>},{<!!>}}") == 9)
	assert(processA("{{<a!>},{<a!>},{<a!>},{<ab>}}") == 3)
	println(processA(input))
}

fun partB(input : String) {
	assert(processB("<>") == 0)
	assert(processB("<random characters>") == 17)
	assert(processB("<<<<>") == 3)
	assert(processB("<{!>}>") == 2)
	assert(processB("<!!>") == 0)
	assert(processB("<!!!>>") == 0)
	assert(processB("<{o\"i!a,<{i<a>") == 10)
	println(processB(input))
}

fun processA(str: String): Int {
	var garbageMode = false
	var skip = false
	var depth = 0
	var score = 0

	for (it in str) {
		if (skip || it.toChar() == '!') {
			skip = !skip
			continue
		}

		if (!garbageMode) {
			when (it.toChar()) {
				'{' -> depth++
				'}' -> score += depth--
				'<' -> garbageMode = true
			}
		} else if (it.compareTo('>') == 0) {
			garbageMode = false
		}
	}
	return score
}

fun processB(str: String): Int {
	var garbageMode = false
	var skip = false
	var score = 0

	for (it in str) {
		if (skip || it.toChar() == '!') {
			skip = !skip
			continue
		}

		if (!garbageMode) {
			garbageMode = it.toChar() == '<'
		} else if (it.compareTo('>') == 0) {
			garbageMode = false
		} else {
			score++
		}
	}
	return score
}

