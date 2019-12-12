package codeLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Multi-Variable Optimization program that utilizes Yuelong's Gradient
 * Descend. It has great performance in most ranges, and costs minimal
 * iterations. The program strives for efficiency, accuracy, and flexibility.
 * <p>
 * Yuelong's gradient descent is a specially generalized version of Newton-Raphsone method that supports
 * optimization for multivariable functions. The method does not need the constant 'a'. This version of the 
 * is designed to be faster in computation time and complexity than the previous one with a mult-variable second 
 * order apporximation model.
 * </p>
 * 
 * @author Yuelong Li
 * @since 09/20/2019
 *
 *@version 3.0
 */
public class GradientDescent2 {

	int maxITS;
	double convergeRange;
	FunctionInterface function;
	PartialDInterface[] partials;
	int iteratedTimes = 0;
	boolean convergent = false;
	double[] cds;
	double[] gradient;
	//Cache for computing gradient 1 subtracted by gradient 0
	double[] gradient1m0;
	// The magnitude of the initial step size
	final double INITIALSTEP = 1;
	final double MAXLEARNINGRATE = 1;
	
	public interface FunctionInterface{
		public double f(double[] v);
	}
	
	public interface PartialDInterface{
		public double p(double[] v, double f);
	}
	
	/**
	 * @param maxITS
	 * @param convergeRange
	 * @param function multivariable function through lambda interface
	 * @param derivative multivariable function with the origininal coordinates and y value as the last input
	 */
	public GradientDescent2(int maxITS, double convergeRange,FunctionInterface function, PartialDInterface... partials) {
		this.maxITS = maxITS;
		this.convergeRange = convergeRange;
		this.function = function;
		this.partials = partials;
	}
	
	/**
	 * The method to initiate gradient descent, which runs until the maximum iteration time has been reached 
	 * or the result have converged. Does not print intermediate vector positions
	 * @param startingPoint
	 * @return whether the result has converged (=>the minimum has been found)
	 */
	public boolean find(double... startingPoint) {
		return find(startingPoint, false);
	}
	
	public interface CDSCallBackInterface{
		public void stepPerformed(double[] cds, double[] gradient);
	}
	
	/**
	 * The method to initiate gradient descent, which runs until the maximum iteration time has been reached 
	 * or the result have converged.
	 * @param startingPoint
	 * @param printStep set printStep to true to print each intermediate input positions
	 * @return whether the result has converged (=>the minimum has been found)
	 */
	public boolean find(double[] startingPoint, boolean printStep, CDSCallBackInterface... callbacks) {
		this.cds = startingPoint.clone();
		gradient = new double[cds.length];
		gradient1m0 = new double[cds.length];
		getGradient(cds);
		double[] lastGradient = this.gradient.clone();
		double[] delta = new double[cds.length];
		double a = INITIALSTEP;
		boolean initializing = true;
		while(iteratedTimes<maxITS) {
			convergent = true;
			if(initializing) {
				for(int i = 0; i<delta.length; i++) 
					delta[i] = (magnitude(gradient)!=0)?gradient[i]*(-a)/magnitude(gradient):0;
				initializing = false;
			}else 
				for(int i = 0; i<delta.length; i++) 
					delta[i] = gradient[i]*(-a);
			// Print the information of this current step, with coordinates before the step has taken place
			if(printStep) System.out.println("Step "+iteratedTimes+" :"+" cds: "+Arrays.toString(cds)+" function value: "+function.f(cds)+" gradient: "
			+Arrays.toString(gradient)+" delta: "+Arrays.toString(delta));
			if(callbacks.length!=0)
				for(CDSCallBackInterface callback:callbacks)
					callback.stepPerformed(cds,gradient);
			// Perform the step
			for(int i = 0; i<cds.length; i++)
				cds[i]+=delta[i];
			getGradient(cds);
			iteratedTimes++;
			if(convergent) break;
			a = getLearningRate(gradient, lastGradient, delta);
			for(int i= 0; i<lastGradient.length; i++)
				lastGradient[i]=gradient[i];
		}
		if(printStep) System.out.println("Step "+iteratedTimes+" :"+" cds: "+Arrays.toString(cds)+" function value: "+function.f(cds)+" gradient: "
		+Arrays.toString(gradient)+" delta: uncomputed");
		return convergent;
	}
	
	/**
	 * Sets the gradient variable to gradient at cords
	 * @param cords
	 */
	private void getGradient(double[] cords) {
		double y = function.f(cords);
		for(int i = 0; i<cds.length; i++) {
			gradient[i]=partials[i].p(cords, y);
			if(Math.abs(gradient[i])>convergeRange)convergent = false;
		}
	}
	
	private double getLearningRate(double[] gradient1, double[] gradient0, double[] delta0) {
		for(int i = 0; i<gradient1.length; i++)
			gradient1m0[i]=gradient1[i]-gradient0[i];
		double aprxDoubleDeriv =dot(gradient1m0, gradient1)/dot(delta0,gradient1);
		double learningRate = 1/Math.abs(aprxDoubleDeriv);
		return (learningRate<MAXLEARNINGRATE)?learningRate:MAXLEARNINGRATE;
	}
	
	private double magnitude(double[] v) {
		return Math.sqrt(dot(v,v));
	}
	
	private double dot(double[] u, double[] v) {
		double result = 0;
		for(int i =0; i<Math.max(u.length, v.length); i++) {
			result+=(i>u.length)?(v[i]*0):(i>v.length)?(u[i]*0):u[i]*v[i];
		}
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

	/**
	 * @return the value of the function at the convergence point
	 */
	public double[] getResult() {
		double[] result = new double[cds.length + 1];
		for (int i = 0; i < cds.length; i++) {
			result[i] = cds[i];
		}
		result[cds.length] = function.f(cds);
		return result;
	}
	
	/**
	 * Expected accuracy and actual accuracy specify and depend on gradient
	 */
	public String toString() {
		return "Descentd Result: " + Arrays.toString(getResult()) + ", Convergent: " + convergent + ", Iterated Times: "
				+ iteratedTimes + ", Gradient Accuracy: "+magnitude(gradient)+"/"+convergeRange;

	}
}
