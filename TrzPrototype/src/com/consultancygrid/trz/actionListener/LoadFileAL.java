package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.InputData;
import com.consultancygrid.trz.model.InputFileType;
import com.consultancygrid.trz.model.MatchcodeList;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.ui.frame.FileChooserDemo;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.CSVReaderUtil;
import com.consultancygrid.trz.util.ExcelReaderUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class LoadFileAL extends BaseActionListener {

	
	static private final String newline = "\n";
	private JFileChooser fc;
	private JTextArea log;
	private File file;
	
	

	public LoadFileAL(PrototypeMainFrame mainFrame, JFileChooser fc,
			JTextArea log, File file) {

		super(mainFrame);
		this.fc = fc;
		this.log = log;
		this.file = file;
	}

	public void actionPerformed(ActionEvent e) {

		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
		
				CSVReaderUtil util = new CSVReaderUtil();
				if (file != null) {
		
					util.readCSVcarloFibu(file);
					// This is where a real application would save the file.
					log.append("Loading: " + file.getName() + "." + newline);
					log.setCaretPosition(log.getDocument().getLength());
				} else {
		
					final File folder = new File("D:/data");
					Period period  = null;
					for (final File fileEntry : folder.listFiles()) {
					
						System.out.println(fileEntry.getName());
						InputData result = new InputData();
						
						final String f1 = "order";
						final String f2 = "turhauptdisponent";
						final String f3 = "sales";
						final String f4 = "tur last change";
						String name = fileEntry.getName();
						
						String periodCode = "";
						
						Query q = em.createQuery("from InputFileType  where prefix = :prefix");
						if (name.startsWith(f1)) {
							
							q.setParameter("prefix", f1);
							periodCode = name.substring(f1.length() + 1, name.indexOf(".Csv"));
						} else  if (name.startsWith(f2)) {
							q.setParameter("prefix", f2);
							periodCode = name.substring(f2.length() + 1, name.indexOf(".Csv"));
						} else if (name.startsWith(f3)) {
							q.setParameter("prefix", f3);
							periodCode = name.substring(f3.length() + 1, name.indexOf(".Csv"));
						} else if (name.startsWith(f4)) {
							q.setParameter("prefix", f4);
							periodCode = name.substring(f4.length() + 1, name.indexOf(".Csv"));
						} else {
							System.err.println("Error Format!");
							//TODO trow exception
						}
						
						if (period == null) {
							
							period = new Period();
							period.setCode(periodCode.trim());
							em.persist(period);
							
							Query qPeriodTrzStatic = em.createQuery(" from TrzStatic");
							List<TrzStatic> trzResult =	(List<TrzStatic>) qPeriodTrzStatic.getResultList();
							
							HashMap<TrzStatic, JTextField> map = new HashMap<TrzStatic, JTextField> ();
							for (TrzStatic singleStatic : trzResult) {
								
								PeriodSetting ps = new PeriodSetting();
								ps.setPeriod(period);
								ps.setTrzStatic(singleStatic);
								ps.setValue(singleStatic.getValue());
								em.persist(ps);
							}
						}
						List<InputFileType> selectedFile = (List<InputFileType>) q.getResultList();
						processSingleFile(fileEntry, em, selectedFile.get(0), period);
						
					}
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
	
	
	private void processSingleFile(File file, EntityManager em, InputFileType ift , Period period) {
		
		CSVReaderUtil util = new CSVReaderUtil();
		util.readCSVcarloFibu(file);
		Map<String, String> data = util.getMatchCodeRev();
		for (Map.Entry<String, String> singleData : data.entrySet()) {
			
			InputData inputData = new InputData();
			inputData.setInputFileType(ift);
			inputData.setRevenue(BigDecimal.valueOf(Double.valueOf(singleData.getValue().replace (",", "."))));
			inputData.setMatchcode(singleData.getKey());
			inputData.setPeriod(period);
			em.persist(inputData);
		}
		
		Map<String, String> byNames = util.getMatchNameBased();
		for (Map.Entry<String, String> singleNameBasedData : byNames.entrySet()) {
			
			String rawName = singleNameBasedData.getKey();
			Query emplQ = em.createQuery("select mcl from MatchcodeList as mcl where mcl.id.firstName = :firstName and mcl.id.lastName = :lastName");
			emplQ.setParameter("firstName", rawName.substring(0, rawName.indexOf(" ")));
			emplQ.setParameter("lastName", rawName.substring(rawName.indexOf(" ")+1));
			List<MatchcodeList> result =	(List<MatchcodeList>) emplQ.getResultList();
			
			InputData inputData = new InputData();
			inputData.setInputFileType(ift);
			inputData.setRevenue(BigDecimal.valueOf(Double.valueOf(singleNameBasedData.getValue().replace (",", "."))));
			inputData.setMatchcode(result.get(0).getId().getMatchcode());
			inputData.setPeriod(period);
			em.persist(inputData);
		}
	}
}
