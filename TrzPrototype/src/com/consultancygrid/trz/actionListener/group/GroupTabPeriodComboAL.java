package com.consultancygrid.trz.actionListener.group;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.ui.table.group.GroupCfgEmplsTableModel;
import com.consultancygrid.trz.util.GroupTablPeriodLoaderUtil;

/**
 * ACtion Listener for code list
 * 
 * @author user
 *
 */

public class GroupTabPeriodComboAL extends BaseActionListener {

	private JComboBox comboBox;
	private JTable table;
	private JTable table2;

	public GroupTabPeriodComboAL(PrototypeMainFrame mainFrame, JComboBox comboBox, JTable table, JTable table2) {

		super(mainFrame);
		this.comboBox = comboBox;
		this.table = table;
		this.table2 = table2;
	}

	@Override
	protected void eventCore() throws IOException {

		Period period = ((Period) comboBox.getModel().getSelectedItem());

		Vector tableData = new Vector();
		Vector tableData2 = new Vector();
		GroupTablPeriodLoaderUtil grTabPeriodLoaderUtil = new GroupTablPeriodLoaderUtil();
		grTabPeriodLoaderUtil.loadData(period, em, tableData);
		grTabPeriodLoaderUtil.loadData2(period, em, tableData2);

		if (comboBox.getModel().getSelectedItem() != null) {

			GroupCfgEmplsTableModel currentModel = (GroupCfgEmplsTableModel) table
					.getModel();
			currentModel.setData(tableData);
			table.setModel(currentModel);

			GroupCfgEmplsTableModel currentModel2 = (GroupCfgEmplsTableModel) table2
					.getModel();
			currentModel2.setData(tableData2);
			table2.setModel(currentModel2);
			mainFrame.validate();

		}
	}

}
