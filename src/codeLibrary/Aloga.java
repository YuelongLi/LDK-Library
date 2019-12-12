package codeLibrary;

import java.util.*;
import java.math.*;
public class Aloga {
	// heap sort
	public static void heapSort(int... list){
		Heap heap = new Heap(1,3,4,2,3,5,1,8,6);
		Console.println(heap.getOrder());
	}
	public static int[] insertionSort(int... list){
		for(int i = 1; i<list.length; i++){
			for(int a = i; list[a-1]>list[a]; a--){
				int pass = list[a];
				list[a] = list[a-1];
				list[a-1] = pass;
				if(a==1)break;
			}
		}
		return list;
	}
	
	public static int[] mergeSort(int[] list){
		return mergeSortRecur(list, 0, list.length);
	}
	public static int[] mergeSortRecur(int[] list,int startIndex, int size){
		int childrenSize = size/2;
		int[] result = new int[size];
		Console.println(size);
		if(size>2){
			int[] a = mergeSortRecur(list, startIndex, childrenSize);
			int[] b = mergeSortRecur(list, startIndex+childrenSize, size-childrenSize);
			int cursea=0;
			int curseb=0;
			for(int i = 0; i<size; i++){
				if(a[cursea]<b[curseb]){
					result[cursea+curseb]=a[cursea];
					cursea+=1;
				}else{
					result[cursea+curseb]=b[curseb];
					curseb+=1;
				}
			}
		}else{
			Console.println("SIZE"+size);
			Console.println("START"+startIndex);
			if(size<=1){
				if(size==1)result[0]=list[startIndex];
			}
			else{
				if(list[startIndex]<list[startIndex+1]){
					result[0]=list[startIndex];
					result[1]=list[startIndex+1];
				}else{
					result[1]=list[startIndex];
					result[0]=list[startIndex+1];
				}
			}
		}
		return result;
	}
	
	public static double getMax(List<Double> a){
		double b = Integer.MIN_VALUE;
		for(int i = 0; i<a.size(); i++){
			if(a.get(i) > b) b = a.get(i);
		}
		return b;
	}
	public static double getMin(List<Double> a){
		double b = Double.MAX_VALUE;
		for(int i = 0; i<a.size(); i++){
			if(a.get(i) < b) b = a.get(i);
		}
		return b;
	}
	/**
	 * Returns a vector that contains the max value of columns of the vector
	 * @param a row: f(x), column: function value vector
	 * @return a vector that contains the max value of columns of the vector
	 */
	public static LinkedList<Double> getMatrixMax(List<ArrayList<Double>> a){
		LinkedList<Double> maxValues=new LinkedList<Double>();
		for(List<Double> row:a){
			maxValues.add(getMax(row));
		}
		return maxValues;
	}
	/**
	 * Returns a vector that contains the min value of columns of the vector
	 * @param a row: f(x), column: function value vector
	 * @return a vector that contains the min value of columns of the vector
	 */
	public static LinkedList<Double> getMatrixMin(List<ArrayList<Double>> a){
		LinkedList<Double> minValues = new LinkedList<Double>();
		for(List<Double> row:a){
			minValues.add(getMin(row));
		}
		return minValues;
	}
	
	private static final BigDecimal SQRT_DIG = new BigDecimal(10);
	private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

	/**
	 * Private utility method used to compute the square root of a BigDecimal.
	 * 
	 * @author Luciano Culacciatti 
	 * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
	 */
	private static BigDecimal sqrtNewtonRaphson  (BigDecimal c, BigDecimal xn, BigDecimal precision){
	    BigDecimal fx = xn.pow(2).add(c.negate());
	    BigDecimal fpx = xn.multiply(new BigDecimal(2));
	    BigDecimal xn1 = fx.divide(fpx,2*SQRT_DIG.intValue(),RoundingMode.HALF_DOWN);
	    xn1 = xn.add(xn1.negate());
	    BigDecimal currentSquare = xn1.pow(2);
	    BigDecimal currentPrecision = currentSquare.subtract(c);
	    currentPrecision = currentPrecision.abs();
	    
	    if (currentPrecision.compareTo(precision) <= -1){
	        return xn1;
	    }
	    return sqrtNewtonRaphson(c, xn1, precision);
	}

	/**
	 * Uses Newton Raphson to compute the square root of a BigDecimal.
	 * 
	 * @author Luciano Culacciatti 
	 * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
	 */
	public static BigDecimal bigSqrt(BigDecimal c){
	    return sqrtNewtonRaphson(c,new BigDecimal(1),new BigDecimal(1).divide(SQRT_PRE));
	}
}
/**
 * @version 1.0
 * @author Yuelong
 */
//The default type of heap is max
class Heap{
	int[] list;
	int[] order;
	final int Max = 0;
	final int Min = 1;
	public Heap(int... list){
		this.list = list;
		order = new int[list.length];
		for(int i = 0; i < list.length; i++)order[i] = i;
		heapify(this.Max);
	}
	public void heapify(int type){
		
	}
	public void merge(){
		
	}
	public int[] toArray(){
		return list;
	}
	public String toArrayString(){
		return Arrays.toString(list);
	}
	public int[] getOrder(){
		return order;
	}
	void refresh(){
		
	}
}