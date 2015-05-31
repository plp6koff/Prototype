package com.consultancygrid.trz.actionListener.statistic;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.personal.statistic1.mini.PrsStat1MiniCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic1.mini.PrsStat1MiniCfgEmplsTableModel;
import com.consultancygrid.trz.util.XslGeneratorUtil;

public class ExportStatistic31MiniAL extends BaseActionListener {

	private JFileChooser fc;
	private PrsStat1MiniCfgEmplsTable prsStat1CfgEmplsTable;
	private String periodCode;

	public ExportStatistic31MiniAL(
			PrototypeMainFrame mainFrame,
			JFileChooser fc,
			PrsStat1MiniCfgEmplsTable prsStat1CfgEmplsTabl,
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
			PrsStat1MiniCfgEmplsTableModel model  
					= (PrsStat1MiniCfgEmplsTableModel)prsStat1CfgEmplsTable.getModel();
			XslGeneratorUtil.generateCore(
					model.getTableHeaders(), 
					model.getData(), 
					selectedPath.getAbsolutePath(), 
					"Statistic1-Short-" + periodCode + ".xls");
		} catch (Exception e1) {
			Logger.error(e1);
			rollBack();
		} finally {
			commit();
		}	
	}

}
