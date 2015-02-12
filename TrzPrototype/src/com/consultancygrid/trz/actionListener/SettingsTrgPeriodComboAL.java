package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.TargetEmplPeriod;
import com.consultancygrid.trz.model.TargetPeriod;
import com.consultancygrid.trz.ui.combo.DepartmentComboBoxModel;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

import static com.consultancygrid.trz.base.Constants.*;
/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class SettingsTrgPeriodComboAL extends BaseActionListener {

	
	private JComboBox comboBoxTarget;
	private JComboBox comboBoxEmployee;
	private JPanel createFormPanel; 
	
	public SettingsTrgPeriodComboAL(PrototypeMainFrame mainFrame,JPanel createFormPanel,JComboBox comboBoxTarget, JComboBox comboBoxEmployee) {

		super(mainFrame);
		this.comboBoxTarget = comboBoxTarget;
		this.comboBoxEmployee = comboBoxEmployee;
		this.createFormPanel = createFormPanel;
	}

	public void actionPerformed(ActionEvent e) {

		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();

			TargetPeriod target = ((TargetPeriod) comboBoxTarget.getModel().getSelectedItem());
			Employee empl = ((Employee) comboBoxEmployee.getModel().getSelectedItem());

			Query q = em.createQuery(" from TargetEmplPeriod as tep  where  tep.targetPeriod.id = :targetId and tep.employee.id = :emplId");
			q.setParameter("targetId", target.getId());
			q.setParameter("emplId", empl.getId());
			List<TargetEmplPeriod> allTargets = (List<TargetEmplPeriod>) q.getResultList();
			
			boolean update = false;
			TargetEmplPeriod tep = null;
			if (allTargets != null && !allTargets.isEmpty()) {
				tep = allTargets.get(0);
				update = true;
			} else {
				tep = new TargetEmplPeriod();
				update = false;
			}
			
			JLabel lblEmpl1 = new JLabel("Target Value");
			lblEmpl1.setBounds(50, 100, 100, 25);
			createFormPanel.add(lblEmpl1);

			JTextField textFieldValue = new JTextField();
			textFieldValue.setBounds(180, 100, 200, 25);
			textFieldValue.setText(tep.getTargetValue() != null ? tep.getTargetValue().toString() : "");
			createFormPanel.add(textFieldValue);
			
			
			JButton btnSavePeriod = new JButton(update ? "Update" :"Save");
			btnSavePeriod.setBounds(50, 150, 200, 25);
			createFormPanel.add(btnSavePeriod);
			SaveTargetEmployeeAL saveTAL  = new SaveTargetEmployeeAL(mainFrame, textFieldValue, comboBoxTarget, comboBoxEmployee, createFormPanel, tep, update, btnSavePeriod);
			btnSavePeriod.addActionListener(saveTAL);
			createFormPanel.revalidate();
			createFormPanel.repaint();
			mainFrame.validate();
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.close();
			}
		}
	}
}
