package com.consultancygrid.trz.ui.table.personal.statistic1.mini;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class PrsStat1MiniCfgEmplsTable extends JTable {

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

	public PrsStat1MiniCfgEmplsTable() throws IOException {
		
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setModel(new PrsStat1MiniCfgEmplsTableModel());
		getColumnModel().getColumn(0).setMinWidth(150);
		getColumnModel().getColumn(1).setMaxWidth(250);
		getColumnModel().getColumn(2).setMinWidth(150);
		getColumnModel().getColumn(3).setMinWidth(150);
		getColumnModel().getColumn(4).setMaxWidth(250);
		getColumnModel().getColumn(5).setMinWidth(150);
		getColumnModel().getColumn(6).setMinWidth(150);
		getColumnModel().getColumn(7).setMinWidth(150);
		setRowHeight(25);
	}

	
}
