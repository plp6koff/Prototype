package com.consultancygrid.trz.ui.table;

import java.awt.Component;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;


public class TargetLevelCfgTable extends JTable {

	private static final long serialVersionUID = 1L;
	

	public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
		Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		return comp;
	}

	public TargetLevelCfgTable() throws IOException {
		
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setModel(new PersonalCfgEmplsTableModel());
		getColumnModel().getColumn(0).setMaxWidth(70);
		getColumnModel().getColumn(1).setMinWidth(70);
		getColumnModel().getColumn(2).setMinWidth(70);
		setRowHeight(50);
	}

	
}
