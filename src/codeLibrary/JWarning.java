package codeLibrary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JWarning extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	public static void warn(String warning){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new JWarning(warning);
			}
		});	
	}
	/**
	 * Create the dialog.
	 */
	public JWarning(String warn) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width-519)/2, (screenSize.height-250)/2, 519, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(JWarning.class.getResource("/com/sun/javafx/scene/control/skin/caspian/dialog-warning.png")));
		label.setBounds(24, 63, 48, 48);
		contentPanel.add(label);
		
		setVisible(true);
		
		JLabel lblWarning = new JLabel("<html>"+warn+"</html>");
		lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarning.setFont(new Font("Arial", Font.PLAIN, 23));
		lblWarning.setBounds(86, 47, 325, 92);
		contentPanel.add(lblWarning);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("     OK     ");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e){
		dispose();
	}
}
