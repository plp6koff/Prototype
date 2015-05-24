package com.consultancygrid.trz.ui.frame;

import java.awt.EventQueue;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.consultancygrid.trz.actionListener.login.LoginAL;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.util.HibernateUtil;

public class LoginFrame {

	private JTextField textField;
	private JComboBox<Employee> comboBoxEmployees;
	private static EntityManager  em ;
	private static EntityTransaction tx;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					HibernateUtil.initiate();
					em = HibernateUtil.getEntityManager();
					tx = em.getTransaction();
					tx.begin();
					
					JFrame frame = new JFrame("T-R-Z Login");
					frame.setBounds(500, 500, 300, 150);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


					JPanel panel = new JPanel();
					frame.add(panel);
					placeComponents(panel, frame);

					frame.setVisible(true);
					
				} catch (Exception e) {
					if (tx != null && tx.isActive()) {
						tx.rollback();
					}
				} finally {	
					if (tx != null && tx.isActive()) {
						tx.commit();
					}
				}
				
			}
		});
	}
	
	
	private static void placeComponents(JPanel panel, JFrame frame) {

		
		panel.setLayout(null);
		
		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("Sign In");
		loginButton.setBounds(100, 80, 100, 25);
		panel.add(loginButton);
		LoginAL lAL = new LoginAL(frame, userText, passwordText);
		loginButton.addActionListener(lAL);
	}
}
