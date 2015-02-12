package com.consultancygrid.trz.actionListener;

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
import javax.persistence.Persistence;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;
import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.TargetPeriod;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class SaveTargetAL extends BaseActionListener{

	private JDatePickerImpl datePickerEnd;
	private JDatePickerImpl datePickerStart;
	private JTextField fieldCode;
	private JTextField fieldLenght;
	private JPanel createFormPanel;
	private JPanel panLinkPeriod2Empl;
	
	public SaveTargetAL(PrototypeMainFrame mainFrame, JTextField tf1, JTextField tf3, JDatePickerImpl datePickerTo, JDatePickerImpl datePickerFrom) {
		
		super(mainFrame);
		this.datePickerEnd = datePickerTo;
		this.datePickerStart = datePickerFrom;
		this.fieldCode = tf1;
		this.fieldLenght = tf3;
	} 

	@Override
	public void actionPerformed(ActionEvent e) {
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			String code  = this.fieldCode.getText();
			if (code == null || "".equals(code) || code.length() > 7) {
				JOptionPane.showMessageDialog(mainFrame, "Please fill the code !",
						ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR) , JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			TargetPeriod  trg = new TargetPeriod();
			trg.setCode(code);
			
			trg.setDateStart((Date)datePickerStart.getModel().getValue());
			trg.setDateEnd((Date)datePickerEnd.getModel().getValue());
			trg.setPeriodLength(BigDecimal.valueOf(Double.valueOf(fieldLenght.getText())));
			
			
			
			em.persist(trg);
			
			
			this.fieldCode.setText("");
			this.fieldLenght.setText("0.0");
			datePickerStart.getModel().setSelected(false);
			datePickerStart.repaint();
			datePickerEnd.getModel().setSelected(false);
			datePickerEnd.repaint();
			
			
//			PeriodComboBoxModel modelToRefresh = ((PeriodComboBoxModel) comboBoxPeriod.getModel());
//			modelToRefresh.addItem(tempPeriod);
			//comboBoxPeriod.revalidate();
			//comboBoxPeriod.repaint();
			//this.panLinkPeriod2Empl.revalidate();
			//this.panLinkPeriod2Empl.repaint();
			
			try {
				JOptionPane.showMessageDialog(mainFrame, 
						"Target created",
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
			if (em!= null && em.isOpen()) {
				em.getTransaction().rollback();
				em.close();
			}

		} finally {
			if (em!= null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
			}
		}	
	}
	
}
