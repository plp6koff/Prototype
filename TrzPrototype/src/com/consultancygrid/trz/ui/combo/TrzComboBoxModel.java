package com.consultancygrid.trz.ui.combo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import com.consultancygrid.trz.model.Period;

public class TrzComboBoxModel implements ComboBoxModel<Period> {

	Period selectedPeriod;
	List<Period> comboBoxItemList;
	
	public TrzComboBoxModel(List<Period> comboBoxItemList) {
		
		this.comboBoxItemList = comboBoxItemList;
	}
	
	public TrzComboBoxModel() {
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
	
}
