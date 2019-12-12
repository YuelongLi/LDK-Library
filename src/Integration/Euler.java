package Integration;

import java.util.ArrayList;

import codeLibrary.*;

public class Euler {
	static double c1=0.1,
			c2 = 2,
			L=1.0/7,
			G=0.7,
			x=1.5305,
			y=-4.36956,
			z=-0.15034,
			t=0,
			dt = 0.01;
	static public ArrayList<double[]> curve = new ArrayList<double[]> ();
	static double dex,dey,dez;
	static ArrayList<Double> xs = new ArrayList<Double>();
	public static void main1(String[] args){
		
		t=0;
		x= 0;
		y = 1;
		z= 0;
		while(t<100){
			euler(dt);
			curve.add(0,new double[] {x,y,z});
		}
	}
	public static void euler(double dt){
			x = 2*t;
			y = Math.sin(t*t);;
			z = Math.exp(t);
			t+=dt;
	}
	public static double g(double x){
		return Math.cos(x);
	}
}

