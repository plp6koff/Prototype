package com.consultancygrid.trz.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.consultancygrid.trz.ui.PrototypeMainFrame;



public class BaseActionListener implements ActionListener {

	
	protected PrototypeMainFrame mainFrame;
	
	
	public BaseActionListener(PrototypeMainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
