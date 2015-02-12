package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.TargetEmplPeriod;
import com.consultancygrid.trz.model.TargetPeriod;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class SaveTargetEmployeeAL extends BaseActionListener{

	private JTextField value;
	private JComboBox comboBoxTarget;
	private JComboBox comboBoxEmployee;
	private JPanel createPanel;
	private boolean update;
	private JButton button;
	private TargetEmplPeriod targEmplPer;
	
	public SaveTargetEmployeeAL(PrototypeMainFrame mainFrame, JTextField value,
			JComboBox comboBoxTarget, 
			JComboBox comboBoxEmployee,
			JPanel createPanel, 
			TargetEmplPeriod targEmplPer, 
			boolean update, JButton button) {
		
		super(mainFrame);
		this.value = value;
		this.comboBoxTarget = comboBoxTarget;
		this.comboBoxEmployee = comboBoxEmployee;
		this.value = value;
		this.createPanel = createPanel;
		this.update  = update;
		this.targEmplPer = targEmplPer;
		this.button = button;
	} 

	@Override
	public void actionPerformed(ActionEvent e) {
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			String valueTarget  = this.value.getText();
			if (valueTarget == null || "".equals(valueTarget) || valueTarget.length() > 7) {
				JOptionPane.showMessageDialog(mainFrame, "Please fill the value !",
						ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR) , JOptionPane.ERROR_MESSAGE);
				return;
			}
			Employee empl = (Employee)comboBoxEmployee.getModel().getSelectedItem();
			TargetPeriod tP = (TargetPeriod) comboBoxTarget.getModel().getSelectedItem();
			
			targEmplPer.setTargetPeriod(tP);
			targEmplPer.setEmployee(empl);
			targEmplPer.setTargetValue(BigDecimal.valueOf(Double.valueOf(value.getText())));
			
			if (update) {
				em.merge(targEmplPer);
			} else {
				em.persist(targEmplPer);
			}
			
			
			comboBoxEmployee.getModel().setSelectedItem(null);
			comboBoxTarget.getModel().setSelectedItem(null);
			createPanel.remove(button);
			createPanel.remove(value);
			createPanel.revalidate();
			createPanel.repaint();
			
			
			try {
				JOptionPane.showMessageDialog(mainFrame, 
						"Target 'Linked to employee ",
						ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_INFO), JOptionPane.INFORMATION_MESSAGE);
			} catch (HeadlessException | IOException e2) {
				e2.printStackTrace();
			}
			
		} catch (Exception e1) {
			Logger.error(e1);
			try {
				JOptionPane.showMessageDialog(mainFrame, 
						 ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_ERR), 
						 ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR),JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException | IOException e2) {
				e2.printStackTrace();
			}
			if (em!= null && em.isOpen()) {
				em.getTransaction().rollback();
				em.close();
			}

		} finally {
			if (em!= null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
			}
		}	
	}
	
}
