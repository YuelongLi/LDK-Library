package testers;

import codeLibrary.Tester;

import java.util.List;

import codeLibrary.Algorithms;
import codeLibrary.Console;

public class VectorTest extends Tester{
	public static void main(String[] args) {
		int len = 1000000;
		double[] a = new double[len], b = new double[len];
		List<Double> c = Algorithms.getRandomDoubles(len, 1, 0);
		List<Double> d = Algorithms.getRandomDoubles(len, 1, 0);
		for(int i = 0; i < len; i++) {
			a[i] = c.get(i);
			b[i] = d.get(i);
		}
		
		double time0 = System.nanoTime();
		Algorithms.dot(a,b);
		double time1 = System.nanoTime();
		Console.println((time1 - time0)/1000000);
	}
}
