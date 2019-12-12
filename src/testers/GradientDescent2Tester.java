package testers;

import codeLibrary.GradientDescent2;

public class GradientDescent2Tester {
	public static void main(String[] args) {
		test3();
	}
	
	public static void test1() {
		GradientDescent2 optimizer = 
				new GradientDescent2(500, 0.0001, (v)->Math.pow(v[2], 4)*(Math.sin(v[0]+v[1])+Math.sin(v[0])), (v,f)->Math.pow(v[2], 4)*(Math.cos(v[0]+v[1])+Math.cos(v[0])), 
						(v,f)->Math.pow(v[2], 4)*Math.cos(v[0]+v[1]),(v,f)->4*Math.pow(v[2], 3)*(Math.sin(v[0]+v[1])+Math.sin(v[0])));
		optimizer.find(new double[] {-0.01,2,1}, true);
		System.out.print(optimizer);
	}
	
	/**
	 * Gradient descent on fourth-order polynomial from near max integer value
	 * @result 107 iterations
	 */
	public static void test2() {
		GradientDescent2 optimizer = new GradientDescent2(500, 0.001, (v)->Math.pow(v[0], 4),(v,f)->4*Math.pow(v[0], 3));
		optimizer.find(new double[] {-1000000000},true);
		System.out.println(optimizer);
	}
	
	public static void test3() {
		GradientDescent2 optimizer = new GradientDescent2(500, 0.001, (v)->Math.pow(Math.abs(v[0]), 1.5),(v,f)->Math.signum(v[0])*1.5*Math.pow(Math.abs(v[0]), 0.5));
		optimizer.find(new double[] {10},true);
		System.out.println(optimizer);
	}
}
