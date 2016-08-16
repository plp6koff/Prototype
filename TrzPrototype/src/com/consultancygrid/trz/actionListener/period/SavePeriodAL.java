package com.consultancygrid.trz.actionListener.period;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;
import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.HibernateUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class SavePeriodAL extends BaseActionListener{

	private JDatePickerImpl datePickerTo;
	private JDatePickerImpl datePickerFrom;
	private JTextField fieldCode;
	private JTextField fieldRevenue;
	private JPanel createFormPanel;
	private JComboBox comboBoxPeriod;	
	private JPanel panLinkPeriod2Empl;
	private HashMap<TrzStatic, JTextField> map ;
	private HashMap<Department, JTextField> mapDept;
	
	public SavePeriodAL(PrototypeMainFrame mainFrame, JDatePickerImpl datePickerFrom, JDatePickerImpl datePickerTo, JTextField fieldCode,
			JTextField fieldRevenue, 	HashMap<TrzStatic, JTextField> map,HashMap<Department, JTextField> mapDept ,JPanel createFormPanel,
			JComboBox comboBoxPeriod, JPanel panLinkPeriod2Empl) {
		super(mainFrame);
		this.datePickerTo = datePickerTo;
		this.datePickerFrom = datePickerFrom;
		this.fieldCode = fieldCode;
		this.fieldRevenue = fieldRevenue;
		this.map = 	map; 
		this.mapDept = mapDept;
		this.createFormPanel = createFormPanel;
		this.comboBoxPeriod  = comboBoxPeriod;
		this.panLinkPeriod2Empl = panLinkPeriod2Empl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		EntityManager em = null;
		EntityTransaction trx = null;
		try {
			
			em = HibernateUtil.getEntityManager();
			trx = em.getTransaction();
			trx.begin();;
			
			Period tempPeriod = new Period();
			tempPeriod.setCode(fieldCode.getText());
			
			String code  = this.fieldCode.getText();
			if (code == null || "".equals(code) || code.length() > 7) {
				JOptionPane.showMessageDialog(mainFrame,  
						ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_ERR_CODE),
						ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR) , JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String revenue  = this.fieldRevenue.getText();
			if (revenue == null || "".equals(revenue)) {
				JOptionPane.showMessageDialog(mainFrame,
						ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_ERR_REVENUE),  
						ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR), JOptionPane.ERROR_MESSAGE);
				return;
			} 
			try {
			tempPeriod.setRevenue((fieldRevenue != null 
					               && !Constants.EMPTY_STRING.equals(fieldRevenue))
					               ? BigDecimal.valueOf(Long.valueOf(fieldRevenue.getText())): BigDecimal.ZERO);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(mainFrame, 
						ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_ERR_REVENUE),
						ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR), JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			Date selectedDateTo = (Date) datePickerFrom.getModel().getValue();
			//Date selectedDateTo = selectedValue != null ? selectedValue.getTime() : new Date();
			Date selectedDateFrom = (Date) datePickerTo.getModel().getValue();
			//Date selectedDateFrom = selectedValue1 != null ? selectedValue1.getTime() : new Date();
			
			tempPeriod.setDateStart(selectedDateFrom);
			tempPeriod.setDateEnd(selectedDateTo);
			em.persist(tempPeriod);
			
			for (Entry<TrzStatic, JTextField> entry : map.entrySet()) {
				
				PeriodSetting ps = new PeriodSetting();
				ps.setPeriod(tempPeriod);
				ps.setTrzStatic(entry.getKey());
				ps.setValue(entry.getValue().getText());
				em.persist(ps);
			}
			
			for (Entry<Department, JTextField> entry : mapDept.entrySet()) {
				
				RevenueDeptPeriod rdp = new RevenueDeptPeriod();
				rdp.setPeriod(tempPeriod);
				rdp.setRevenue(BigDecimal.valueOf(Double.valueOf(entry.getValue().getText())));
				rdp.setDepartment(entry.getKey());
				em.persist(rdp);
			}
			
			this.createFormPanel.setVisible(false);
			this.createFormPanel.repaint();
			this.fieldCode.setText("");
			this.fieldRevenue.setText("0.0");
			datePickerFrom.getModel().setSelected(false);
			datePickerFrom.repaint();
			datePickerTo.getModel().setSelected(false);
			datePickerTo.repaint();
			
			
			PeriodComboBoxModel modelToRefresh = ((PeriodComboBoxModel) comboBoxPeriod.getModel());
			modelToRefresh.addItem(tempPeriod);
			comboBoxPeriod.revalidate();
			comboBoxPeriod.repaint();
			this.panLinkPeriod2Empl.revalidate();
			this.panLinkPeriod2Empl.repaint();
			
			try {
				JOptionPane.showMessageDialog(mainFrame, 
						ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_SUCCESS),
						ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_INFO), JOptionPane.INFORMATION_MESSAGE);
			} catch (HeadlessException | IOException e2) {
				e2.printStackTrace();
			}
			
		} catch (Exception e1) {
			Logger.error(e1);
			try {
				JOptionPane.showMessageDialog(mainFrame, 
						 ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_ERR), 
						 ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR),JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException | IOException e2) {
				e2.printStackTrace();
			}
			if (trx!= null && trx.isActive()) {
				trx.rollback();
			}
		} finally {
			if (trx!= null && trx.isActive()) {
				trx.commit();
			}
		}	
	}
	
}
