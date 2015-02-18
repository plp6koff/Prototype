package com.consultancygrid.trz.actionListener.employee;
import static com.consultancygrid.trz.base.LabelsConstants.*;
import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.employee.EmployeeActiveTableModel;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.util.EmployeeSalaryUtil;
import com.consultancygrid.trz.util.EmployeeSettingsUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class SaveEmployeeAL extends BaseActionListener {

	
	private static final Font labelFont = new Font("Arial", Font.BOLD, 11);
	private JPanel panel;
	private JComboBox employeesCombo;
	private List<Period> periods;
	private Map<String, JTextField> params;

	public SaveEmployeeAL(PrototypeMainFrame mainFrame, JPanel panel ,Map<String, JTextField> params, List<Period> periods, JComboBox employeesCombo) {

		super(mainFrame);
		this.panel = panel;
		this.params = params;
		this.periods = periods;
		this.employeesCombo = employeesCombo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {

			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

			em = factory.createEntityManager();
			em.getTransaction().begin();

			JTextField fnField = params.get(SET_CRT_EMPL_FNAME);
			JTextField lnField = params.get(SET_CRT_EMPL_LNAME);
			JTextField mcField = params.get(SET_CRT_EMPL_MATCHODE);
			String firstName = fnField.getText();
			String lastName = lnField.getText();
			String matchCode = mcField.getText();
			
			
			int comonentCounts = panel.getComponentCount();
			if (comonentCounts > 7) {
				panel.remove(panel.getComponent(7));
			}
			
			if (periods != null && !periods.isEmpty()) {
				
				Period period = periods.get(0);
				
				double vauchers = 0.0d;
				for (PeriodSetting es : period.getPeriodSettings()) {
					
					
					if  (("VAUCHER1".equals(es.getTrzStatic().getKey()))
							|| ("VAUCHER2".equals(es.getTrzStatic().getKey()))) {
						
						vauchers = vauchers + Double.valueOf(es.getValue());
					}
				}
				
				
				if (firstName!= null && !"".equals(firstName)
					&&	lastName!= null && !"".equals(lastName)
					&&  matchCode!= null && !"".equals(matchCode)) {
					
					Employee employee = new Employee();
					employee.setIsActive("Y");
					employee.setFirstName(firstName);
					employee.setLastName(lastName);
					employee.setMatchCode(matchCode);
					em.persist(employee);
					EmployeeSalaryUtil.createSalary(em, period, employee, vauchers);
					EmployeeSettingsUtil.createSettings(em, period, employee);
					EmplComboBoxModel model = (EmplComboBoxModel) employeesCombo.getModel();
					model.addItem(employee);
					employeesCombo.getParent().revalidate();
					employeesCombo.getParent().repaint();
					fnField.setText("");
					lnField.setText("");
					mcField.setText("");
					if (comonentCounts > 7) {
						panel.remove(panel.getComponent(7));
					}
					EmployeeActiveTableModel model2 = new EmployeeActiveTableModel();
				
					Object row = new Object[]{(employee.getFirstName() + " " + employee.getLastName()), true};
					model2.addData(row);
					
					JOptionPane.showMessageDialog(mainFrame,
							ResourceLoaderUtil.getLabels(LabelsConstants.SET_CRT_EMPL_SAVE_SUCCESS),
							ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_OK),
							JOptionPane.OK_OPTION);
					
				} else {
					
					JLabel errorLabel = new JLabel(ResourceLoaderUtil.getLabels(SET_CRT_EMPL_VALID1));
					errorLabel.setFont(labelFont);
					errorLabel.setForeground(Color.RED);
					errorLabel.setBounds(30, 10, 500, 25);
					panel.add(errorLabel);
				}
			} else {
				JLabel errorLabel = new JLabel(ResourceLoaderUtil.getLabels(SET_CRT_EMPL_VALID2));
				errorLabel.setFont(labelFont);
				errorLabel.setForeground(Color.RED);
				errorLabel.setBounds(30, 180, 400, 25);
				panel.add(errorLabel);
			}
			panel.revalidate();
			panel.repaint();
			
		} catch (Exception e1) {
			Logger.error(e1);
				try {
					JOptionPane.showMessageDialog(mainFrame,
						ResourceLoaderUtil.getLabels(SET_CRT_EMPL_SAVE_ERR),
						ResourceLoaderUtil.getLabels(ALERT_MSG_ERR),
						JOptionPane.ERROR_MESSAGE);
				} catch (HeadlessException | IOException e2) {
					e2.printStackTrace();
				}
			if (em != null && em.isOpen()) {
				em.getTransaction().rollback();
				em.close();
			}

		} finally {
			if (em != null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
				factory.close();
			}
		}
	}

}
