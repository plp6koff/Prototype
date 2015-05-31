package com.consultancygrid.trz.actionListener.statistic;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.statistic2.PrsStat2CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic2.PrsStat2CfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.personal.statistic2.PrsStat2Util;

public class LoadStatistic2AL extends BaseActionListener {


	private JPanel firstInnerPanel;
	private JComboBox<Employee> comboBoxEmployee;
	private JFileChooser fc;
	//private JComboBox<Period> comboBoxPeriod;
	private PrsStat2CfgEmplsTable personalConfTable;
	
	public LoadStatistic2AL(
			PrototypeMainFrame mainFrame, 
			JPanel firstInnerPanel,
			//JComboBox<Period> comboBoxPeriod,
			JComboBox<Employee> comboBoxEmployee,
			PrsStat2CfgEmplsTable personalConfTable,
			JFileChooser fc) {

		super(mainFrame);
		this.firstInnerPanel = firstInnerPanel;
		//this.comboBoxPeriod = comboBoxPeriod;
		this.comboBoxEmployee = comboBoxEmployee;
		this.personalConfTable = personalConfTable;
		this.fc = fc;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			init();
			PrsStat2CfgEmplsTableModel model = new PrsStat2CfgEmplsTableModel();
			EmplComboBoxModel emplMod = ((EmplComboBoxModel) comboBoxEmployee.getModel());
		//	PeriodComboBoxModel periodMod = ((PeriodComboBoxModel) comboBoxPeriod.getModel());
			
			Employee employee = emplMod.getSelectedItem();
			//Period 	 period   = periodMod.getSelectedItem();
			
			
			PrsStat2Util.load(em, model, 
					"",
					(employee != null ? employee.getMatchCode() : ""));
			personalConfTable.setModel(model);
			
			JButton exportButt = new JButton("Export ...");
			exportButt.setEnabled(true);
			exportButt.setBounds(820, 40, 250, 25);
			exportButt.addActionListener(new ExportStatistic32AL(mainFrame, fc, personalConfTable, (employee != null ? employee.getMatchCode() : "ÄllEmployees")));
			firstInnerPanel.add(exportButt);
			firstInnerPanel.revalidate();
			firstInnerPanel.repaint();
		} catch (Exception e2) {
			Logger.error(e2);
			rollBack();
		} finally {
			commit();
		}	
	}

}
