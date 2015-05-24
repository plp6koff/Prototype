package com.consultancygrid.trz.actionListener.group;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.InputData;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTable;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class PeriodDelRowAL extends BaseActionListener{

	private JComboBox<Period> comboBoxPeriod;
	private JComboBox<Period> comboBoxPeriod2;
	private GroupCfgEmplsTable departTable;
	private GroupCfgEmplsTable departTable2;
	
	public PeriodDelRowAL(PrototypeMainFrame mainFrame, 
						 JComboBox<Period> comboBoxPeriod,
						 JComboBox<Period> comboBoxPeriod2,
						 GroupCfgEmplsTable departTable,
						GroupCfgEmplsTable departTable2) {
		super(mainFrame);
		this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxPeriod2 = comboBoxPeriod2;
		this.departTable = departTable;
		this.departTable2 = departTable2;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
	
			PeriodComboBoxModel pCBM
				= (PeriodComboBoxModel) comboBoxPeriod.getModel();
			PeriodComboBoxModel pCBM2
			= (PeriodComboBoxModel) comboBoxPeriod2.getModel();
			int indx = comboBoxPeriod.getSelectedIndex();
			Period prd  = pCBM.getSelectedItem();
			if (prd == null ) {
				JOptionPane.showMessageDialog(mainFrame,  
						"Please Select period !",
						ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_WARN) , JOptionPane.WARNING_MESSAGE);
				return;
			}
			for (RevenueEmplPeriod empl: prd.getRevenueEmplPeriods()) {
				
				if (empl.getId() != null) {
					RevenueEmplPeriod attachedEmp = em.find(RevenueEmplPeriod.class, empl.getId());
					em.remove(attachedEmp);
				}
			}
			for (EmployeeSettings es: prd.getEmployeeSettingses()) {
				EmployeeSettings attachedEs = em.find(EmployeeSettings.class, es.getId());
				em.remove(attachedEs);
			}
			for (EmployeeSalary es: prd.getEmployeeSalaries()) {
				
				EmployeeSalary attachedEs = em.find(EmployeeSalary.class, es.getId());
				em.remove(attachedEs);
			}
			for (RevenueDeptPeriod rdp: prd.getRevenueDeptPeriods()) {
				
				RevenueDeptPeriod attachedRdp = em.find(RevenueDeptPeriod.class, rdp.getId());
				em.remove(attachedRdp);
			}
			for (EmplDeptPeriod edp: prd.getEmplDeptPeriods()) {
				
				EmplDeptPeriod attachedEdp = em.find(EmplDeptPeriod.class, edp.getId());
				em.remove(attachedEdp);
			}
			
			for (PeriodSetting ps: prd.getPeriodSettings()) {
				
				PeriodSetting attachedEs = em.find(PeriodSetting.class, ps.getId());
				em.remove(attachedEs);
			}
			
			Query q = em.createQuery(" from InputData  where  period.id = :periodId");
			q.setParameter("periodId", prd.getId());
			List<InputData> allInputDatas = (List<InputData>) q.getResultList();
			for (InputData in: allInputDatas) {
				//PeriodSetting attachedEs = em.find(PeriodSetting.class, ps.getId());
				em.remove(in);
			}
			
			System.out.println("Period : " + prd.getCode() + " Deleted" );
			Period attachedPrd = em.find(Period.class, prd.getId());
			em.remove(attachedPrd);
			
			Query qPeriods = em.createQuery(" from Period");
			List<Period> allPeriods = (List<Period>) qPeriods.getResultList();
			
			
			em.flush();
			//((PeriodComboBoxModel) 
			//		comboBoxPeriod.getModel()).гreinit(allPeriods);
			comboBoxPeriod.remove(indx);
			comboBoxPeriod.revalidate();
			comboBoxPeriod.repaint();
			
			((PeriodComboBoxModel) 
					comboBoxPeriod2.getModel()).reinit(allPeriods);
			comboBoxPeriod2.remove(indx);
			comboBoxPeriod2.revalidate();
			comboBoxPeriod2.repaint();
			
			((GroupCfgEmplsTableModel) departTable.getModel()).removeData();
			((GroupCfgEmplsTableModel) departTable2.getModel()).removeData();
			departTable.repaint();
			departTable.revalidate();
			departTable2.repaint();
			departTable2.revalidate();
			
		} catch (Exception e1) {
			Logger.error(e1);
			try {
				JOptionPane.showMessageDialog(mainFrame, 
						 "Period delete failure !", 
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
