package testers;

import java.util.Arrays;

import codeLibrary.Algorithms;
import codeLibrary.Console;

public class BucketSortDemo {
	public static void main(String[] args) {
		while(true) {
			try {
				String[] st = Console.getList();
				double[] el = new double[st.length];
				for(int i = 0; i<st.length; i++) el[i] = Double.valueOf(st[i]);
				Algorithms.yuelongSort(el);
				Console.println(Arrays.toString(el));

			}catch(Exception e) {
				e.printStackTrace();
				continue;}
		}
	}
}
