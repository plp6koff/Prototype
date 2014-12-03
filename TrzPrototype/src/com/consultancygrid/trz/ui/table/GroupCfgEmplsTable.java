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

public class GroupCfgEmplsTable extends JTable {

	private static final long serialVersionUID = 1L;

	private boolean enableEdit = false;
	
	private int rowEditable = 0;
	
	private Set editableY = new HashSet<>();
	
	public Component prepareRenderer(TableCellRenderer renderer, int Index_row,
			int Index_col) {
		// get the current row
		Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		// even index, not selected
		Set xValue = new HashSet<>();
		Set yValue = new HashSet<>();

		int x = 5;
		int y = 6;

		Set totalXValues = new HashSet<>();
		Set totalYValues = new HashSet<>();

		int totalX = 8;
		int totalY = 3;

		Set profitXValues = new HashSet<>();
		Set profitAllYValues = new HashSet<>();
		Set profitGroupYValues = new HashSet<>();

		int profitX = 2;
		int profitAllY = 0;
		int profitGroupY = 1;

		profitXValues.add(profitX);
		profitXValues.add(profitX = +6);
		profitXValues.add(profitX = +7);
		//profitXValues.add(profitX = +8);

		Set totalProfitXValues = new HashSet<>();
		Set totalProfitYValues = new HashSet<>();

		int totalProfitX = 4;
		int totalProfitY = 2;

		totalProfitXValues.add(totalProfitX);
		totalProfitXValues.add(totalProfitX += 2);
		totalProfitXValues.add(totalProfitX += 1);
		//totalProfitXValues.add(totalProfitX += 1);
		while (x < 100) {
			xValue.add(x);
			yValue.add(y);
			totalXValues.add(totalX);
			totalYValues.add(totalY);

			profitAllYValues.add(profitAllY);
			profitGroupYValues.add(profitGroupY);

			totalProfitYValues.add(totalProfitY);
			// init them all
			x = x + 7;
			y = y + 7;
			totalX = totalX + 7;
			totalY = totalY + 7;

			profitAllY = profitAllY + 7;
			profitGroupY = profitGroupY + 7;

			totalProfitY = totalProfitY + 7;
		}

		if (xValue.contains(Index_row) || yValue.contains(Index_row)) {
			comp.setBackground(Color.lightGray);
		} else if (totalXValues.contains(Index_col)
				&& totalYValues.contains(Index_row)) {
			comp.setBackground(Color.yellow);
		} else if (profitXValues.contains(Index_col)
				&& (profitAllYValues.contains(Index_row) || profitGroupYValues
						.contains(Index_row))
				|| (totalProfitXValues.contains(Index_col) && totalProfitYValues
						.contains(Index_row))) {
			comp.setBackground(Color.green);
		} else {
			comp.setBackground(Color.white);
		}
		
		
		if (enableEdit && editableY.contains(this.getSelectedRow())) {
			if (((Index_row == rowEditable 
					|| Index_row == rowEditable + 1 
					|| Index_row == rowEditable + 2) 
					&& (Index_col == 6))
				|| ((Index_row == rowEditable 
						|| Index_row == rowEditable + 1) 
						&& (Index_col == 7))) {
			comp.setBackground(Color.yellow);
			
			}
		} 
		
		return comp;
	}

	public GroupCfgEmplsTable() throws IOException {

		
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setModel(new GroupCfgEmplsTableModel());
		getColumnModel().getColumn(0).setMinWidth(col0MW);
		getColumnModel().getColumn(1).setMinWidth(col1MW);
		getColumnModel().getColumn(2).setMinWidth(col2MW);
		getColumnModel().getColumn(3).setMinWidth(col3MW);
		getColumnModel().getColumn(4).setMinWidth(col4MW);
		getColumnModel().getColumn(5).setMinWidth(col5MW);
		getColumnModel().getColumn(6).setMinWidth(col6MW);
		// TODO comment until it is required
		getColumnModel().getColumn(7).setMinWidth(col9MW);
		//getColumnModel().getColumn(8).setMinWidth(col8MW);
		getColumnModel().getColumn(8).setMinWidth(col6MW);
		getColumnModel().getColumn(9).setMinWidth(col10MW);
		int y = 0;
		while (y < 100) {
			editableY.add(y);
			y = y + 7;
			
		}
	}
	
	public boolean isEnableEdit() {
		return enableEdit;
	}

	public void setEnableEdit(boolean enableEdit) {
		this.enableEdit = enableEdit;
	}

	@Override
	public boolean isCellEditable(int Index_row, int Index_col) {
		
		if (enableEdit && editableY.contains(Index_row)) {
			
			return (((Index_row == rowEditable 
					|| Index_row == rowEditable + 1 
					|| Index_row == rowEditable + 2) 
					&& (Index_col == 6))
				|| ((Index_row == rowEditable 
						|| Index_row == rowEditable + 1) 
						&& (Index_col == 7)));
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

	public Set getEditableRows() {
		return this.editableY;
	}
}
