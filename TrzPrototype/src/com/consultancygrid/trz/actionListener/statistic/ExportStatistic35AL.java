package com.consultancygrid.trz.actionListener.statistic;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.statistic5.PrsStat5CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic5.PrsStat5CfgEmplsTableModel;
import com.consultancygrid.trz.util.XslGeneratorUtil;

public class ExportStatistic35AL extends BaseActionListener {

	private JFileChooser fc;
	private PrsStat5CfgEmplsTable prsStat5CfgEmplsTable;

	public ExportStatistic35AL(
			PrototypeMainFrame mainFrame,
			JFileChooser fc,
			PrsStat5CfgEmplsTable prsStat5CfgEmplsTable) {

		super(mainFrame);
		this.fc = fc;
		this.prsStat5CfgEmplsTable = prsStat5CfgEmplsTable;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			init();
			fc.showSaveDialog(mainFrame);
			File selectedPath = fc.getSelectedFile();
			PrsStat5CfgEmplsTableModel model  
					= (PrsStat5CfgEmplsTableModel) prsStat5CfgEmplsTable.getModel();
			XslGeneratorUtil.generateCore(
					model.getTableHeaders(), 
					model.getData(), 
					selectedPath.getAbsolutePath(), 
					"Statistic-5.xls");
		} catch (Exception e1) {
			Logger.error(e1);
			rollBack();
		} finally {
			commit();
		}	
	}

}
