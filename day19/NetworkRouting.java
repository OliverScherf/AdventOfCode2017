package day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NetworkRouting {

	enum Dir {
		NORTH, EAST, SOUTH, WEST;
	}

	class Index {
		public int x = 0;
		public int y = 0;

		Index(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "x: " + x + ", y: " + y;
		}

	}

	private String[] map = null;
	private String result = "";
	private Dir dir = Dir.SOUTH;
	private Index idx = new Index(0, 0);

	public static void main(String[] args) {
		new NetworkRouting().partA();
	}

	public NetworkRouting() {
		try {
			String input = new String(Files.readAllBytes(Paths.get("input.txt")));
			map = input.split("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void partA() {
		idx = findStart();
		while (true) {
			char c = next();
			if (c == '+') {
				changeDirection();
			} else if (Character.isLetter(c)) {
				result += c;
			} else if (c == ' ') {
				System.out.println("Finished, result: " + result);
				return;
			}
		}
	}

	private char next() {
		int changeX = 0;
		int changeY = 0;
		if (dir == Dir.EAST)
			changeX = 1;
		else if (dir == Dir.WEST)
			changeX = -1;
		else if (dir == Dir.NORTH)
			changeY = -1;
		else
			changeY = 1;
		idx.x += changeX;
		idx.y += changeY;
		return map[idx.y].charAt(idx.x);
	}

	private char changeDirection() {
		char up = map[idx.y - 1].charAt(idx.x);
		char right = map[idx.y].charAt(idx.x + 1);
		char down = map[idx.y + 1].charAt(idx.x);
		char left = map[idx.y].charAt(idx.x - 1);
		if (dir == Dir.NORTH || dir == Dir.SOUTH) {
			if (left != ' ') {
				dir = Dir.WEST;
				return left;
			}
			if (right != ' ') {
				dir = Dir.EAST;
				return right;
			}
		}
		if (dir == Dir.EAST || dir == Dir.WEST) {
			if (up != ' ') {
				dir = Dir.NORTH;
				return left;
			}
			if (down != ' ') {
				dir = Dir.SOUTH;
				return down;
			}
		}
		return ' ';

	}

	private Index findStart() {
		Index idx = new Index(0, 0);
		while (map[idx.y].charAt(idx.x) != '|') {
			idx.x++;
		}
		return idx;
	}

}
