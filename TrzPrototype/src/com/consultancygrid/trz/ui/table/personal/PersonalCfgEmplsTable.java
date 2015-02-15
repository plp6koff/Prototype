package com.consultancygrid.trz.ui.table.personal;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class PersonalCfgEmplsTable extends JTable {

	private static final long serialVersionUID = 1L;
	

	public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
		//TODO implement if  needed
		Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		
		if (Index_col == 0) {
			comp.setBackground(Color.green);
		} else {
			comp.setBackground(Color.LIGHT_GRAY);	
		}
		return comp;
	}

	public PersonalCfgEmplsTable() throws IOException {
		
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setModel(new PersonalCfgEmplsTableModel());
		getColumnModel().getColumn(0).setMaxWidth(70);
		getColumnModel().getColumn(1).setMinWidth(70);
		getColumnModel().getColumn(2).setMinWidth(70);
		getColumnModel().getColumn(3).setMinWidth(70);
		getColumnModel().getColumn(4).setMinWidth(100);
		getColumnModel().getColumn(5).setMinWidth(105);
		getColumnModel().getColumn(6).setMinWidth(90);
		getColumnModel().getColumn(7).setMaxWidth(80);
		getColumnModel().getColumn(8).setMinWidth(80);
		getColumnModel().getColumn(9).setMinWidth(150);
		getColumnModel().getColumn(10).setMinWidth(70);
		getColumnModel().getColumn(11).setMinWidth(100);
		getColumnModel().getColumn(12).setMinWidth(100);
		getColumnModel().getColumn(13).setMinWidth(70);
		getColumnModel().getColumn(14).setMaxWidth(200);
		getColumnModel().getColumn(15).setMinWidth(70);
		getColumnModel().getColumn(16).setMinWidth(70);
		getColumnModel().getColumn(17).setMinWidth(80);
		getColumnModel().getColumn(18).setMinWidth(80);
		getColumnModel().getColumn(19).setMinWidth(80);
	
		setRowHeight(50);
	}

	
}
