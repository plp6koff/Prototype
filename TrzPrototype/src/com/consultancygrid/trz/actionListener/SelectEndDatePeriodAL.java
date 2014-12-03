package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;

public class SelectEndDatePeriodAL extends BaseActionListener{

	private JPanel createPanel;
	private HashMap<TrzStatic, JTextField> map ;
	private HashMap<Department, JTextField> mapDept;
	
	public SelectEndDatePeriodAL(PrototypeMainFrame mainFrame, 
								HashMap<TrzStatic, JTextField> map,
								HashMap<Department, JTextField> mapDept,
								JPanel createPanel) {
		super(mainFrame);
		this.map = 	map; 
		this.mapDept = mapDept;
		this.createPanel = createPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			
			for (Entry<TrzStatic, JTextField> entry : map.entrySet()) {
				
				entry.getValue().setText("0.0");
			}
			
			for (Entry<Department, JTextField> entry : mapDept.entrySet()) {
				
				entry.getValue().setText("0.0");
			}
			
			createPanel.setVisible(true);
			createPanel.revalidate();
	}
	
}
