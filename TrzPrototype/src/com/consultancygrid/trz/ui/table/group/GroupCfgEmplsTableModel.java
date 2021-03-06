/**
 * 
 */
package com.consultancygrid.trz.ui.table.group;

import java.io.IOException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

/**
 * @author user
 *
 */
public class GroupCfgEmplsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5044877015250409328L;


	private Vector<String> tableHeaders = new Vector<String>();

	private Vector data = new Vector();

	public GroupCfgEmplsTableModel() throws IOException {

		tableHeaders = new Vector<String>();
		// Init headers
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL0));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL1));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL2));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL3));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL4));
		tableHeaders.add(" ");
		tableHeaders.add("%");
		// TODO comment until it is required
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL7));

		/*
		 * tableHeaders.add(ResourceLoaderUtil
		 * .getLabels(LabelsConstants.GROUP_CONF_HEADER_COL8));
		 */
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL9));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.GROUP_CONF_HEADER_COL10));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return tableHeaders.size();
	}

	@Override
	public String getColumnName(int col) {
		return tableHeaders.get(col);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	
	public Vector<Object> getRowData(int rowIndex) {
	
		if (data.size() == 0) {
			return null;
		}
		Vector<Object> o = (Vector<Object>) data.get(rowIndex);
		return o;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

		if (data.size() == 0) {
			return null;
		}
		Vector<Object> o = (Vector<Object>) data.get(rowIndex);
		if (o != null) {
			return o.get(columnIndex);
		} else {
			return "EMPTY";
		}

	}

	public Vector<String> getTableHeaders() {
		return tableHeaders;
	}

	public void setData(Vector data) {

		this.data = data; // <-- Update the data
		fireTableDataChanged(); // <-- fire the event so the table is notified.
								// If you change only one cell you need to call
								// the appropriate fire event
	}
	
	public void removeData() {
		this.data = new Vector<Object>();
		fireTableRowsDeleted(0, 0);
		fireTableDataChanged();
	}
	
	

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		super.setValueAt(aValue, rowIndex, columnIndex);
		fireTableCellUpdated(rowIndex, rowIndex);
		fireTableDataChanged(); 
	}

}
