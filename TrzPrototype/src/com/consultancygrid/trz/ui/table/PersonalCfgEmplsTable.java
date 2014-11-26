package com.consultancygrid.trz.ui.table;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import static com.consultancygrid.trz.base.Constants.*;

public class PersonalCfgEmplsTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	private boolean enableEdit = false;
	
	private int rowEditable = 0;

	public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
		//TODO implement if  needed
		Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		
		if (enableEdit) {
			if ((Index_row == rowEditable) && (Index_col == 5 || Index_col == 7 || Index_col ==9 )) {
			comp.setBackground(Color.yellow);
			
			}
		}
		return comp;
	}

	public PersonalCfgEmplsTable() throws IOException {
		
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setModel(new PersonalCfgEmplsTableModel());
		getColumnModel().getColumn(0).setMinWidth(100);
		getColumnModel().getColumn(1).setMinWidth(100);
		getColumnModel().getColumn(2).setMinWidth(150);
		getColumnModel().getColumn(3).setMinWidth(300);
		getColumnModel().getColumn(4).setMinWidth(350);
		getColumnModel().getColumn(5).setMinWidth(300);
		getColumnModel().getColumn(6).setMinWidth(50);
		getColumnModel().getColumn(7).setMinWidth(150);
		getColumnModel().getColumn(8).setMinWidth(150);
		getColumnModel().getColumn(9).setMinWidth(150);
		getColumnModel().getColumn(10).setMinWidth(300);
		getColumnModel().getColumn(11).setMinWidth(350);
		getColumnModel().getColumn(12).setMinWidth(100);
		getColumnModel().getColumn(13).setMinWidth(50);
		getColumnModel().getColumn(14).setMinWidth(100);
		getColumnModel().getColumn(15).setMinWidth(100);
		getColumnModel().getColumn(16).setMinWidth(150);
		getColumnModel().getColumn(17).setMinWidth(150);
		getColumnModel().getColumn(18).setMinWidth(150);
		getColumnModel().getColumn(19).setMinWidth(50);
		getColumnModel().getColumn(20).setMinWidth(70);
		getColumnModel().getColumn(21).setMinWidth(100);
		getColumnModel().getColumn(22).setMinWidth(70);
		getColumnModel().getColumn(23).setMinWidth(50);
		getColumnModel().getColumn(24).setMinWidth(100);
		getColumnModel().getColumn(25).setMinWidth(100);
		
		getColumnModel().getColumn(26).setMinWidth(250);
	}

	public boolean isEnableEdit() {
		return enableEdit;
	}

	public void setEnableEdit(boolean enableEdit) {
		this.enableEdit = enableEdit;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		
		if (enableEdit) {
			
			return ((row == rowEditable) && (column == 5 || column == 7 || column ==9 ));
		} else {
			return enableEdit;
		}
	}

	public int getRowEditable() {
		return rowEditable;
	}

	public void setRowEditable(int rowEditable) {
		this.rowEditable = rowEditable;
	}
	
	
}
