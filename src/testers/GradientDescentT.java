package testers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import codeLibrary.Algorithms;
import codeLibrary.Console;

/**
 * A testing version for Gradient Descent
 * <p>
 * Yuelong's gradient descend is an optimization method that uses dynamic
 * definition for dx and enhanced Newton's method.
 * </p>
 * 
 * @author Yuelong Li
 *
 *@version beta 2
 *
 *@see codeLibrary.GradientDescent
 */
public class GradientDescentT {
	int size = 1;
	int iteratedTimes = 0;
	// the most time gradients can be performed
	int maxITS = 100;
	double lowestValue;
	/**
	 * dynamic definition of zero that changes as the coordinate approaches minimum
	 */
	private double ZERO = 0.000001;
	private double ZERO2 = 0.0001;
	final double Zero1;
	final double PSIX = 0.06;
	/**
	 * @param cds
	 *            current coordinates of independent variables
	 */
	double[] cds;
	/**
	 * @param fps
	 *            Derivatives array
	 */
	double[] fps = new double[2];;
	double[] gradient;

	/**
	 * indicates whether the result is convergent and reliable
	 */
	public boolean convergent;

	/**
	 * Searches for the value closest to 0 when set to find zero mode, other wise it
	 * searches for the minimum
	 */
	boolean findZero;
	
	FunctionInterface fInterface;

	protected double function(double... coord) {
		return fInterface.function(coord);
	};
	
	public interface FunctionInterface{
		public double function(double... coords);
	}

	public GradientDescentT(int maxITS, FunctionInterface... fInterface) {
		this.maxITS = maxITS;
		this.Zero1 = 0.000001;
		this.fInterface = fInterface[0];
	}

	public GradientDescentT(int maxITS, double convergeRange,FunctionInterface... fInterface) {
		this.maxITS = maxITS;
		this.Zero1 = convergeRange;
		this.fInterface = fInterface[0];
	}

	/**
	 * finds the lowest point on the graph
	 * 
	 * @return whether the function is convergent
	 */
	public boolean find(double[] orgCoordinates, boolean... isFindingZero) {
		this.cds = orgCoordinates.clone();
		
		//dimension of the coordinates
		int length = orgCoordinates.length+1;
		//Adding initialPointw
		double[] temp = new double[length];
		for(int i = 0; i<cds.length; i++)temp[i] = cds[i];
		temp[length-1] = function(cds);
		points.add(temp);
		
		if(isFindingZero.length!=0)findZero = isFindingZero[0];
		gradient = new double[cds.length];
		for (int i = 0; i < maxITS; i++) {
			Console.println("Iteration" + i);
			double[] point = new double[length]; // intermediate point
			boolean converge = true;
			getGradient();
			Console.println("Gradients" + Arrays.toString(gradient));
			for (int a = 0; a < cds.length; a++) {
				double cd = cds[a];
				if (!Double.isNaN(gradient[a]))
					cd -= gradient[a];
				cds[a] = cd;
				if (isInvalid()) {
					Console.println(a);
					while (isInvalid()) {
						cd += 0.01 * Math.signum(gradient[a]);
						cds[a] = cd;
					}
					cds[a] = cd;
				}
				if (Math.abs(gradient[a]) > Zero1)
					converge = false;
				point[a] = cds[a];
			}
			point[length-1] = function(cds);
			points.add(point);
			Console.println("cds" + Arrays.toString(cds));
			Console.println();
			iteratedTimes++;
			if (converge)
				break;
		}
		return convergent;
	}

	protected void setZero(double numericSample) {
		int exponent = (int) Math.round(0.11536 * Math.log(100.58 + numericSample) - 6.8068);
		this.ZERO = Math.pow(10, exponent);
	}

	protected void setZero2(double numericSample) {
		int exponent = (int) Math.round(0.11536 * Math.log(100.58 + numericSample) - 6.8068);
		this.ZERO2 = Math.pow(10, exponent);
	}

	protected boolean isInvalid() {
		return Double.isNaN(function(cds));
	}

	/**
	 * 
	 * @return the curved gradient at coordinate cds
	 * @param cds
	 *            coordinates of the current point
	 */
	private void getGradient() {
		convergent = true;
		setZero(function(cds));
		for (int i = 0; i < cds.length; i++) {
			fps(i);
			gradient[i] = fps[0];
			if ((int) fps[0] != 0)
				convergent = false;
			if(findZero)gradient[i]/=fps[1];
		}
		
		if(!findZero) {
			fps[1] = getDbDeriv(cds, gradient);
			double curvedDD = (Math.abs(fps[1]) < 0.5) ? 0.5 : Math.abs(fps[1]);
			Console.println("curvedFDP" + curvedDD);
			for(int i =0; i< cds.length; i++) {
				gradient[i]/=curvedDD;
			}
		}
		
	}

	/**
	 * finds the f prime f double prime and f triple prime of the assigned variable
	 * 
	 * @return big decimal value of first to the third derivatives
	 * @param variableIndex
	 *            specifies the variable of which the partial dirivative is computed
	 * @param coordinate
	 *            the coordinate of the variable specified
	 */
	private void fps(int variableIndex) {
		if (findZero) {
			fps[0] = function(cds);
			fps[1] = getDeriv(cds, variableIndex);
		} else {
			fps[0] = getDeriv(cds, variableIndex);
		}
	}

	private double getDeriv(double[] cds, int variableIndex) {
		double[] cds1 = new double[cds.length];
		for (int i = 0; i < cds.length; i++) {
			cds1[i] = cds[i];
		}
		cds1[variableIndex] -= ZERO;
		return (function(cds) - function(cds1)) / ZERO;
	}
	
	private double getDeriv(double[] cds, double[] direction) {
		double[] cds1 = new double[cds.length];
		double mag = Algorithms.magnitude(direction);
		setZero(function(cds));
		for (int i = 0; i < cds.length; i++) {
			cds1[i] = cds[i] - direction[i]/mag *ZERO;
		}
		return (function(cds) - function(cds1)) / ZERO;
	}

	private double getDbDeriv(double[] cds, double[] direction) {
		double[] cds1 = new double[cds.length];
		double mag = Algorithms.magnitude(direction);
		setZero2(getDeriv(cds,direction));
		for (int i = 0; i < cds.length; i++) {
			cds1[i] = cds[i] - direction[i]/mag*ZERO2;
		}
		return (getDeriv(cds, direction) - getDeriv(cds1, direction)) / ZERO2;
	}

	/**
	 * 
	 * @return the value of the function at the lowest point
	 */
	public double[] getResult() {
		double[] result = new double[cds.length + 1];
		for (int i = 0; i < cds.length; i++) {
			result[i] = cds[i];
		}
		result[cds.length] = function(cds);
		return result;
	}
	
	
	private List<double[]> points = new ArrayList<double[]>();
	
	/**
	 * Finds the intermediate points used to find the minimum value
	 * @return A list comprising the coordinates of those intermediate points
	 */
	public List<double[]> getPoints(){
		return points;
	}

	public String toString() {
		return "Descentd Result: " + Arrays.toString(getResult()) + ", Convergent: " + convergent + ", Iterated Times: "
				+ iteratedTimes + ", Gradient Accuracy: " + Zero1;

	}
}
/*
 * © Copyright 2017 Cannot be used without authorization
 */

