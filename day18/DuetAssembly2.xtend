package day18

import java.nio.file.Files
import java.nio.file.Paths
import java.util.Map
import java.util.HashMap
import java.util.LinkedList
import java.util.concurrent.Semaphore

class DuetAssembly2 extends Thread {

	private var String[] code
	private var pc = 0
	private var Map<Character, Long> registers = new HashMap<Character, Long>()
	private var DuetAssembly2 counterProgram
	private var queue = new LinkedList<Long>()
	private var semaphore = new Semaphore(0)
	private var mutex = new Semaphore(1)
	private var blocked = false
	private var sends = 0L

	def static void main(String[] args) {
		val input = new String(Files.readAllBytes(Paths.get("input.txt")))
		val lines = input.split('\n')
		var p0 = new DuetAssembly2(lines, 0L)
		var p1 = new DuetAssembly2(lines, 1L)
		p0.daemon = true
		p1.daemon = true
		p0.counterProgram = p1
		p1.counterProgram = p0

		p0.start
		p1.start
		while (true) {
			synchronized (p0) {
				p0.wait(100)
			}
			synchronized (p1) {
				p1.wait(100)
			}
			if (p0.blocked && p1.blocked) {
				println("Deadlock detected.");
				println('''p0 sends «p0.sends» p1 sends «p1.sends»''')
				return
			}
			synchronized (p0) {
				p0.notify
			}
			synchronized (p1) {
				p1.notify
			}
		}
	}

	override run() {
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
	}

	new(String[] code, Long p) {
		registers.put('p', p)
		this.code = code
	}

	def execSet(String[] inst) {
		registers.put(inst.get(1).toChar, value(inst.get(2)))
		pc++
	}

	def execMod(String[] inst) {
		var oldValue = value(inst.get(1))
		registers.put(inst.get(1).toChar, oldValue % value(inst.get(2)))
		pc++
	}

	def execSnd(String[] inst) {
		sends++
		add(value(inst.get(1)))
		semaphore.release
		pc++
	}

	def execRcv(String[] inst) {
		counterProgram.blocked = true
		counterProgram.semaphore.acquire
		counterProgram.blocked = false
		var l = remove
		registers.put(inst.get(1).toChar, l)
		pc++
	}

	def add(Long l) {
		mutex.acquire
		queue.add(l)
		mutex.release
	}

	def remove() {
		counterProgram.mutex.acquire
		var l = counterProgram.queue.remove()
		counterProgram.mutex.release
		return l
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
