package com.consultancygrid.trz.actionListener.statistic;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.statistic2.PrsStat2CfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic2.PrsStat2CfgEmplsTableModel;
import com.consultancygrid.trz.util.XslGeneratorUtil;

public class ExportStatistic32AL extends BaseActionListener {

	private JFileChooser fc;
	private PrsStat2CfgEmplsTable prsStat2CfgEmplsTable;
	private String mCode;

	public ExportStatistic32AL(
			PrototypeMainFrame mainFrame,
			JFileChooser fc,
			PrsStat2CfgEmplsTable prsStat2CfgEmplsTabl,
			String mCode) {

		super(mainFrame);
		this.fc = fc;
		this.prsStat2CfgEmplsTable = prsStat2CfgEmplsTabl;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			init();
			fc.showSaveDialog(mainFrame);
			File selectedPath = fc.getSelectedFile();
			PrsStat2CfgEmplsTableModel model  
					= (PrsStat2CfgEmplsTableModel)prsStat2CfgEmplsTable.getModel();
			XslGeneratorUtil.generateCore(
					model.getTableHeaders(), 
					model.getData(), 
					selectedPath.getAbsolutePath(), 
					"Statistic-2" + mCode + ".xls");
		} catch (Exception e2) {
			Logger.error(e2);
			rollBack();
		} finally {
			commit();
		}	
	}

}
