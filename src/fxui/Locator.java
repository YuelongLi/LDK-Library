package fxui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import static codeLibrary.Algorithms.add;
import static codeLibrary.Algorithms.multiply;

import java.awt.Dimension;

/**
 * Locator can be implemented in canvas or other graphing tools to provide a set
 * of dynamic transformations, including resizing, rotating, translating and
 * etc. It helps with graphic designs by converting a ui window or a JPanel into
 * a flexible coordinate system.
 * 
 * <p>
 * Version 2.0 uses unit vectors to support 3 dimensional transformations. 
 * It has three coordinate systems, namely constrained coordinates, movable virtual coordinates and resized coordinates,
 * which are defined by three sets of unit vectors.
 * </p>
 * 
 * @author Yuelong Li
 * @version 2.0
 */

public class Locator {
	
	int dpiSize;

	/**
	 * Resized unit vectors based on rotatable unit vectors
	 */
	double[] i = new double[3], j = new double[3], k = new double[3];
	
	/**
	 * Rotatable unit vectors with modes of 1 expressed in terms of a, b, c
	 */
	double[] iv = new double[3], jv = new double[3], kv = new double[3];
	
	/**
	 * Constrained unit vectors expressed in terms of iv, jv and kv, for reversed transformation
	 */
	double[] a = new double[3], b = new double[3], c = new double[3];

	double xScale = 1, yScale = 1, zScale = 1;
	
	/**
	 * Translating moves the resized coordinate system to a new position in the constrained coordinates
	 */
	double xTr, yTr, zTr;
	
	/**
	 * Offseting the coordinate changes the position of the origin of the constrained coordinate system
	 */
	double xOffset, yOffset;

	int I_X, I_Y;

	/**
	 * Dynamic property of window width and window height
	 */
	double[] I_xy = new double[2];

	private Scene fxCanvas;

	boolean centered = true;
	
	public void setUnitVectors(double[] i, double[] j, double[] k) {
		this.iv=i;
		this.jv=j;
		this.kv=k;
		resizeVectors();
	}
	
	public double[][] getUnitVectors(){
		return new double[][] {i,j,k};
	}

	public void setxScale(double xScale) {
		this.xScale = xScale;
		resizeVectors();
	}

	public void setyScale(double yScale) {
		this.yScale = yScale;
		resizeVectors();
	}

	public void setzScale(double zScale) {
		this.zScale = zScale;
		resizeVectors();
	}

	public void rotateZ(double theta) {
		rotateSingleZ(theta, iv);
		rotateSingleZ(theta, jv);
		rotateSingleZ(theta, kv);
		resizeVectors();
	}

	private void rotateSingleZ(double theta, double[] l) {
		double l1 = l[0], l2 = l[1];
		l[0] = l1 * Math.cos(theta) - l2 * Math.sin(theta);
		l[1] = l2 * Math.cos(theta) + l1 * Math.sin(theta);
	}

	public void rotateY(double theta) {
		rotateSingleY(theta, iv);
		rotateSingleY(theta, jv);
		rotateSingleY(theta, kv);
		resizeVectors();
	}

	private void rotateSingleY(double theta, double[] l) {
		double l1 = l[0], l3 = l[2];
		l[0] = l1 * Math.cos(theta) - l3 * Math.sin(theta);
		l[2] = l3 * Math.cos(theta) + l1 * Math.sin(theta);
	}
	
	public void rotateX(double theta) {
		rotateSingleX(theta, iv);
		rotateSingleX(theta, jv);
		rotateSingleX(theta, kv);
		resizeVectors();
	}

	private void rotateSingleX(double theta, double[] l) {
		double l2 = l[1], l3 = l[2];
		l[1] = l2 * Math.cos(theta) + l3 * Math.sin(theta);
		l[2] = l3 * Math.cos(theta) - l2 * Math.sin(theta);
	}
	
	/**
	 * Translating moves the resized coordinate system to a new position in the constrained coordinates
	 * @param xTr the amount of x units toward which the coordinates are translated
	 */
	public void setxTr(double xTr) {
		this.xTr = xTr;
	}
	
	/**
	 * Translating moves the resized coordinate system to a new position in the constrained coordinates
	 * @param xTr the amount of y units toward which the coordinates are translated
	 */
	public void setyTr(double yTr) {
		this.yTr = yTr;
	}
	
	/**
	 * Translating moves the resized coordinate system to a new position in the constrained coordinates
	 * @param zTr the amount of z units toward which the coordinates are translated
	 */
	public void setzTr(double zTr) {
		this.zTr = zTr;
	}
	
	public double getxTr() {
		return xTr;
	}
	
	public double getyTr() {
		return yTr;
	}
	
	public double getzTr() {
		return zTr;
	}
	
	/**
	 * Offsetting moves the constrained coordinate to a new position on screen
	 * @param xOffset the <b>units</b> (not pixels) toward x direction which the coordinates are moved
	 */
	public void setxOffset(double xOffset) {
		this.xOffset = xOffset;
	}
	
	/**
	 * Offsetting moves the constrained coordinate to a new position on screen
	 * @param yOffset the <b>units</b> (not pixels) toward y direction which the coordinates are moved
	 */
	public void setyOffset(double yOffset) {
		this.yOffset = yOffset;
	}
	
	/**
	 * Offsetting moves the constrained coordinate to a new position on screen
	 * @param xOffset the <b>units</b> (not pixels) toward x direction which the coordinates are moved
	 */
	public double getxOffset() {
		return xOffset;
	}
	
	/**
	 * Offsetting moves the constrained coordinate to a new position on screen
	 * @param yOffset the <b>units</b> (not pixels) toward y direction which the coordinates are moved
	 */
	public double getyOffset() {
		return yOffset;
	}

	public void setScale(double... scale) {
		switch (scale.length) {
		case 0:
			break;
		case 1:
			xScale = scale[0];
			yScale = scale[0];
			zScale = scale[0];
			break;
		case 2:
			xScale = scale[0];
			yScale = scale[1];
			break;
		case 3:
			xScale = scale[0];
			yScale = scale[1];
			zScale = scale[2];
			break;
		default:
			xScale = scale[0];
			yScale = scale[1];
			zScale = scale[2];
			break;
		}
		I_xy[0] = I_x();
		I_xy[1] = I_y();
		resizeVectors();
	}

	/**
	 * Scales virtual unit vectors to resized unit vectors
	 */
	private void resizeVectors() {
		for (int c = 0; c < 3; c++) {
			i[c] = iv[c] * xScale;
			j[c] = jv[c] * yScale;
			k[c] = kv[c] * zScale;
		}
		recalculateabcVectors();
	}
	
	/**
	 * a, b and c vectors are constrained unit vectors expressed in terms of iv, jv and kv
	 */
	private void recalculateabcVectors() {
		a[0] = iv[0];
		a[1] = jv[0];
		a[2] = kv[0];
		b[0] = iv[1];
		b[1] = jv[1];
		b[2] = kv[1];
		c[0] = iv[2];
		c[1] = jv[2];
		c[2] = kv[2];
	}

	/**
	 * Configures whether the (0,0) coordinate is in the center or at the left side
	 * of the panel
	 * 
	 * @param centered
	 *            <p>
	 *            true: the coordinate is located where its oringin is in the center
	 *            <p>
	 *            false: the origin for non-centered locator is at the left center
	 *            of the panel
	 *            </p>
	 */
	public void setCentered(boolean centered) {
		this.centered = centered;
	}

	/**
	 * 
	 * @return a dynamic property of screen width and height
	 */
	public double[] getI_xy() {
		return I_xy;
	}

	public double I_x() {
		return (int)I_X / dpiSize /xScale;
	}

	public double I_y() {
		return (int)I_Y / dpiSize /yScale;
	}
	
	double[] fv2 = new double[3];//forward conversion vector2, expressed in terms of l, m, n


	public int toY(double... coords) {
		
		switch(coords.length) {
		case 1:
			return I_Y/2-(int)Math.round((projections(0,coords[0],0)[1]+yOffset)*dpiSize);
		case 2:
			return I_Y/2-(int)Math.round((projections(coords[0],coords[1],0)[1]+yOffset)*dpiSize);
		case 3:
			return I_Y/2-(int)Math.round((projections(coords[0],coords[1],coords[2])[1]+yOffset)*dpiSize);
		default: 
			break;
		}
		return 0;
	}

	public int toX(double... coords) {

		switch(coords.length) {
		case 1:
			return ((centered)?I_X/2:0)+(int)Math.round((projections(coords[0],0,0)[0]+xOffset)*dpiSize);
		case 2:
			return ((centered)?I_X/2:0)+(int)Math.round((projections(coords[0],coords[1],0)[0]+xOffset)*dpiSize);
		case 3:
			return ((centered)?I_X/2:0)+(int)Math.round((projections(coords[0],coords[1],coords[2])[0]+xOffset)*dpiSize);
		default: 
			break;
		}
		return 0;
	}
	
	public int toZ(double... coords) {

		switch(coords.length) {
		case 1:
			return (int)Math.round((projections(0,0,coords[0])[2])*dpiSize);
		case 2:
			return (int)Math.round((projections(coords[0],coords[1],0)[2])*dpiSize);
		case 3:
			return (int)Math.round((projections(coords[0],coords[1],coords[2])[2])*dpiSize);
		default: 
			break;
		}
		return 0;
	}
	
	public double[] toXYZ(double[] coords) {
		return new double[] {((centered)?I_X/2:0)+(int)Math.round((projections(coords[0],coords[1],coords[2])[0]+xOffset)*dpiSize),
				I_Y/2-(projections(coords[0],coords[1],coords[2])[1]+yOffset)*dpiSize,
				(projections(coords[0],coords[1],coords[2])[2])*dpiSize};
	}
	
	private double[] projections(double x, double y, double z) {
		fv2 = add(multiply(x + xTr, i),multiply(y + yTr, j),multiply(z + zTr, k));
		return fv2;	
	}
	
	public double toConstrainedx(int X) {
		return (double)(X-((centered)?I_X/2:0))/dpiSize-xOffset;
	}
	
	public double toConstrainedy(int Y) {
		return (double)(I_Y/2-Y)/dpiSize-yOffset;
	}
	
	
	/**
	 * Inverse conversion of coordinates --- from screen pixel to original coordinates --- 
	 * using vector projections
	 * @param Coords input coordinates in XYZ (screen pixels)
	 * @return coordinates that corresponds to the input value
	 */
	public double[] toxyz(int... Coords) {
		switch(Coords.length) {
		case 1: 
			return getxyz(Coords[0], 0, 0);
		case 2:
			return getxyz(Coords[0],Coords[1],0);
		case 3:
			return getxyz(Coords);
		default: 
			break;
		}
		return null;
	}
	
	/**
	 * Inverse conversion of coordinates --- from screen pixel to original x coordinate
	 * --- using vector projections
	 * @param Coords input coordinates in XYZ (screen pixels)
	 * @return coordinates that corresponds to the input value
	 */
	public double tox(int...Coords) {
		switch(Coords.length) {
		case 1: 
			return getxyz(Coords[0], 0, 0)[0];
		case 2:
			return getxyz(Coords[0],Coords[1],0)[0];
		case 3:
			return getxyz(Coords)[0];
		default: 
			break;
		}
		return Double.NaN;
	}
	
	/**
	 * Inverse conversion of coordinates --- from screen pixel to original y coordinate
	 * --- using vector projections
	 * @param Coords input coordinates in XYZ (screen pixels)
	 * @return coordinates that corresponds to the input value
	 */
	public double toy(int...Coords) {
		switch(Coords.length) {
		case 1: 
			return getxyz(0, Coords[0], 0)[1];
		case 2:
			return getxyz(Coords[0],Coords[1],0)[1];
		case 3:
			return getxyz(Coords)[1];
		default: 
			break;
		}
		return Double.NaN;
	}
	
	/**
	 * Inverse conversion of coordinates --- from screen pixel to original z coordinate
	 * --- using vector projections
	 * 
	 * <p>
	 * When the Coords size is 2, the input coordinates will be considered as (x,-,z) by default.
	 * Any input size beyond 3 is not accepted.
	 * </p>
	 * @param Coords input coordinates in XYZ (screen pixels)
	 * @return coordinates that corresponds to the input value
	 */
	public double toz(int...Coords) {
		switch(Coords.length) {
		case 1: 
			return getxyz(0, 0, Coords[0])[2];
		case 2:
			return getxyz(Coords[0] ,0, Coords[1])[2];
		case 3:
			return getxyz(Coords)[2];
		default: 
			break;
		}
		return Double.NaN;
	}

	private double[] xyzHoard = new double[3];
	private double[] rv0 = new double[3];
	private double[] coordsr = new double[3];
	/**
	 * Inverse conversion of coordinates --- from screen pixel to coordinate
	 * @param Coords input coordinates in XYZ (screen pixels)
	 * @return coordinates that corresponds to the input value
	 */
	private double[] getxyz(int... Coords) {
		rv0[0] =(double)(Coords[0]-((centered)?I_X/2:0))/dpiSize-xOffset;
		rv0[1] = (double)(I_Y/2-Coords[1])/dpiSize-yOffset;
		rv0[2] = (double)Coords[2]/dpiSize;
		for(int i = 0; i<3; i++) {
			coordsr[i] = a[i]*rv0[0]+b[i]*rv0[1]+c[i]*rv0[2];
		}
		xyzHoard[0] = coordsr[0]/xScale - xTr;
		xyzHoard[1] = coordsr[1]/yScale - yTr;
		xyzHoard[2] = coordsr[2]/zScale - zTr;
		return xyzHoard;
	}

	public int toWidth(double d) {
		return (int) Math.round(d * xScale * dpiSize);
	}

	public int toHeight(double d) {
		return (int) Math.round(d * yScale * dpiSize);
	}

	public Scene getCanvas() {
		return fxCanvas;
	}

	protected void extra() {

	}

	public Locator(Scene canvas) {
		this.fxCanvas = canvas;
		ChangeListener<? super Number> listener = new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				I_X = (int)fxCanvas.getWidth();
				I_Y = (int)fxCanvas.getHeight();
				I_xy[0] = I_x();
				I_xy[1] = I_y();
				extra();
			}
			
		};
		canvas.widthProperty().addListener(listener);
		canvas.heightProperty().addListener(listener);
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		dpiSize = (screenSize.width + screenSize.height) / 100;
	}
}
/*
 * © Copyright 2017 Cannot be used without authorization
 */