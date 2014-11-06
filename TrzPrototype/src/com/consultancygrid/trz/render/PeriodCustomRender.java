package com.consultancygrid.trz.render;

import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.util.DateTimeTools;

public class PeriodCustomRender implements ListCellRenderer {

	private static SimpleDateFormat sdf = new SimpleDateFormat(DateTimeTools.DATE_PATTERN_DMY_DASH);
	
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	 public Component getListCellRendererComponent (JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
			        isSelected, cellHasFocus);
		  
		  if (value instanceof Period) {
			  
			  Period tempPeriod = (Period) value;
			  renderer.setText(sdf.format(tempPeriod.getDateStart()) + " - " + sdf.format(tempPeriod.getDateEnd()));
			  
		  } else {
			  renderer.setText("Select period ...");
		  }
		  
		  return renderer;
     }
}
