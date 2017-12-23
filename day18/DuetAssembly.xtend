package day18

import java.nio.file.Files
import java.nio.file.Paths
import java.util.Map
import java.util.HashMap

class DuetAssembly {

	private var pc = 0;
	private var String[] code;
	private var Map<Character, Long> registers = new HashMap<Character, Long>();
	private var lastSound = 0L

	def static void main(String[] args) {
		val input = new String(Files.readAllBytes(Paths.get("input.txt")));
		val lines = input.split('\n')
		new DuetAssembly(lines).partA();
	}

	new(String[] code) {
		this.code = code
	}

	def partA() {
		try {
			while (true) {
				var instruction = code.get(pc).split(' ')
				switch (instruction.get(0)) {
					case "set": execSet(instruction)
					case "snd": execSnd(instruction)
					case "mod": execMod(instruction)
					case "rcv": execRcv(instruction)
					case "jgz": execJgz(instruction)
					case "mul": execMul(instruction)
					case "add": execAdd(instruction)
					default: println("unknown: " + instruction.get(0))
				}
			}
		} catch (Exception e) {
			println(lastSound)
		}
	}

	def execSet(String[] inst) {
		registers.put(inst.get(1).toChar, value(inst.get(2)))
		pc++
	}
	
	def execSnd(String[] inst) {
		lastSound = registers.get(inst.get(1).toChar).zeroIfNull
		pc++
	}

	def execMod(String[] inst) {
		var oldValue = value(inst.get(1))
		registers.put(inst.get(1).toChar, oldValue % value(inst.get(2)))
		pc++
	}

	def execRcv(String[] inst) {
		if (!value(inst.get(1)).equals(0)) {
			pc = -1 // stop the program
		}
		pc++
	}

	def execJgz(String[] inst) {
		var oldValue = value(inst.get(1))
		if (Long.compare(oldValue, 0L) > 0L) {
			pc += value(inst.get(2)).toInt
		} else {
			pc++
		}
	}

	def execMul(String[] inst) {
		var oldValue = value(inst.get(1))
		registers.put(inst.get(1).toChar, oldValue * value(inst.get(2)))
		pc++
	}

	def execAdd(String[] inst) {
		var oldValue = value(inst.get(1))
		registers.put(inst.get(1).toChar, oldValue + value(inst.get(2)))
		pc++
	}

	def zeroIfNull(Long i) {
		return if(i === null) 0L else i
	}

	def toInt(String s) {
		return Integer.parseInt(s)
	}

	def toInt(Long l) {
		return l.intValue
	}

	def toLong(String s) {
		return Long.parseLong(s)
	}

	def toChar(String s) {
		return s.charAt(0)
	}

	def value(String s) {
		if (isDigit(s)) {
			return Long.parseLong(s)
		} else {
			return registers.get(s.toChar).zeroIfNull
		}
	}

	def isDigit(String s) {
		try {
			s.toLong
			return true
		} catch (Exception e) {
			return false
		}
	}

}
