package testers;
import java.util.List;

import codeLibrary.*;

public class GradientDescendTester extends Tester{
	static GradientDescent gd;
	
	static int size = 3;
	static int m = 100;
	public static void main(String[] args) {
		for(int i = 0; i<m; i++)trainingSet[i] = 0.02*i;
		gd = new GradientDescent(500,(var)->lossFunction(var));
		
		gd.find(Algorithms.getRandomArray(size, 0, 1));
		
		Console.println(gd);
		
		double[] result =  gd.getResult();
		for(int i =0; i<result.length - 1; i++) {
			Console.println(String.valueOf((char)('a'+i))+" = "+result[i]);
		}
		Console.println("result" + " = " + result[result.length-1]);
	}
	
	static double[] trainingSet = new double[m];
	
	public static double lossFunction(double[] var) {
		double loss = 0;
		for(double co : trainingSet)loss+=Math.pow(knownFunction(co)-modelFunction(co, var),2);
		return Math.sqrt(loss);
	}
	
	public static double knownFunction(double x) {
		double val = 0;
		double[] coef = new double[]{-12,2,-7};
		for(int i = 0; i< coef.length; i++) {
			val+=coef[i]*Math.pow(x, i);
		}
		return val;
	}
	
	//var[7]*sigmoid(var[2]*sigmoid((cd+var[1])*var[0])+var[3]+var[6]*sigmoid((cd+var[5])*var[4]))+var[8]
	public static double modelFunction(double cd, double... var) {
		double val = 0;
		for(int i = 0; i< var.length; i++) {
			val+=var[i]*Math.pow(cd, i);
		}
		return val;
	}
	
	public static List<double[]> getInterPoints(){
		main(new String[0]);
		return gd.getPoints();
	}
	
	public static double sigmoid(double a) {
		return 1.0/(Math.exp(a)+1);
	}
}
