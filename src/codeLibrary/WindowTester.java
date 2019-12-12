package codeLibrary;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;

public class WindowTester extends JFrame implements ActionListener{

	JPanel contentPane;
	JSizer sizer;
	JPanel panel;
	ArrayList <Integer> tester = new ArrayList<Integer>(0);
	private JPanel panel_2;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowTester frame = new WindowTester();
					frame.setVisible(true);
					Console.println(frame.getSize());
					Console.println(frame.panel.getSize());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 */
	public WindowTester() throws InterruptedException {
		setBounds(0,0,456,315);
		contentPane = new JPanel();
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 438, 268);
		getContentPane().add(panel);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// 
		
	}
}
