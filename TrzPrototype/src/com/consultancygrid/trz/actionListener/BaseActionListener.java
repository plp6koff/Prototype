package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;



public  class BaseActionListener implements ActionListener {

	protected EntityManagerFactory factory = null;
	protected EntityManager em = null;
	protected PrototypeMainFrame mainFrame;
	
	
	public BaseActionListener(PrototypeMainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			em = factory.createEntityManager();
			em.getTransaction().begin();
			eventCore();
			
			
		} catch (Exception e1) {
			Logger.error(e1);

		} finally {
			if (em!= null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
				factory.close();
			}
		}	
	}
	
	protected void eventCore() {
		
	};
}
