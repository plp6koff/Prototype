package com.consultancygrid.trz.actionListener.statistic;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.statistic3.PrsStat3CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic3.PrsStat3CfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.personal.statistic3.PrsStat3Util;

public class LoadStatistic3AL extends BaseActionListener {


	private JPanel firstInnerPanel;
	private JFileChooser fc;
	private JComboBox<Period> comboBoxPeriod;
	private PrsStat3CfgEmplsTable personalConfTable;
	
	public LoadStatistic3AL(
			PrototypeMainFrame mainFrame, 
			JPanel firstInnerPanel,
			JComboBox<Period> comboBoxPeriod,
			PrsStat3CfgEmplsTable personalConfTable,
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
			PrsStat3CfgEmplsTableModel model = new PrsStat3CfgEmplsTableModel();
			PeriodComboBoxModel periodMod = ((PeriodComboBoxModel) comboBoxPeriod.getModel());
			
			Period 	 period   = periodMod.getSelectedItem();
			
			PrsStat3Util.load(em, 
							  model, 
							  (period != null ? period.getCode() : null));
			personalConfTable.setModel(model);
			
			JButton exportButt = new JButton("Export ...");
			exportButt.setEnabled(true);
			exportButt.setBounds(820, 40, 150, 25);
			exportButt.addActionListener(new ExportStatistic33AL(mainFrame, fc, personalConfTable));
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
