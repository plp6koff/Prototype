package com.consultancygrid.trz.actionListener.statistic;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.statistic1.PrsStat1CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic1.PrsStat1CfgEmplsTableModel;
import com.consultancygrid.trz.util.XslGeneratorUtil;

public class ExportStatistic31AL extends BaseActionListener {

	private JFileChooser fc;
	private PrsStat1CfgEmplsTable prsStat1CfgEmplsTable;
	private String periodCode;

	public ExportStatistic31AL(
			PrototypeMainFrame mainFrame,
			JFileChooser fc,
			PrsStat1CfgEmplsTable prsStat1CfgEmplsTabl,
			String periodCode) {

		super(mainFrame);
		this.fc = fc;
		this.prsStat1CfgEmplsTable = prsStat1CfgEmplsTabl;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			init();
			fc.showSaveDialog(mainFrame);
			File selectedPath = fc.getSelectedFile();
			PrsStat1CfgEmplsTableModel model  
					= (PrsStat1CfgEmplsTableModel)prsStat1CfgEmplsTable.getModel();
			XslGeneratorUtil.generateCore(
					model.getTableHeaders(), 
					model.getData(), 
					selectedPath.getAbsolutePath(), 
					"Statistic-" + periodCode + ".xls");
		} catch (Exception e1) {
			Logger.error(e1);
			rollBack();
		} finally {
			commit();
		}	
	}

}
