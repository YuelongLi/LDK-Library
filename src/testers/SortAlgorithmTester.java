package testers;
import codeLibrary.*;

import java.io.IOException;
import java.util.*;

public class SortAlgorithmTester extends Tester{
	static long start, end;
	static Tester This = new Tester();
	static ArrayList<Integer> holder = new ArrayList<Integer>();
	public static void main(String[] args){
		int[] sizes = new int[]{1,2,10,100,1000,10000,20000,100000,200000,1000000,2000000,10000000};
		String[] sortingMeth = new String[]{"Insertion","Bucket","Quick"};
		try {
			CSV testResult = new CSV("SortResult-Yuelong Sort Implementation Version3.csv",false);
			testResult.createNewFile();
			ArrayList<ArrayList<String>> chart = testResult.getChart();
			ArrayList<String> firstRow = new ArrayList<String>();
			firstRow.add("#");
			firstRow.add("Array size");
			firstRow.add("Array range");
			firstRow.add("Sorting method");
			firstRow.add("Consumedd time");
			firstRow.add("Exceptions");
			chart.add(firstRow);
			for(int i = 1; i<= sizes.length; i++){
				int maxSize = sizes[i-1];
				List<Integer> randomList = Algorithms.getRandomIntegers(sizes[i-1], sizes[i-1],0);
				for(int c = 0; c<3; c++){
					ArrayList<String> row = new ArrayList<String>();
					if(c==0){
						row.add(String.valueOf(i));
						row.add(String.valueOf(sizes[i-1]));
						row.add("0 - "+sizes[i-1]);
					}else{
						row.add("");
						row.add("");
						row.add("");
					}
					row.add(sortingMeth[c]);
					try{
						switch(c){
						case 0:
							if(maxSize<200000){
								timerStart();
								randomList = Algorithms.insertionSort(randomList);
								timerStop();								
								row.add(timerReset(sortingMeth[c]));
							}else {
								
								row.add("N/A");
							}
							break;
						case 1:
							double[] list = new double[maxSize];
							for(int o = 0; o<maxSize; o++)list[o]=randomList.get(o);
							timerStart();
							list = Algorithms.yuelongSort(list);
							timerStop();							
							row.add(timerReset(sortingMeth[c]));
							break;
						case 2:
							int[] list1 = new int[maxSize];
							for(int o = 0; o<maxSize; o++)list1[o]=randomList.get(o);
							timerStart();
							list1 = quickSort(list1);
							timerStop();
							row.add(timerReset(sortingMeth[c]));
							break;
						}
					}catch(Exception e){
						row.add(e.getMessage());
					}	
					chart.add(row);
				}
				maxSize = sizes[i-1];
				randomList = Algorithms.getRandomIntegers(sizes[i-1], sizes[i-1]*5,sizes[i-1]);
				for(int c = 0; c<3; c++){
					ArrayList<String> row = new ArrayList<String>();
					if(c==0){
						row.add(String.valueOf(i));
						row.add(String.valueOf(sizes[i-1]));
						row.add(sizes[i-1]+" - "+sizes[i-1]*5);
					}else{
						row.add("");
						row.add("");
						row.add("");
					}
					row.add(sortingMeth[c]);
					try{
						switch(c){
						case 0:
							if(maxSize<100000){
								timerStart();
								randomList = Algorithms.insertionSort(randomList);
								timerStop();								
								row.add(timerReset(sortingMeth[c]));
							}else {
								
								row.add("N/A");
							}
							break;
						case 1:
							double[] list = new double[maxSize];
							for(int o = 0; o<maxSize; o++)list[o]=randomList.get(o);
							timerStart();
							list = Algorithms.yuelongSort(list);
							timerStop();							
							row.add(timerReset(sortingMeth[c]));
							break;
						case 2:
							int[] list1 = new int[maxSize];
							for(int o = 0; o<maxSize; o++)list1[o]=randomList.get(o);
							timerStart();
							list1 = quickSort(list1);
							timerStop();
							row.add(timerReset(sortingMeth[c]));
							break;
						}
					}catch(Exception e){
						row.add(e.getMessage());
					}	
					chart.add(row);
				}
			}
			testResult.flushChart();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static int[] quickSort(int[] input)
	 {
	  HammerMethod(input,0,input.length-1);
	  return input;
	 }
	public static void HammerMethod(int[] input, int ini, int fin)
	 {
	  if(fin-ini==0)//Exit if already down to one integer.
	   return;
	  int counter1 = ini;//First counter starting from the given initializing index.
	  int counter2 = fin;//Second counter starting from the given ending index.
	  int pivot = (input[ini]+input[fin])/2;//The average of the terminal index values.
	  while(counter1<counter2)//While valid.
	  {
	   while(input[counter1]<pivot)//Accumulate the first counter if valid.
	    counter1++;
	   while(input[counter2]>pivot)//Deduct the second counter if valid.
	    counter2--;
	   if(counter1<=counter2)
	   {
	    int temp = input[counter1];
	    input[counter1] = input[counter2];
	    input[counter2] = temp;
	    counter1++;
	    counter2--;
	   }
	  }
	  if(counter1<fin)
	   HammerMethod(input,counter1,fin);
	  if(counter2>ini)
	   HammerMethod(input,ini,counter2);
	 }
}