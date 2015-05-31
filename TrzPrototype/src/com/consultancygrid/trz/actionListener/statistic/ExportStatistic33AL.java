package com.consultancygrid.trz.actionListener.statistic;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.statistic3.PrsStat3CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic3.PrsStat3CfgEmplsTableModel;
import com.consultancygrid.trz.util.XslGeneratorUtil;

public class ExportStatistic33AL extends BaseActionListener {

	private JFileChooser fc;
	private PrsStat3CfgEmplsTable prsStat3CfgEmplsTable;

	public ExportStatistic33AL(
			PrototypeMainFrame mainFrame,
			JFileChooser fc,
			PrsStat3CfgEmplsTable prsStat3CfgEmplsTable) {

		super(mainFrame);
		this.fc = fc;
		this.prsStat3CfgEmplsTable = prsStat3CfgEmplsTable;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			init();
			fc.showSaveDialog(mainFrame);
			File selectedPath = fc.getSelectedFile();
			PrsStat3CfgEmplsTableModel model  
					= (PrsStat3CfgEmplsTableModel) prsStat3CfgEmplsTable.getModel();
			XslGeneratorUtil.generateCore(
					model.getTableHeaders(), 
					model.getData(), 
					selectedPath.getAbsolutePath(), 
					"Statistic-3.xls");
		} catch (Exception e1) {
			Logger.error(e1);
			rollBack();
		} finally {
			commit();
		}	
	}

}
