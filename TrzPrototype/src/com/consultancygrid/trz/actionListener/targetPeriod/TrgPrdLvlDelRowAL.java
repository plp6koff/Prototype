package com.consultancygrid.trz.actionListener.targetPeriod;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.TargetLevels;
import com.consultancygrid.trz.model.TargetPeriod;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTable;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.PersonalCfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTable;
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTableModel;
import com.consultancygrid.trz.util.GroupTablPeriodLoaderUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class TrgPrdLvlDelRowAL extends BaseActionListener{

	
	private TargetLevelCfgTable tLvlConfTable;
	private JComboBox comboBoxTargetPeriod;
	
	public TrgPrdLvlDelRowAL(PrototypeMainFrame mainFrame,  
							 JComboBox comboBoxTargetPeriod,TargetLevelCfgTable tLvlConfTable) {
		
		super(mainFrame);
		this.tLvlConfTable = tLvlConfTable;
		this.comboBoxTargetPeriod = comboBoxTargetPeriod;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			TargetPeriod targetPeriod =  (TargetPeriod)comboBoxTargetPeriod.getSelectedItem();
			
			TargetLevelCfgTableModel model = (TargetLevelCfgTableModel) tLvlConfTable.getModel();
			int i = tLvlConfTable.getSelectedRow();
			UUID id = UUID.fromString((String)model.getValueAt(i, 0));
			
			
			if (targetPeriod != null) {
				
				
				Query q = em.createQuery("from TargetLevels as tl  where  tl.id = :id");
				q.setParameter("id", id);
				List<TargetLevels> allTargetLevels = (List<TargetLevels>) q.getResultList();
				
				if (allTargetLevels != null && !allTargetLevels.isEmpty()) {
					
					TargetLevels tl = allTargetLevels.get(0);
					em.remove(tl);
				
				}
				
				Query q1 = em.createQuery(" from TargetLevels as tl  where  tl.targetPeriod.id = :targetId");
				q1.setParameter("targetId", targetPeriod.getId());
				
				List<TargetLevels> allTPLs = (List<TargetLevels>) q1.getResultList();
				
				Vector<Object> data = new Vector<>();
				for (TargetLevels tL : allTPLs) {
					
					Vector<Object> tempRow = new Vector<>();
					tempRow.add(tL.getId().toString());
					tempRow.add(tL.getTargetPercent().toString());
					tempRow.add(tL.getTargetBonus().toString());
					data.add(tempRow);
				}
				model.setData(data);
				
				
				tLvlConfTable.clearSelection();
				tLvlConfTable.revalidate();
				tLvlConfTable.repaint();
				
			} else {
				JOptionPane.showMessageDialog(mainFrame, ResourceLoaderUtil.getLabels(LabelsConstants.PERSONAL_CFG_WARN2) ,
					 ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_WARN), JOptionPane.WARNING_MESSAGE);
				return;
			}
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
			}
		}	
	}
	
	
	private BigDecimal parseValue(BigDecimal initValue, String newValStr) {
		
		try {
			double parsedDoub = Double.valueOf(newValStr);
			return BigDecimal.valueOf(parsedDoub);
		} catch (Exception e)  {
			 return initValue;
		}
	}
}
