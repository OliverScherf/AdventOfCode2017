package day04;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;

public class PasswordChecker {

	public static void main(String[] args) {
		partA();
		partB();
	}

	private static void partA() {
		HashSet<String> wordsSet = new HashSet<>();
		File file = new File("input.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			int validPassPhrases = 0;
			for (String line; (line = br.readLine()) != null;) {
				String[] wordsOnLine = line.split(" ");
				boolean valid = true;
				for (String word : wordsOnLine) {
					if (!wordsSet.contains(word)) {
						wordsSet.add(word);
					} else {
						System.out.println("DUPLICATE: " + word);;
						valid = false;
						break;
					}
				}
				if (valid) {
					validPassPhrases++;				
				}
				wordsSet.clear();
			}
			System.out.println("Valid Passphrases Part A: " + validPassPhrases);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void partB() {
		HashSet<String> wordsSet = new HashSet<>();
		File file = new File("input.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			int validPassPhrases = 0;
			for (String line; (line = br.readLine()) != null;) {
				String[] wordsOnLine = line.split(" ");
				boolean valid = true;
				for (String word : wordsOnLine) {
					char[] sortedWord = word.toCharArray(); 
					Arrays.sort(sortedWord);
					
					if (!wordsSet.contains(String.valueOf(sortedWord))) {
						wordsSet.add(String.valueOf(sortedWord));
					} else {
						System.out.println("DUPLICATE: " + String.valueOf(sortedWord));;
						valid = false;
						break;
					}
				}
				if (valid) {
					validPassPhrases++;				
				}
				wordsSet.clear();
			}
			System.out.println("Valid Passphrases Part B: " + validPassPhrases);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
