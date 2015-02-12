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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.TargetEmplPeriod;
import com.consultancygrid.trz.model.TargetLevels;
import com.consultancygrid.trz.model.TargetPeriod;
import com.consultancygrid.trz.ui.combo.DepartmentComboBoxModel;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.TargetLevelCfgTable;
import com.consultancygrid.trz.ui.table.TargetLevelCfgTableModel;

import static com.consultancygrid.trz.base.Constants.*;
/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class TrgPeriodLevelsConfigComboAL extends BaseActionListener {

	
	private JComboBox comboBoxTarget;
	private JPanel createFormPanel; 
	
	public TrgPeriodLevelsConfigComboAL(PrototypeMainFrame mainFrame,JPanel createFormPanel,JComboBox comboBoxTarget) {

		super(mainFrame);
		this.comboBoxTarget = comboBoxTarget;
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

			Query q = em.createQuery(" from TargetLevels as tl  where  tl.targetPeriod.id = :targetId");
			q.setParameter("targetId", target.getId());
			
			List<TargetLevels> allTargets = (List<TargetLevels>) q.getResultList();
			
			TargetLevelCfgTableModel tlModel = new TargetLevelCfgTableModel();
			tlModel.setTargetLevels(allTargets);
			TargetLevelCfgTable table = new TargetLevelCfgTable();
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setModel(tlModel);
			//table.setPreferredScrollableViewportSize(table.getPreferredSize());
			table.setBounds(10, 10, 150, 200);
			JScrollPane trzLevelPanel = new JScrollPane(table);
			trzLevelPanel.setBounds(40, 40, 300, 200);
			createFormPanel.add(trzLevelPanel);
			createFormPanel.revalidate();
			createFormPanel.repaint();
			
			
			JButton btnSavePeriod = new JButton("Save");
			btnSavePeriod.setBounds(50, 250, 200, 25);
			createFormPanel.add(btnSavePeriod);
			JButton btnDelPeriod = new JButton("Delete");
			btnDelPeriod.setBounds(280, 250, 200, 25);
			createFormPanel.add(btnDelPeriod);
			JButton btnEditPeriod = new JButton("Edit");
			btnEditPeriod.setBounds(500, 250, 200, 25);
			createFormPanel.add(btnEditPeriod);
			//SaveTargetEmployeeAL saveTAL  = new SaveTargetEmployeeAL(mainFrame, textFieldValue, comboBoxTarget, comboBoxEmployee, createFormPanel, tep, update, btnSavePeriod);
			//btnSavePeriod.addActionListener(saveTAL);
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
