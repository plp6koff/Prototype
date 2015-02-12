package com.consultancygrid.trz.ui.combo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import com.consultancygrid.trz.model.TargetPeriod;

public class TargetComboBoxModel implements ComboBoxModel<TargetPeriod> {

	TargetPeriod selectedTargetPeriod;
	List<TargetPeriod> comboBoxItemList;
	
	public TargetComboBoxModel(List<TargetPeriod> comboBoxItemList) {
		
		this.comboBoxItemList = comboBoxItemList;
	}
	
	public TargetComboBoxModel() {
		comboBoxItemList = new ArrayList<TargetPeriod>();
	}
	
	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public TargetPeriod getElementAt(int arg0) {
		
		return comboBoxItemList.get(arg0);
	}

	@Override
	public int getSize() {
		
		return comboBoxItemList.size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}
	
	public void addAll(List<TargetPeriod> trgPeriod) {
		comboBoxItemList.clear();
		comboBoxItemList.addAll(trgPeriod);
	}	

	@Override
	public TargetPeriod getSelectedItem() {
		
		return selectedTargetPeriod;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		if (arg0 instanceof TargetPeriod) {
			this.selectedTargetPeriod = (TargetPeriod) arg0;
		}
      
	}
	
	public void addItem(TargetPeriod trg) {
		
		comboBoxItemList.add(trg);
	}
	
	public void reinit(List<TargetPeriod> comboBoxItemList) {
		
		comboBoxItemList.clear();
		comboBoxItemList.addAll(comboBoxItemList);
	}

	/**
	 * @return the comboBoxItemList
	 */
	public List<TargetPeriod> getComboBoxItemList() {
		return comboBoxItemList;
	}

	/**
	 * @param comboBoxItemList the comboBoxItemList to set
	 */
	public void setComboBoxItemList(List<TargetPeriod> comboBoxItemList) {
		this.comboBoxItemList = comboBoxItemList;
	}
	
	
	
}
