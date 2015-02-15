package com.consultancygrid.trz.ui.table.targetLevel;

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
		
		setAutoResizeMode(AUTO_RESIZE_OFF);
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setModel(new TargetLevelCfgTableModel());
		getColumnModel().getColumn(0).setMaxWidth(0);
		getColumnModel().getColumn(0).setWidth(0);
		getColumnModel().getColumn(1).setMinWidth(100);
		getColumnModel().getColumn(1).setPreferredWidth(100);
		getColumnModel().getColumn(2).setMinWidth(100);
		getColumnModel().getColumn(2).setPreferredWidth(100);
		setRowHeight(30);
	}

	
}
