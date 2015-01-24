package com.consultancygrid.trz.ui.table;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class EmployeeActiveTable extends JTable {

	private static final long serialVersionUID = 1L;
	

	public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
		//TODO implement if  needed
		Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		
		if (Index_col == 0) {
			comp.setBackground(Color.LIGHT_GRAY);
		} 
		return comp;
	}

	public EmployeeActiveTable(DefaultTableModel model) throws IOException {
		
		setModel(model);
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		setBorder(new EmptyBorder(1, 1, 1, 1));
		getColumnModel().getColumn(0).setMinWidth(100);
		getColumnModel().getColumn(1).setMinWidth(100);
		setRowHeight(30);
	}
	
	
	
	  @Override
      public Class getColumnClass(int column) {
          switch (column) {
              case 0:
                  return String.class;
              default:
                  return Boolean.class;
          }
      }
	  
	
}
