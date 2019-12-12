package testers;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import codeLibrary.JSizer;
import ui.DataPane2D;
import utils.DataType;
import utils.Dataset;
import utils.Function1V;
import utils.MultiVarInterface;

import javax.swing.JButton;

public class DataPane2DTester extends JFrame {

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
					DataPane2DTester frame = new DataPane2DTester();
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
	public DataPane2DTester() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 899, 631);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DataPane2D panel = new DataPane2D();
		panel.setBounds(0, 0, 881, 584);
		contentPane.add(panel);
		
		Dataset sin = new Function1V((var)->Math.sin(var[0]));
		panel.addDataset(sin);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(14, 13, 73, 27);
		panel.add(btnReset);
		btnReset.addActionListener(e-> panel.reset());
		
		JSizer a = new JSizer(panel);
		panel.setLayout(null);
		a.addListener(this);
	}
}
