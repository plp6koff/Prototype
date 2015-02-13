/**
 * 
 */
package com.consultancygrid.trz.ui.table.employee;

import java.io.IOException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.util.ResourceLoaderUtil;


/**
 * @author user
 *
 */
public class EmployeeActiveTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5044877015250409328L;

	private Vector<String> tableHeaders = new Vector<String>();

	private Vector data = new Vector();

	public EmployeeActiveTableModel() throws IOException {

		
		tableHeaders = new Vector<String>();
		// Init headers
		tableHeaders.add(ResourceLoaderUtil.getLabels(LabelsConstants.SET_DEACT_HEAD1));
		tableHeaders.add(ResourceLoaderUtil.getLabels(LabelsConstants.SET_DEACT_HEAD1));
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
	
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		super.setValueAt(aValue, rowIndex, columnIndex);
		fireTableRowsUpdated(rowIndex, columnIndex);
		fireTableDataChanged(); 
	}

	public Vector<String> getTableHeaders() {
		return tableHeaders;
	}

	public void addData(Object data) {

		this.data.add(data) ; // <-- Update the data
		fireTableDataChanged(); // <-- fire the event so the table is notified.
								// If you change only one cell you need to call
								// the appropriate fire event
	}
	
	public void setData(Vector data) {

		this.data = data; // <-- Update the data
		fireTableDataChanged(); // <-- fire the event so the table is notified.
								// If you change only one cell you need to call
								// the appropriate fire event
	}
}
