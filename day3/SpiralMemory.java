package day3;

import java.util.Scanner;

public class SpiralMemory {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int zahl = s.nextInt();
		s.close();
		
		int elems = 1;
		int elemsRingBefore = 0;
		int ring = 1;
		while (true) {
			elems += elemsRingBefore + 8;
			elemsRingBefore += 8;
			if (elems >= zahl) {
				break;
			}
			ring++;
		}
		System.out.println("Total elements: " + elems);
		System.out.println("Current (Spiral) Ring: " + ring);
		
		int distanceHighestNumberOfRing = elems + 1 - zahl;
		int elemPerSize = (elemsRingBefore + 4) /  4;
		
		int middle = elemPerSize / 2 + 1;
		System.out.println("Abstand: " + distanceHighestNumberOfRing);
		if (distanceHighestNumberOfRing < elemPerSize) {
			System.out.println("Go up: " + (distanceHighestNumberOfRing - middle));
		}
		else if (distanceHighestNumberOfRing < elemPerSize * 2 - 1) {
			System.out.println("Go down: " + (distanceHighestNumberOfRing - (elemPerSize - 1) - middle));
		}
		else if (distanceHighestNumberOfRing < elemPerSize * 3 - 2) {
			System.out.println("Go left: " + (distanceHighestNumberOfRing - ((elemPerSize - 1) * 2) - middle));
		}
		else if (distanceHighestNumberOfRing < elemPerSize * 4 - 3) {
			System.out.println("Go up: " + (distanceHighestNumberOfRing - ((elemPerSize - 1) * 3) - middle));
		}
		System.out.println("Go in direction of the middle: " + ring);
	}

}
