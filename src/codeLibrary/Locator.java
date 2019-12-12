package codeLibrary;

import javax.swing.JPanel;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * 
 * @author Yuelong
 * @version 1.0
 */
public class Locator {
	//all screen coordinates start with a capital letter, 
	//all virtual coordinates start with a non capitalized letter
	int[] Alignement = new int[]{0,0};
	double xUnitPixels=30;
	double yUnitPixels = 30;
	//X:x
	double xScale=30;
	//Y:y
	double yScale=30;
	double[] orgOffSet = new double[]{0,0};
	int I_X;
	int I_Y;
	private JPanel jCanvas;
	
	boolean centered = true;
	
	public Locator(JPanel canvas){
		this.jCanvas = canvas;
		canvas.addComponentListener(new ComponentListener(){

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				I_Y=canvas.getHeight();
				I_X=canvas.getWidth();
				extra();
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void zoom(double leverage){
		xScale = Math.pow(Math.E,leverage)*xUnitPixels;
		yScale = Math.pow(Math.E,leverage)*yUnitPixels;
	}

	public void setScale(int...scale){
		switch(scale.length){
		case 0: break;
		case 1: 
			xScale = scale[0];
			yScale = scale[0];
			break;
		case 2:
			xScale = scale[0];
			yScale = scale[1];
			break;
		default:
			xScale = scale[0];
			yScale = scale[1];
			break;
		}
	}
	public void setOriginalScale(int xScale, int yScale){
		this.xScale=xScale;
		this.yScale=yScale;
		xUnitPixels=xScale;
		yUnitPixels=yScale;
	}
	/**
	 * Configures whether the (0,0) coordinate is in the center of the panel
	 * @param centered --  true:  the coordinate is located oringin is in the center
	 * 	<p> false: the origin for non-centered locator is at the left center of the panel
	 */
	public void setCentered(boolean centered){
		this.centered = centered;
	}
	
	/**
	 * Off sets the coordinate origin to a certain location
	 * @param offset (Δx,Δy) measured in relative units
	 */
	public void setOrigninOffset(double[] offset){
		orgOffSet = offset;
	}
	public double[] getxy(){
		return new double[]{I_X/xScale,I_Y/yScale};
	}
	public double I_x(){
		return I_X/xScale;
	}
	public double I_y(){
		return I_Y/yScale;
	}
	public int toY(double y){
		y+=orgOffSet[1];
		return (int)Math.round(I_Y/2-y*yScale);
	}
	
	public int toX(double x){
		x+=orgOffSet[0];
		if(centered)return (int)Math.round(x*xScale+I_X/2);
		else return (int)Math.round(x*xScale);
	}
	
	public double toy(int Y){
		return((double)(I_Y/2-Y))/yScale-orgOffSet[1];
	}
	
	public double tox(int X){
		if(centered)return (double)(X-I_X/2)/xScale-orgOffSet[0];
		else return (double)(X)/xScale-orgOffSet[0];
	}
	
	public int toWidth(double d){
		return (int)Math.round(d*xScale);
	}
	
	public int toHeight(double d){
		return (int)Math.round(d*yScale);
	}
	
	public JPanel getPanel(){
		return jCanvas;
	}
	
	protected void extra(){
		
	}
	
}
/*
 * © Copyright 2016 
 * Cannot be used without authorization
 */