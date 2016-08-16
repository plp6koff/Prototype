package com.consultancygrid.trz.actionListener.group;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

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
import com.consultancygrid.trz.util.HibernateUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class PeriodDelRowAL extends BaseActionListener{
	
	private static Logger log = Logger.getLogger(PeriodDelRowAL.class); 

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
		
		EntityManager em = null;
		EntityTransaction trx = null;
		try {
			
			em = HibernateUtil.getEntityManager();
			trx = em.getTransaction();
			trx.begin();
	
			PeriodComboBoxModel pCBM
				= (PeriodComboBoxModel) comboBoxPeriod.getModel();
			PeriodComboBoxModel pCBM2
			= (PeriodComboBoxModel) comboBoxPeriod2.getModel();
			int indx = comboBoxPeriod.getSelectedIndex();
			Period prd  = pCBM.getSelectedItem();
			if ( prd == null || prd.getId() == null ) {
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
				em.remove(in);
			}
			
			log.debug("Period : " + prd.getCode() + " Deleted" );
			Period attachedPrd = em.find(Period.class, prd.getId());
			em.remove(attachedPrd);
			
			Query qPeriods = em.createQuery(" from Period");
			List<Period> allPeriods = (List<Period>) qPeriods.getResultList();
			PeriodComboBoxModel newModel = new PeriodComboBoxModel();
			newModel.addAll(allPeriods);
			
			em.flush();

			
			comboBoxPeriod.setModel(newModel);
			comboBoxPeriod.revalidate();
			comboBoxPeriod.repaint();
			
			comboBoxPeriod2.setModel(newModel);
			comboBoxPeriod2.revalidate();
			comboBoxPeriod2.repaint();
			
			((GroupCfgEmplsTableModel) departTable.getModel()).removeData();
			((GroupCfgEmplsTableModel) departTable2.getModel()).removeData();
			departTable.repaint();
			departTable.revalidate();
			departTable2.repaint();
			departTable2.revalidate();
			
		} catch (Exception e1) {
			log.error(e1);
			try {
				JOptionPane.showMessageDialog(mainFrame, 
						 "Period delete failure !", 
						 ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR),JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException | IOException e2) {
				log.error(e2);
			}
			if (trx!= null && trx.isActive()) {
				trx.rollback();
			}
		} finally {
			if (trx!= null && trx.isActive()) {
				trx.commit();
			}
		}	
	}
	
}
