/**
 * 
 */
package com.consultancygrid.trz.ui.table.personal.statistic2;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

/**
 * @author user
 *
 */
public class PrsStat2CfgEmplsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5044877015250409328L;

	
	private Vector<String> tableHeaders = new Vector<String>();

	private Vector data = new Vector();

	private List<EmployeeSalary> emplSals;
	
	public PrsStat2CfgEmplsTableModel() throws IOException {

		
		tableHeaders = new Vector<String>();
		// Init headers
		tableHeaders.add("MATCHCODE");
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL0));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL1));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL2));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL3));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL4));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL5));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL6));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL7));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL8));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL9));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL10));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL11));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL12));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL13));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL14));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL15));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL16));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL17));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL18));
		tableHeaders.add(ResourceLoaderUtil
				.getLabels(LabelsConstants.PERSONAL_CFG_HEADER_COL19));
		tableHeaders.add("Avans");
		tableHeaders.add("<html>Po<br/>Banka");
		
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
