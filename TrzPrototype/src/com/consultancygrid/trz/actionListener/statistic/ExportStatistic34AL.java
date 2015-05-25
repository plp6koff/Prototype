package com.consultancygrid.trz.actionListener.statistic;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.statistic4.PrsStat4CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic4.PrsStat4CfgEmplsTableModel;
import com.consultancygrid.trz.util.XslGeneratorUtil;

public class ExportStatistic34AL extends BaseActionListener {

	private JFileChooser fc;
	private PrsStat4CfgEmplsTable prsStat4CfgEmplsTable;

	public ExportStatistic34AL(
			PrototypeMainFrame mainFrame,
			JFileChooser fc,
			PrsStat4CfgEmplsTable prsStat4CfgEmplsTable) {

		super(mainFrame);
		this.fc = fc;
		this.prsStat4CfgEmplsTable = prsStat4CfgEmplsTable;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			init();
			fc.showSaveDialog(mainFrame);
			File selectedPath = fc.getSelectedFile();
			PrsStat4CfgEmplsTableModel model  
					= (PrsStat4CfgEmplsTableModel) prsStat4CfgEmplsTable.getModel();
			XslGeneratorUtil.generateCore(
					model.getTableHeaders(), 
					model.getData(), 
					selectedPath.getAbsolutePath(), 
					"Statistic-4.xls");
		} catch (Exception e1) {
			Logger.error(e1);
			rollBack();
		} finally {
			commit();
		}	
	}

}
