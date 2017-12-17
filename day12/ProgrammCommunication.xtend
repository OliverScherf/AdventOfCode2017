package day12

import java.nio.file.Files
import java.nio.file.Paths
import java.util.ArrayList
import java.util.List
import java.util.HashSet

class Program {
	public var number = 0;
	public var refs = new ArrayList<Integer>();
	
	override toString() {
		'''Number «number», Ref «refs»'''
	}
	
}

class ProgrammCommunication {

	val static programs = new ArrayList<Program>();

	def static void main(String[] args) {
		val input = new String(Files.readAllBytes(Paths.get("input.txt")));
		val lines = input.split('\n')
		partA(lines)
	}
	
	def static partA(String[] lines) {
		lines.forEach [
			val l = it.replaceAll(" <->|,", '').split(' ')
			val p = new Program()
			p.number = Integer.parseInt(l.get(0))
			for (i : 1 ..< l.length) {
				p.refs.add(Integer.parseInt(l.get(i)))
			}
			programs.add(p)
		]

		val solution = new HashSet<Program>
		solution.add(programs.get(0))
		programs.forEach [
			if (isConnectedTo(it, programs.get(0), new ArrayList<Program>())) {
				solution.add(it)
			}
		]
		println(solution.size)
	}

	
	def static boolean isConnectedTo(Program p1, Program p2, List<Program> path) {
		if (p1.refs.contains(p2) || p1.equals(p2)) {
			return true
		}
		if (path.contains(p1)) {
			return false;
		}
		path.add(p1);

		return (p1.refs.map [
			programs.get(it)
		].exists [
			isConnectedTo(it, p2, path) == true
		])
	}

}
