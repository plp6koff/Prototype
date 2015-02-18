package com.consultancygrid.trz.actionListener.loadFile;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class LoadCreatePeriodPanelAL extends BaseActionListener {

	private JPanel createPanelMain;
	private JTextField fieldCode;
	JComboBox<Period> comboBoxPeriod;
	
	public LoadCreatePeriodPanelAL(
					  PrototypeMainFrame mainFrame, 
			          JTextField fieldCode,
			          JPanel createPanelMain,
			          JComboBox<Period> comboBoxPeriod) {

		super(mainFrame);
		this.createPanelMain = createPanelMain;
		this.fieldCode = fieldCode;
		this.comboBoxPeriod = comboBoxPeriod;
	}

	
	public void actionPerformed(ActionEvent e) {

		if (false) {
			createPanelMain.remove(3);
		}
		JPanel createPanelInner = new JPanel(null);
		createPanelInner.setBounds(5, 5, 1000, 500);
		
		HashMap<TrzStatic, JTextField> map = new HashMap<TrzStatic, JTextField>();
	
		EntityManagerFactory factory = null;
		EntityManager em = null;
		Period period = new Period();
		period.setCode(fieldCode.getText());
		String code  = this.fieldCode.getText();

		try {
		
				factory = Persistence
						.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
				
				em = factory.createEntityManager();
				em.getTransaction().begin();
				
				if (code == null || "".equals(code) || code.length() > 7) {
					JOptionPane.showMessageDialog(mainFrame,  
							ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_ERR_CODE),
							ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR) , JOptionPane.ERROR_MESSAGE);
					return;
				}
				JSeparator sep = new JSeparator();
				sep.setBounds(40, 85, 700, 5);
				createPanelInner.add(sep);
				int y = 100;
				int delta = 30;
	
				
				Query qPeriod = em.createQuery(" from Period order by code desc");
				List<Period> settingsPeriod = (List<Period>) qPeriod.getResultList();
				
				if (settingsPeriod != null && !settingsPeriod.isEmpty()) {
					
					Period lstPeriod = settingsPeriod.get(0);
					
					for (PeriodSetting settings : lstPeriod.getPeriodSettings()) {
		
						TrzStatic singleStatic = settings.getTrzStatic();
						JLabel lblPeriodSetting = new JLabel(
								singleStatic.getKeyDescription());
						lblPeriodSetting.setBounds(40, y, 350, 25);
						createPanelInner.add(lblPeriodSetting);
						JTextField textFieldValue = new JTextField();
						textFieldValue.setBounds(400, y, 150, 25);
						textFieldValue.setColumns(10);
						textFieldValue.setText(settings.getValue());
						createPanelInner.add(textFieldValue);
						map.put(singleStatic, textFieldValue);
						y = y + delta;
					}
					
				} else {
				
					Query qPeriodTrzStatic = em.createQuery(" from TrzStatic");
					List<TrzStatic> trzResult 
							= (List<TrzStatic>) qPeriodTrzStatic.getResultList();
					for (TrzStatic singleStatic : trzResult) {
		
						JLabel lblPeriodSetting = new JLabel(
								singleStatic.getKeyDescription());
						lblPeriodSetting.setBounds(40, y, 350, 25);
						createPanelInner.add(lblPeriodSetting);
						JTextField textFieldValue = new JTextField();
						textFieldValue.setBounds(400, y, 150, 25);
						textFieldValue.setColumns(10);
						textFieldValue.setText(singleStatic.getValue());
						createPanelInner.add(textFieldValue);
						map.put(singleStatic, textFieldValue);
						y = y + delta;
					}
				}
				JSeparator sep1 = new JSeparator();
				sep1.setBounds(40, y + 20, 700, 5);
				createPanelInner.add(sep1);
				
				y = y + 40;
				
				JButton openButton = new JButton("Open a Files...");
				JButton loadButton = new JButton(ResourceLoaderUtil.getLabels(LabelsConstants.SET_LOAD_FILE_TAB) + "...");
	
				JFileChooser fc = new JFileChooser();
				JTextArea log = new JTextArea(5, 20);
				log.setBounds(40, y, 250, 20);
				log.setEditable(false);
				log.append(ResourceLoaderUtil.getConfig(Constants.DEFAULT_DATA_DIR)
						+ System.getProperties().getProperty("file.separator")
						+ "*.Csv");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				openButton.setBounds(450, y, 200, 20);
				OpenFileAL oFA = new OpenFileAL(mainFrame, fc, log);
	
				openButton.addActionListener(oFA);
				openButton.setEnabled(false);
				File file = oFA.getFile();
	
				LoadFileAL loadAl = new LoadFileAL(mainFrame, fc, log, file, comboBoxPeriod, fieldCode, map, createPanelMain , createPanelInner);
				loadButton.addActionListener(loadAl);
				loadButton.setBounds(680, y, 200, 20);
				
				createPanelInner.add(log);
				createPanelInner.add(openButton);
				createPanelInner.add(loadButton);
		
		} catch (Exception e1) {
			Logger.error(e1);
			try {
				JOptionPane.showMessageDialog(mainFrame,
						ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_LOAD_FILE_ERROR),
						ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR),
						JOptionPane.ERROR_MESSAGE);
			} catch (HeadlessException | IOException e2) {
				e2.printStackTrace();
			}
			if (em != null && em.isOpen()) {
				em.getTransaction().rollback();
				em.close();
			}

		} finally {
			if (em != null && em.isOpen()) {
				em.getTransaction().commit();
				em.close();
			}
		}
		
		comboBoxPeriod.revalidate();
		comboBoxPeriod.repaint();
		createPanelMain.add(createPanelInner);
		createPanelMain.revalidate();
		createPanelMain.repaint();
	}
	

}
