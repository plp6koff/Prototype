package com.consultancygrid.trz.ui.combo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import com.consultancygrid.trz.model.Period;

public class PeriodComboBoxModel implements ComboBoxModel<Period> {

	Period selectedPeriod;
	List<Period> comboBoxItemList;
	
	public PeriodComboBoxModel(List<Period> comboBoxItemList) {
		
		this.comboBoxItemList = comboBoxItemList;
	}
	
	public PeriodComboBoxModel() {
		comboBoxItemList = new ArrayList<Period>();
	}
	
	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Period getElementAt(int arg0) {
		
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
	

	@Override
	public Period getSelectedItem() {
		
		return selectedPeriod;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		if (arg0 instanceof Period) {
			this.selectedPeriod = (Period) arg0;
		}
      
	}
	
	public void addItem(Period perdiod) {
		
		comboBoxItemList.add(perdiod);
	}
	
	public void reinit(List<Period> comboBoxItemList) {
		
		comboBoxItemList.clear();
		comboBoxItemList.addAll(comboBoxItemList);
	}

	/**
	 * @return the comboBoxItemList
	 */
	public List<Period> getComboBoxItemList() {
		return comboBoxItemList;
	}

	/**
	 * @param comboBoxItemList the comboBoxItemList to set
	 */
	public void setComboBoxItemList(List<Period> comboBoxItemList) {
		this.comboBoxItemList = comboBoxItemList;
	}
	
	
	
}
