package com.consultancygrid.trz.render;

import java.awt.Component;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.TargetPeriod;
import com.consultancygrid.trz.util.DateTimeTools;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class TargetPeriodCustomRender implements ListCellRenderer {

	private static SimpleDateFormat sdf = new SimpleDateFormat(DateTimeTools.DATE_PATTERN_DMY_DASH);
	
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	 public Component getListCellRendererComponent (JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
			        isSelected, cellHasFocus);
		  
		  if (value instanceof TargetPeriod) {
			  
			  TargetPeriod tempPeriod = (TargetPeriod) value;
			  renderer.setText(tempPeriod.getCode());
			  
		  } else {
			  try {
				renderer.setText(ResourceLoaderUtil.getLabels(LabelsConstants.COMBO_PERIOD));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  
		  return renderer;
     }
}
