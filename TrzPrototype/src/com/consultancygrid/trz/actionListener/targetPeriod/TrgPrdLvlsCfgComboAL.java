package com.consultancygrid.trz.actionListener.targetPeriod;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

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
import javax.swing.JViewport;
import javax.swing.ScrollPaneLayout;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
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
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTable;
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTableModel;

import static com.consultancygrid.trz.base.Constants.*;
/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class TrgPrdLvlsCfgComboAL extends BaseActionListener {

	
	private JComboBox comboBoxTarget;
	private JPanel createFormPanel; 
	
	public TrgPrdLvlsCfgComboAL(PrototypeMainFrame mainFrame,JPanel createFormPanel,JComboBox comboBoxTarget) {

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
			Vector<Object> data = new Vector<>();
			for (TargetLevels tL : allTargets) {
				
				Vector<Object> tempRow = new Vector<>();
				tempRow.add(tL.getTargetPercent());
				tempRow.add(tL.getTargetBonus());
				data.add(tempRow);
			}
			tlModel.setData(data);
			TargetLevelCfgTable table = new TargetLevelCfgTable();
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setModel(tlModel);
			table.setBounds(40, 100, 160, 200);
			JScrollPane trzLevelPanel = new JScrollPane(table);
			trzLevelPanel.setBounds(40, 100, 150, 200);
			
			trzLevelPanel.setLayout(new ScrollPaneLayout());
			
			createFormPanel.add(trzLevelPanel);
			createFormPanel.revalidate();
			createFormPanel.repaint();
			
			
			JButton btnSavePeriod = new JButton("Save");
			btnSavePeriod.setBounds(300, 100, 100, 25);
			createFormPanel.add(btnSavePeriod);
			JButton btnDelPeriod = new JButton("Delete");
			btnDelPeriod.setBounds(300, 140, 100, 25);
			createFormPanel.add(btnDelPeriod);
			JButton btnEditPeriod = new JButton("Edit");
			btnEditPeriod.setBounds(300, 180, 100, 25);
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
