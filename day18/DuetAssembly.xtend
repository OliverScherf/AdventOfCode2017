package day18

import java.nio.file.Files
import java.nio.file.Paths
import java.util.Map
import java.util.HashMap

class DuetAssembly {

	private var pc = 0;
	private var String[] code;
	private var Map<Character, Long> registers = new HashMap<Character, Long>();
	private var lastStackSymbol = 0L

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
			println(lastStackSymbol)
		}
	}

	def execSet(String[] inst) {
		if (isDigit(inst.get(2))) {
			registers.put(inst.get(1).toChar, inst.get(2).toLong);
		} else {
			registers.put(inst.get(1).toChar, registers.get(inst.get(2).toChar).zeroIfNull);
		}
		pc++
	}
	
	def execSnd(String[] inst) {
		lastStackSymbol = registers.get(inst.get(1).toChar).zeroIfNull
		pc++
	}

	def execMod(String[] inst) {
		var oldValue = registers.get(inst.get(1).toChar).zeroIfNull
		if (isDigit(inst.get(2))) {
			registers.put(inst.get(1).toChar, oldValue % inst.get(2).toLong)
		} else {
			var regValue = registers.get(inst.get(2).toChar).zeroIfNull
			registers.put(inst.get(1).toChar, oldValue % regValue)
		}
		pc++
	}

	def execRcv(String[] inst) {
		if (!registers.get(inst.get(1).toChar).zeroIfNull.equals(0)) {
			pc = -1 // stop the program
		}
		pc++
	}

	def execJgz(String[] inst) {
		try {
			var oldValue = registers.get(inst.get(1).toChar).zeroIfNull
			if (Long.compare(oldValue, 0L) > 0) {
				if (isDigit(inst.get(2))) {
					pc += inst.get(2).toInt
				} else {
					pc += registers.get(inst.get(2).toChar).zeroIfNull.toInt
				}
			} else {
				pc++;
			}
		} catch (Exception e) {
			e.printStackTrace
			pc = 1000;
		}
	}

	def execMul(String[] inst) {
		var oldValue = registers.get(inst.get(1).toChar).zeroIfNull
		if (isDigit(inst.get(2))) {
			registers.put(inst.get(1).toChar, oldValue * inst.get(2).toInt)
		} else {
			var regValue = registers.get(inst.get(2).toChar)
			registers.put(inst.get(1).toChar, oldValue * regValue)
		}
		pc++
	}
	
	def execAdd(String[] inst) {
		var oldValue = registers.get(inst.get(1).toChar).zeroIfNull
		if (isDigit(inst.get(2))) {
			registers.put(inst.get(1).toChar, oldValue + inst.get(2).toInt)
		} else {
			var regValue = registers.get(inst.get(2).toChar).zeroIfNull
			registers.put(inst.get(1).toChar, oldValue + regValue)
		}
		pc++
	}
	
	def zeroIfNull(Long i) {
		return if (i === null) 0L else i
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

	def isDigit(String s) {
		try {
			s.toLong
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
