package com.consultancygrid.trz.actionListener.statistic;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.statistic5.PrsStat5CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic5.PrsStat5CfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.personal.statistic5.PrsStat5Util;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class LoadStatistic5AL extends BaseActionListener {


	private JPanel firstInnerPanel;
	private JComboBox<Employee> comboBoxEmployee;
	private JFileChooser fc;
	private JComboBox<Period> comboBoxPeriod;
	private PrsStat5CfgEmplsTable personalConfTable;
	
	public LoadStatistic5AL(
			PrototypeMainFrame mainFrame, 
			JPanel firstInnerPanel,
			JComboBox<Period> comboBoxPeriod,
			JComboBox<Employee> comboBoxEmployee,
			PrsStat5CfgEmplsTable personalConfTable,
			JFileChooser fc) {

		super(mainFrame);
		this.firstInnerPanel = firstInnerPanel;
		this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxEmployee = comboBoxEmployee;
		this.personalConfTable = personalConfTable;
		this.fc = fc;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			init();
			PrsStat5CfgEmplsTableModel model = new PrsStat5CfgEmplsTableModel();
			EmplComboBoxModel emplMod = ((EmplComboBoxModel) comboBoxEmployee.getModel());
			PeriodComboBoxModel periodMod = ((PeriodComboBoxModel) comboBoxPeriod.getModel());
			
			Employee employee = emplMod.getSelectedItem();
			Period 	 period   = periodMod.getSelectedItem();
			
			PrsStat5Util.load(em, 
							  model, 
							  (period != null ? period.getCode() : null),
							  (employee != null ? employee.getMatchCode() : null));
			personalConfTable.setModel(model);
			
			JButton exportButt = new JButton(ResourceLoaderUtil
					.getLabels(LabelsConstants.STAT_TAB_EXPORT_BUTT));
			exportButt.setEnabled(true);
			exportButt.setBounds(820, 40, 150, 25);
			exportButt.addActionListener(new ExportStatistic35AL(mainFrame, fc, personalConfTable));
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
