package day02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Checksum {
	public static void main(String[] args) {
		partA();
		partB();
	}

	private static void partA() {
		int sum = 0;
		File file = new File("input.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (String line; (line = br.readLine()) != null;) {
				String[] numbersString = line.split("\t");
				int[] numbers = new int[numbersString.length];
				for (int i = 0; i < numbersString.length; ++i) {
					numbers[i] = Integer.parseInt(numbersString[i]);
				}
				sum += Arrays.stream(numbers).max().getAsInt() - Arrays.stream(numbers).min().getAsInt();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Sum Part A: " + sum);
	}
	
	private static void partB() {
		int sum = 0;
		File file = new File("input.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (String line; (line = br.readLine()) != null;) {
				String[] numbersString = line.split("\t");
				int[] numbers = new int[numbersString.length];
				for (int i = 0; i < numbersString.length; ++i) {
					numbers[i] = Integer.parseInt(numbersString[i]);
				}
				for (int i = 0; i < numbers.length; ++i) {
					boolean found = false;
					for (int j = 0; j < numbers.length; ++j) {
						if (i != j) {
							if (numbers[i] % numbers[j] == 0) {
								sum += numbers[i] / numbers[j];
								found = true;
								break;
							}
							if (numbers[j] % numbers[i] == 0) {
								sum += numbers[j] / numbers[i];
								found = true;
								break;
							}
						}
					}
					if (found) {
						break;
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Sum Part B: " + sum);
	}
}
