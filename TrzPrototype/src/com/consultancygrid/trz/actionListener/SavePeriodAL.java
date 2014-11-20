package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;
import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;

public class SavePeriodAL extends BaseActionListener{

	private JDatePickerImpl datePickerTo;
	private JDatePickerImpl datePickerFrom;
	private JTextField fieldCode;
	private JTextField fieldRevenue;
	
	private HashMap<TrzStatic, JTextField> map ;
	
	public SavePeriodAL(PrototypeMainFrame mainFrame, JDatePickerImpl datePickerFrom, JDatePickerImpl datePickerTo, JTextField fieldCode, JTextField fieldRevenue, 	HashMap<TrzStatic, JTextField> map ) {
		super(mainFrame);
		this.datePickerTo = datePickerTo;
		this.datePickerFrom = datePickerFrom;
		this.fieldCode = fieldCode;
		this.fieldRevenue = fieldRevenue;
		this.map = 	map; 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			Period tempPeriod = new Period();
			tempPeriod.setCode(fieldCode.getText());
			
			tempPeriod.setRevenue((fieldRevenue != null 
					               && !Constants.EMPTY_STRING.equals(fieldRevenue))
					               ? BigDecimal.valueOf(Long.valueOf(fieldRevenue.getText())): BigDecimal.ZERO);
			
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
			
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
			}
		}	
	}
	
}
