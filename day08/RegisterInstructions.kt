package day08

import java.io.InputStream
import java.io.File

val registers = mutableMapOf<String, Int>()

fun main(args: Array<String>) {
	var test = sequenceOf("b inc 5 if a > 1", "a inc 1 if b < 5", "c dec -10 if a >= 1", "c inc -20 if c == 10")
	partA(test)
	var inputStream: InputStream = File("input.txt").inputStream()
	inputStream.bufferedReader().useLines { lines ->
		partA(lines)
	}
	
	registers.clear()
	inputStream = File("input.txt").inputStream()
	partB(test)
	inputStream.bufferedReader().useLines { lines ->
		partB(lines)
	}
}

fun partB(lines: Sequence<String>) {
	var max = 0
	lines.asIterable()
			.map { it.split(' ') }
			.forEach {
				if (condition(it.subList(4, 7))) {
					registers.putIfAbsent(it[0], 0)
					if (it[1].equals("inc")) {
						registers += it[0] to (registers[it[0]]!!.toInt() + it[2].toInt())
					} else {
						registers += it[0] to (registers[it[0]]!!.toInt() - it[2].toInt())
					}
					if (max < registers[it[0]]!!.toInt()) {
						max = registers[it[0]]!!.toInt() 
					}
				}
			}
	println(max)
}

fun partA(lines: Sequence<String>) {
	lines.asIterable()
			.map { it.split(' ') }
			.forEach {
				if (condition(it.subList(4, 7))) {
					registers.putIfAbsent(it[0], 0)
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
