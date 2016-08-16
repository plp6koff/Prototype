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
import com.consultancygrid.trz.ui.table.personal.statistic1.mini.PrsStat1MiniCfgEmplsTable;
import com.consultancygrid.trz.ui.table.personal.statistic1.mini.PrsStat1MiniCfgEmplsTableModel;
import com.consultancygrid.trz.ui.table.personal.statistic1.mini.PrsStat1MiniUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class LoadStatistic1MiniAL extends BaseActionListener {


	private JPanel firstInnerPanel;
	private JFileChooser fc;
	private JComboBox<Period> comboBoxPeriod;
	private PrsStat1MiniCfgEmplsTable personalConfTable;
	
	public LoadStatistic1MiniAL(
			PrototypeMainFrame mainFrame, 
			JPanel firstInnerPanel,
			JComboBox<Period> comboBoxPeriod,
			PrsStat1MiniCfgEmplsTable personalConfTable,
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
			PrsStat1MiniCfgEmplsTableModel model = new PrsStat1MiniCfgEmplsTableModel();
			PeriodComboBoxModel periodMod = ((PeriodComboBoxModel) comboBoxPeriod.getModel());
			Period 	 period   = periodMod.getSelectedItem();
			
			if (//employee == null || 
					period == null) {
				JOptionPane.showMessageDialog(mainFrame, 
									 "Please select Period Code and Employee code !", 
									  ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_WARN) , JOptionPane.WARNING_MESSAGE);
				return;
			}
			PrsStat1MiniUtil.load(em, model, period.getCode());
			personalConfTable.setModel(model);
			
			JButton exportButt = new JButton(ResourceLoaderUtil
					.getLabels(LabelsConstants.STAT_TAB_EXPORT_BUTT) + " Mini");
			exportButt.setEnabled(true);
			exportButt.setBounds(1000, 40, 200, 25);
			exportButt.addActionListener(new ExportStatistic31MiniAL(mainFrame, fc, personalConfTable, period.getCode()));
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
