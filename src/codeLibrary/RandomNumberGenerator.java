package codeLibrary;

import java.util.ArrayList;

public class RandomNumberGenerator {
	
	public static void main(String[] args) {
		ArrayList<Integer> series = new ArrayList<Integer> (100);
		for(int i = 0; i < 100; i++) {
			series.add(i+1);
		}
		ArrayList<Integer> output = new ArrayList<Integer> (100);
		for(int j = 0; j < 20; j++) {
			int r = (int)(Math.random()*(100-j));
			output.add(series.remove(r));
		}
		output.sort((Integer a, Integer b)->a.compareTo(b));
		for(int k = 0; k < 20; k++) {
			System.out.println(k+1+": "+output.get(k));
		}
	}
}
