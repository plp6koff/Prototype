package com.consultancygrid.trz.actionListener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.JFrame;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.util.HibernateUtil;



public  class GBaseActionListener implements ActionListener {

	protected JFrame mainFrame;
	protected EntityTransaction transaction;
	protected EntityManager em;
	public GBaseActionListener(JFrame mainFrame) {
		
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			init();
			eventCore();
			
		} catch (Exception e1) {
			Logger.error(e1);
			rollBack();
		} finally {
			commit();
		}	
	}
	
	protected void eventCore() throws IOException {
		
	};
	
	protected void rollBack() {
		if (transaction!= null && transaction.isActive()) {
			transaction.rollback();
		}
	}
	
	protected void commit() {
		if (transaction!= null && transaction.isActive()) {
			transaction.commit();
		}
	}
	
	protected void init() {
		em = HibernateUtil.getEntityManager();
		transaction = em.getTransaction();
		transaction.begin();
	}
}
