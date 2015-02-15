package com.consultancygrid.trz.actionListener.targetPeriod;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.TargetLevels;
import com.consultancygrid.trz.model.TargetPeriod;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTable;
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTableModel;
import com.consultancygrid.trz.util.ResourceLoaderUtil;
/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class TrgPrdLvlsCfgComboAL extends BaseActionListener {

	
	private JComboBox comboBoxTarget;
	private TargetLevelCfgTable table;
	private JPanel createFormPanel; 
	
	public TrgPrdLvlsCfgComboAL(PrototypeMainFrame mainFrame,JPanel createFormPanel,TargetLevelCfgTable table,JComboBox comboBoxTarget) {

		super(mainFrame);
		this.comboBoxTarget = comboBoxTarget;
		this.createFormPanel = createFormPanel;
		this.table = table;
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
				tempRow.add(tL.getId().toString());
				tempRow.add(tL.getTargetPercent().toString());
				tempRow.add(tL.getTargetBonus().toString());
				data.add(tempRow);
			}
			tlModel.setData(data);
			
			table.setModel(tlModel);
			table.removeColumn(table.getColumnModel().getColumn(0));
			table.revalidate();
			createFormPanel.revalidate();
			createFormPanel.repaint();
			
			
			JButton btnSavePeriod = new JButton(ResourceLoaderUtil
					.getLabels(LabelsConstants.TRG_SETT_TAB_LVLS_ADD));
			btnSavePeriod.setBounds(300, 100, 100, 25);
			btnSavePeriod.addActionListener(new TrgPrdLvlAddRowAL(mainFrame, comboBoxTarget, table));
			createFormPanel.add(btnSavePeriod);
			JButton btnDelPeriod = new JButton(ResourceLoaderUtil
					.getLabels(LabelsConstants.TRG_SETT_TAB_LVLS_DEL));
			btnDelPeriod.setBounds(300, 140, 100, 25);
			btnDelPeriod.addActionListener(new TrgPrdLvlDelRowAL(mainFrame, comboBoxTarget, table));
			createFormPanel.add(btnDelPeriod);
			JButton btnEditPeriod = new JButton(ResourceLoaderUtil
					.getLabels(LabelsConstants.TRG_SETT_TAB_LVLS_EDIT));
			btnEditPeriod.setBounds(300, 180, 100, 25);
			btnEditPeriod.addActionListener(new TrgPrdLvlEditRowAL(mainFrame, comboBoxTarget, table));
			createFormPanel.add(btnEditPeriod);
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
