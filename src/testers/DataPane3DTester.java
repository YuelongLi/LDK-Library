package testers;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import codeLibrary.JSizer;
import ui.DataPane3D;
import utils.DataMatrix1V;
import utils.DataMatrix2V;
import utils.Dataset;
import utils.Function2V;

public class DataPane3DTester extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataPane3DTester frame = new DataPane3DTester();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DataPane3DTester() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 250, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Create a datapane 3D
		DataPane3D dataPane3D = new DataPane3D();
		//Size and locate it relative to its parent
		dataPane3D.setBounds(0, 0, 750, 480);
		contentPane.add(dataPane3D);
		
		//Create a 2 variable function z=x^2+y^2
		Dataset sin = new Function2V((var)->Math.sin(var[0])+Math.cos(var[1])+3);
		//Add the paraboloid to the dataset for rendering
		dataPane3D.addDataset(sin);
		sin.setColor(new Color(0, 102, 208));
		
		//Draw a parametric curve (trefoil knot)
		DataMatrix1V knot = new DataMatrix1V();
		ArrayList<double[]> curve = new ArrayList<double[]>();
		double t = -Math.PI*2, dt = 0.01,x,y,z;
		while(t<Math.PI*2) {
			x = (2+Math.cos(1.5*t))*Math.cos(t);
			y = (2+Math.cos(1.5*t))*Math.sin(t);
			z = Math.sin(1.5*t);
			curve.add(new double[] {x,y,z});
			t+=dt;
		}
		knot.setCurve(curve);
		knot.setName("trefoil knot");
		knot.setColor(Color.ORANGE);
		dataPane3D.addDataset(knot);
		
		//dataPane3D.addDataset(torus());
		
		JSizer sizer = new JSizer(dataPane3D);
		sizer.addListener(this);
	}
	
	private static Dataset torus() {
		double r1 = 2;
		double r2 = 0.75;
		DataMatrix2V torus = new DataMatrix2V();
		List<ArrayList<double[]>> surface = new ArrayList<ArrayList<double[]>>();
		double t = 0, dt = Math.PI/20;
		while(t<=Math.PI*2) {
			ArrayList<double[]> curve = new ArrayList<double[]>();
			double s = 0, ds = Math.PI/20;
			while(s <=Math.PI*2) {
				curve.add(new double[] {
						Math.cos(t)*(r2*Math.cos(s)+r1),
						Math.sin(t)*(r2*Math.cos(s)+r1),
						Math.sin(s)*r2
				});
				s+=ds;
			}
			t+=dt;
			surface.add(curve);
		}
		torus.setSurface(surface);
		torus.setName("torus");
		return torus;
	}
}
