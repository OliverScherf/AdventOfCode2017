package day08

import java.io.InputStream
import java.io.File

val registers = mutableMapOf<String, Int>()

fun main(args: Array<String>) {
	val inputStream: InputStream = File("input.txt").inputStream()
	var test = sequenceOf("b inc 5 if a > 1", "a inc 1 if b < 5", "c dec -10 if a >= 1", "c inc -20 if c == 10")
	partA(test)
	
	inputStream.bufferedReader().useLines { lines ->
		partA(lines)
	}

}

fun partA(lines: Sequence<String>) {
	lines.asIterable()
			.map { it.split(' ') }
			.forEach {
				if (condition(it.subList(4, 7))) {
					registers.putIfAbsent(it[0], 0)
					registers.putIfAbsent(it[0], 4)
					if (it[1].equals("inc")) {
						registers += it[0] to (registers[it[0]]!!.toInt() + it[2].toInt())
					} else {
						registers += it[0] to (registers[it[0]]!!.toInt() - it[2].toInt())
					}
				}
			}
	println(registers.maxBy { it.value })
}

fun condition(cond: List<String>): Boolean {
	val reg = cond[0]
	registers.putIfAbsent(reg, 0)
	val value = registers[reg]!!.toInt()
	println(value)
	when (cond[1]) {
		">"  -> return value > cond[2].toInt()
		">=" -> return value >= cond[2].toInt()
		"<"  -> return value < cond[2].toInt()
		"<=" -> return value <= cond[2].toInt()
		"==" -> return value == cond[2].toInt()
		"!=" -> return value != cond[2].toInt()
		else -> println("unknown operation: ${cond[1]}")
	}
	return false
}