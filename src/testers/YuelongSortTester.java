package testers;

import java.util.List;

import codeLibrary.*;

public class YuelongSortTester extends Tester{
	public static void main(String[] args) {
		List<Integer> a = Algorithms.getRandomIntegers(5, 1, 7);
		double[] orgList = new double[a.size()];
		for(int i = 0; i < a.size(); i++) orgList[i] = a.get(i);
		Console.println(Algorithms.yuelongSort(orgList));
	}
}
