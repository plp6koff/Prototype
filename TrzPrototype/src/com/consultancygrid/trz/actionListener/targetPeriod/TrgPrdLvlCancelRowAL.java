package com.consultancygrid.trz.actionListener.targetPeriod;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.targetLevel.TargetLevelCfgTable;

public class TrgPrdLvlCancelRowAL extends BaseActionListener{

	
	private TargetLevelCfgTable tLvlConfTable;
	private JComboBox comboBoxTargetPeriod;
	private JFrame popUp;
	
	public TrgPrdLvlCancelRowAL(PrototypeMainFrame mainFrame, TargetLevelCfgTable tLvlConfTable, 
							 JComboBox comboBoxTargetPeriod,  
							 JFrame frame) {
		
		super(mainFrame);
		this.tLvlConfTable = tLvlConfTable;
		this.comboBoxTargetPeriod = comboBoxTargetPeriod;
		this.popUp  = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
				
			popUp.setVisible(false);
			tLvlConfTable.clearSelection();
			tLvlConfTable.revalidate();
			tLvlConfTable.repaint();
				
			
	}
	
}
