package codeLibrary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ComponentListener;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.util.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

public class FunctionDisplay extends JFrame {
	private JPanel contentPane;
	private JScrollPane scrollPane = new JScrollPane();
	protected JFunctionPane functionPane;
	JSizer sizer;
	int maxScaleFactor = 10;
	JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
	JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
	JViewport scrollViewPort = scrollPane.getViewport();
	boolean mousePressed=false;
	int[] mouseLocation = new int[2];
	int[] scrollLocation = new int[2];
	boolean updated;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {Desktop.getDesktop().mail();
					FunctionDisplay frame = new FunctionDisplay("Display board",50);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FunctionDisplay(String name,int...density) {
		super(name);
		setBounds(100, 100, 1000, 655);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 982, 608);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JSlider horizontalScale = new JSlider();
		horizontalScale.setBackground(Color.WHITE);
		horizontalScale.setBounds(0, 582, 948, 26);
		horizontalScale.setMinimum(0);
		horizontalScale.setMaximum(100);
		horizontalScale.setValue(50);
		panel.add(horizontalScale);
		horizontalScale.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				double newScale=getScale((double)((JSlider)e.getSource()).getValue()/100);
				functionPane.XScale = newScale;
			}
			
		});
		
		JSlider verticalScale = new JSlider();
		verticalScale.setBackground(Color.WHITE);
		verticalScale.setOrientation(SwingConstants.VERTICAL);
		verticalScale.setBounds(947, 0, 35, 582);
		verticalScale.setValue(50);
		verticalScale.setMaximum(100);
		verticalScale.setMinimum(0);
		panel.add(verticalScale);
		verticalScale.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double newScale=getScale((double)((JSlider)arg0.getSource()).getValue()/100);
				functionPane.YScale=newScale;
			}
			
		});
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setBounds(14, 13, 934, 569);
		panel.add(scrollPane);
		scrollPane.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				mousePressed=true;
				mouseLocation[0]=arg0.getX();
				mouseLocation[1]=arg0.getY();
				scrollLocation[0]=horizontalScrollBar.getValue();
				scrollLocation[1]=verticalScrollBar.getValue();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				mousePressed=false;
			}
			
		});
		scrollPane.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0) {
				verticalScrollBar.setValue(scrollLocation[1]-arg0.getY()+mouseLocation[1]);
				horizontalScrollBar.setValue(scrollLocation[0]-arg0.getX()+mouseLocation[0]);
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// 
				
			}
			
		});
		
		functionPane = new JFunctionPane(-100,100,(density.length!=0)?density[0]:50){
			public double[] f(double x){
				return function(x);
			}
		};
		scrollPane.setViewportView(functionPane);
		scrollPane.repaint();
		horizontalScrollBar.setValue(horizontalScrollBar.getMaximum()/2);
		verticalScrollBar.setValue(verticalScrollBar.getMaximum()/2);
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon(FunctionDisplay.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaVolumeThumb.png")));
		button.setBounds(957, 582, 25, 27);
		panel.add(button);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				assertMiddle();
			}
			
		});
		functionPane.addComponentListener(new ComponentListener(){

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent e) {
				assertMiddle();
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		sizer = new JSizer(panel);
		sizer.addAllComponents();
		sizer.addListener(this);
		setVisible(true);
	}
	
	public void assertMiddle(){
		while(functionPane.painting){
			Console.println(functionPane.painting);
		};
		double viewPortWidth = scrollViewPort.getWidth();
		double viewPortHeight = scrollViewPort.getHeight();
		double newX = functionPane.IX/2-viewPortWidth/2;
		double newY = functionPane.IY/2-viewPortHeight/2;
		horizontalScrollBar.setValue((int)newX);
		verticalScrollBar.setValue((int)newY);
	}
	protected double[] function(double x){
		return  new double[]{-Math.cos(x),x*x/2-1,Math.cos(x)+x*x/2};
	}
	private double getScale(double power){
		return 45*Math.pow(maxScaleFactor, power*2-1);
	}
}
