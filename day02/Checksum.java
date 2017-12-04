package day02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Checksum {
	public static void main(String[] args) {
		int sum = 0;
		File file = new File("input.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (String line; (line = br.readLine()) != null;) {
				String[] numbersString = line.split("\t");
				int[] numbers = new int[numbersString.length];
				for (int i = 0; i < numbersString.length; ++i) {
					numbers[i] = Integer.parseInt(numbersString[i]);
					System.out.print(numbers[i]);
					System.out.print(" ");
				}
				sum += Arrays.stream(numbers).max().getAsInt() - Arrays.stream(numbers).min().getAsInt();
				System.out.println();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(sum);
	}
}
