package com.consultancygrid.trz.actionListener.login;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.persistence.Query;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;






import org.apache.log4j.Logger;

import com.consultancygrid.trz.actionListener.GBaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.TrzUser;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class LoginAL extends GBaseActionListener {
	
	private static Logger log =  Logger.getLogger(LoginAL.class); 
	private JTextField userText = null;
	private JPasswordField passwordText = null;

	public LoginAL(JFrame mainFrame, JTextField userText, JPasswordField passwordText) {

		super(mainFrame);
		this.userText = userText;
		this.passwordText = passwordText;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		boolean loginPassed = false;
		try {
			init();
			loginPassed = processData();
		} catch (Exception e1) {
			log.error(e1);
			rollBack();
		} finally {
			commit();
		}	
		
		if (loginPassed) {
			
			this.mainFrame.setVisible(false);
			PrototypeMainFrame.main(new String[0]);
			 
		} 
	}

	
	protected boolean processData() throws HeadlessException, IOException {


		String loginString = this.userText.getText();
		if (loginString == null || "".equals(loginString)) {
			JOptionPane
					.showMessageDialog(
							mainFrame,
							"Enter login !",
							ResourceLoaderUtil
									.getLabels(LabelsConstants.ALERT_MSG_ERR),
							JOptionPane.ERROR_MESSAGE);
			return false;
		}
		String passString = new String(this.passwordText.getPassword());
		
		if (passString == null || "".equals(passString)) {
			JOptionPane
					.showMessageDialog(
							mainFrame,
							"Enter password !",
							ResourceLoaderUtil
									.getLabels(LabelsConstants.ALERT_MSG_ERR),
							JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		Query q = em.createQuery("select u from TrzUser as u  where  u.login = :login and u.password = :pass ");
		q.setParameter("login", loginString);
		q.setParameter("pass", passString);
		List<TrzUser> trzUser = (List<TrzUser>) q.getResultList();
		
		if (trzUser != null && !trzUser.isEmpty()) {
			return true;
		} else {
			try {
				JOptionPane
				.showMessageDialog(
						mainFrame,
						"Wrong username / password !",
						ResourceLoaderUtil
								.getLabels(LabelsConstants.ALERT_MSG_ERR),
						JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException | IOException e1) {
				log.error(e1);
			}
			return false;
			
		}
		
	}

}
