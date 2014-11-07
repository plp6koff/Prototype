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

	public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
		//TODO implement if  needed
		Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		return comp;
	}

	public PersonalCfgEmplsTable() throws IOException {

		setAutoResizeMode(HEIGHT);
		setAutoResizeMode(WIDTH);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setModel(new PersonalCfgEmplsTableModel());
		getColumnModel().getColumn(0).setMinWidth(col6MW);
		getColumnModel().getColumn(1).setMinWidth(col6MW);
		getColumnModel().getColumn(2).setMinWidth(col6MW);
		getColumnModel().getColumn(3).setMinWidth(col6MW);
		getColumnModel().getColumn(4).setMinWidth(col6MW);
		getColumnModel().getColumn(5).setMinWidth(col6MW);
		getColumnModel().getColumn(6).setMinWidth(col6MW);
		getColumnModel().getColumn(7).setMinWidth(col6MW);
		getColumnModel().getColumn(8).setMinWidth(col6MW);
		getColumnModel().getColumn(9).setMinWidth(col6MW);
		getColumnModel().getColumn(10).setMinWidth(col6MW);
		getColumnModel().getColumn(11).setMinWidth(col6MW);
		getColumnModel().getColumn(12).setMinWidth(col6MW);
		getColumnModel().getColumn(13).setMinWidth(col6MW);
		getColumnModel().getColumn(14).setMinWidth(col6MW);
		getColumnModel().getColumn(15).setMinWidth(col6MW);
		getColumnModel().getColumn(16).setMinWidth(col6MW);
		getColumnModel().getColumn(17).setMinWidth(col6MW);
		getColumnModel().getColumn(18).setMinWidth(col6MW);
		getColumnModel().getColumn(19).setMinWidth(col6MW);
		getColumnModel().getColumn(20).setMinWidth(col6MW);
		getColumnModel().getColumn(21).setMinWidth(col6MW);
		getColumnModel().getColumn(22).setMinWidth(col6MW);
		getColumnModel().getColumn(23).setMinWidth(col6MW);
		getColumnModel().getColumn(24).setMinWidth(col6MW);
		getColumnModel().getColumn(25).setMinWidth(col6MW);
	}

}
