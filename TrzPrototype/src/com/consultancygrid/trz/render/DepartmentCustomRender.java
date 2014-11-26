package com.consultancygrid.trz.render;

import java.awt.Component;
import java.io.IOException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class DepartmentCustomRender implements ListCellRenderer {

	
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	 public Component getListCellRendererComponent (JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
		  JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
			        isSelected, cellHasFocus);
		  
		  if (value instanceof Department) {
			  
			  Department tempDepartment = (Department) value;
			  renderer.setText(tempDepartment.getCode());
			  
		  } else {
			  try {
				renderer.setText(ResourceLoaderUtil.getLabels(LabelsConstants.COMBO_DEP_));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  
		  return renderer;
     }
}
