package com.consultancygrid.trz.render;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class HeaderRenderer implements TableCellRenderer { 

	private static final Font labelFont = new Font("Arial", Font.BOLD, 11);

	private TableCellRenderer delegate;

	 public HeaderRenderer(TableCellRenderer delegate) {
	     this.delegate = delegate;
	 } 
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		 Component c = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		    if(c instanceof JLabel) {
		        JLabel label = (JLabel) c;
		        label.setFont(labelFont);
		        label.setVerticalTextPosition(SwingConstants.CENTER);
		        label.setBorder(BorderFactory.createEtchedBorder());
		        
		    }
		    return c;
	}
	
}
