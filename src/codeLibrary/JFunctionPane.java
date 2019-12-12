package codeLibrary;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class JFunctionPane extends JPanel{
	//unit: points per unit
	public int density;
	//start and end of the function
	double xo=0;
	double xf=3; 
	//y bounderies of the function
	double yo;
	double yf;
	public double XScale=45;
	public double YScale=45;
	public int IX;
	public int IY;
	public double Ix;
	public double Iy;
	ArrayList<Double> xv = new ArrayList<Double>();
	ArrayList<ArrayList<Double>> ym=new ArrayList<ArrayList<Double>>();
	boolean painting=true;
	Color[] functionColors;
	Stroke stroke;
	public JFunctionPane(int a, int b,int density){
		super();
		this.density=density;
		setBackground(Color.WHITE);
		functionColors = new Color[f(0).length];
		Random rand = new Random(17);
		for(int i=0;i<f(0).length;i++){
			if(i==5)rand.nextFloat();
			functionColors[i] = Color.getHSBColor((float)((Math.cos(rand.nextFloat()/15*2*Math.PI)+1)*180), 100, 75);
			ym.add(new ArrayList<Double>());
		}
		
		lay(a,b);
		stroke = new BasicStroke(1.5f);
	}
	public void lay(int a, int b){
		xo=a;
		xf=b;
		for(double i = xo*density; i<=xf*density;i++){
			double x;
			double[] y;
			x = i/density;
			y=f(x);
			xv.add(x);
			int index=0;
			for(double pass:y){
				if(Math.abs(pass)>100){
					ym.get(index).add(101.0);
				}else{
					ym.get(index).add(pass);
				}
				index++;
			}
		}
		yf=Aloga.getMax(Aloga.getMatrixMax(ym));
		yo=Aloga.getMin(Aloga.getMatrixMin(ym));
		Iy=yf-yo;
		Ix=xf-xo;
	}
	public void repaint(){
		super.repaint();
	}
	boolean initialize=true;
	protected void paintComponent(Graphics g){
		painting=true;
		super.paintComponent(g);
		setPreferredSize(new Dimension((int)(Ix*XScale),(int)(Iy*YScale)));
		IX=getSize().width;
		IY=getSize().height;
		g.setColor(Color.LIGHT_GRAY);
		for(double i = (int)(-IX/2/XScale); i<IX/2/XScale; i++){
			g.drawLine((int)(IX/2+(i)*XScale), 0, (int)(IX/2+(i)*XScale), getHeight());
			g.drawString(String.valueOf((int)(i*100)/100), IX/2+(int)((i)*XScale), IY/2);
		}
		for(double i = (int)(-IY/2/YScale); i<IY/2/YScale; i++){
			g.drawLine(0, (int)(IY/2-(i)*YScale),getWidth(), (int)(IY/2-(i)*YScale));
			if(((int)(i*100)/100)!=0){
				g.drawString(String.valueOf((int)(i*100)/100), IX/2, (int)(IY/2-(i)*YScale));
			}	
		}
		g.setColor(Color.BLACK);
		g.drawLine((int)(IX/2), 0, (int)(IX/2), getHeight());
		g.drawLine(0, (int)(IY/2),getWidth(), (int)(IY/2));
		int index=0;
		Graphics2D gr = (Graphics2D) g;
		gr.setStroke(stroke);
		for(ArrayList<Double> yv: ym){
			gr.setColor(functionColors[index]);
			for(int i = 1; i<xv.size();i++){
				if(!(yv.get(i-1).isNaN()||yv.get(i).isNaN()))
				g.drawLine((int)(IX/2+(xv.get(i-1))*XScale), (int)(IY/2-(yv.get(i-1))*YScale), (int)(IX/2+(xv.get(i))*XScale), (int)(IY/2-(yv.get(i))*YScale));
			}
			index++;
		}
		painting=false;
		repaint();
	}
	public double[] f(double x){
		return new double[]{Math.sin(x)};
	}
}