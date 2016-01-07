package com.consultancygrid.trz.actionListener.statistic;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.statistic1.PrsStat1CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic1.PrsStat1CfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.personal.statistic1.PrsStat1Util;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class LoadStatistic1AL extends BaseActionListener {


	private JPanel firstInnerPanel;
	//private JComboBox<Employee> comboBoxEmployee;
	private JFileChooser fc;
	private JComboBox<Period> comboBoxPeriod;
	private PrsStat1CfgEmplsTable personalConfTable;
	
	public LoadStatistic1AL(
			PrototypeMainFrame mainFrame, 
			JPanel firstInnerPanel,
			JComboBox<Period> comboBoxPeriod,
			PrsStat1CfgEmplsTable personalConfTable,
			JFileChooser fc) {

		super(mainFrame);
		this.firstInnerPanel = firstInnerPanel;
		this.comboBoxPeriod = comboBoxPeriod;
		this.personalConfTable = personalConfTable;
		this.fc = fc;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			init();
			PrsStat1CfgEmplsTableModel model = new PrsStat1CfgEmplsTableModel();
			//EmplComboBoxModel emplMod = ((EmplComboBoxModel) comboBoxEmployee.getModel());
			PeriodComboBoxModel periodMod = ((PeriodComboBoxModel) comboBoxPeriod.getModel());
			
			//Employee employee = emplMod.getSelectedItem();
			Period 	 period   = periodMod.getSelectedItem();
			
			if (//employee == null || 
					period == null) {
				JOptionPane.showMessageDialog(mainFrame, 
									 "Please select Period Code and Employee code !", 
									  ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_WARN) , JOptionPane.WARNING_MESSAGE);
				return;
			}
			PrsStat1Util.load(em, model, period.getCode());
			personalConfTable.setModel(model);
			
			JButton exportButt = new JButton(ResourceLoaderUtil
					.getLabels(LabelsConstants.STAT_TAB_EXPORT_BUTT));
			exportButt.setEnabled(true);
			exportButt.setBounds(1000, 40, 150, 25);
			exportButt.addActionListener(new ExportStatistic31AL(mainFrame, fc, personalConfTable, period.getCode()));
			firstInnerPanel.add(exportButt);
			firstInnerPanel.revalidate();
			firstInnerPanel.repaint();
		} catch (Exception e1) {
			Logger.error(e1);
			rollBack();
		} finally {
			commit();
		}	
	}

}
