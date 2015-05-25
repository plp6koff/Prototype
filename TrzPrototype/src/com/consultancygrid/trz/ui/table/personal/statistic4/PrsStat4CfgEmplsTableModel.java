/**
 * 
 */
package com.consultancygrid.trz.ui.table.personal.statistic4;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.consultancygrid.trz.model.EmployeeSalary;

/**
 * @author user
 *
 */
public class PrsStat4CfgEmplsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5044877015250409328L;

	
	private Vector<String> tableHeaders = new Vector<String>();

	private Vector data = new Vector();

	private List<EmployeeSalary> emplSals;
	
	public PrsStat4CfgEmplsTableModel() throws IOException {

		
		tableHeaders = new Vector<String>();
		// Init headers
		tableHeaders.add("PERIOD_CODE");
		tableHeaders.add("MATCHCODE");
		tableHeaders.add("SP");
		tableHeaders.add("INDIVIDUALNA PREMIA");
		tableHeaders.add("GRUPOVA PREMIA");
		tableHeaders.add("OBSHTA PREMIA");
		tableHeaders.add("OBSHTO PREMII");
		tableHeaders.add("OTNOSHENIE OBSHTO PREMII SP");
		tableHeaders.add("TBD");
	}
	
	@Override
	public String getColumnName(int column) {
	
//		switch(column) {
//		
//		 case 1 : return "<html>Test<br>Test1<br>Test2";
//			
//		}
//		StringBuffer sb = new StringBuffer("<html>");
		return  tableHeaders.get(column);
//		raw = raw.replace(" ", "<br>");
//		sb.append(b)

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

	public void setData(Vector data) {

		this.data = data; // <-- Update the data
		fireTableDataChanged(); // <-- fire the event so the table is notified.
								// If you change only one cell you need to call
								// the appropriate fire event
	}
	
	public List<EmployeeSalary> getEmplSals() {
		return emplSals;
	}

	public void setEmplSals(List<EmployeeSalary> emplSals) {
		this.emplSals = emplSals;
	}
	
	public void clearData() {
		this.data = new Vector<Object>();
	}

	public Vector getData() {
		return data;
	}
	
	
}
