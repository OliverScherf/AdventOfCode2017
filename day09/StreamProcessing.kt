package day09

import java.io.InputStream
import java.io.File

fun main(args: Array<String>) {
	val stream: InputStream = File("input.txt").inputStream()
	val input = stream.bufferedReader().use { it.readText() }
	
	assert(process("{}") == 1)
	assert(process("{{{}}}") == 6)
	assert(process("{{},{}}") == 5)
	assert(process("{{{},{},{{}}}}") == 16)
	assert(process("{<a>,<a>,<a>,<a>}") == 1)
	assert(process("{{<ab>},{<ab>},{<ab>},{<ab>}}") == 9)
	assert(process("{{<!!>},{<!!>},{<!!>},{<!!>}}") == 9)
	assert(process("{{<a!>},{<a!>},{<a!>},{<ab>}}") == 3)
	println(process(input))
}

fun process(str: String): Int {
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
			garbageMode = false;
		}
	}
	return score;
}
