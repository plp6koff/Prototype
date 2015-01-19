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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.AvgInputData;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.InputData;
import com.consultancygrid.trz.model.InputFileType;
import com.consultancygrid.trz.model.MatchcodeList;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.model.UserDepartment;
import com.consultancygrid.trz.ui.combo.EmplComboBoxModel;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.CSVReaderUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class LoadFileAL extends BaseActionListener {

	
	static private final String newline = "\n";
    private static final String f1 = "order";
    private static final String f2 = "turhauptdisponent";
    private static final String f3 = "sales";
    private static final String f4 = "tur last change";
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
		Period period  = null;
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
		
					final File folder = new File(ResourceLoaderUtil.getConfig(Constants.DEFAULT_DATA_DIR));
					for (final File fileEntry : folder.listFiles()) {
					
						System.out.println(fileEntry.getName());
						InputData result = new InputData();
					
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
		
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			empl2Period2Depart(em, period);
			
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
	
	
	public void empl2Period2Depart(EntityManager em , Period period) {
		
		
		
		Query avgQ = em.createQuery(" from AvgInputData  where  id.periodCode = :pCode");
		avgQ.setParameter("pCode", period.getCode());
		List<AvgInputData> avgs =	(List<AvgInputData>) avgQ.getResultList();
		
		for (AvgInputData singleAvg : avgs) {
			
			String mCode = singleAvg.getId().getMatchcode();
			Query machDept = em.createQuery("from UserDepartment  where id.matchcode = :mCode ");
			machDept.setParameter("mCode", mCode);
			
			List<UserDepartment> uDepts = (List<UserDepartment>) machDept.getResultList();
			if (!uDepts.isEmpty()) {
				UserDepartment uDept = uDepts.get(0);
				
				//Period
				//Match code
				Query emplQ = em.createQuery("from Employee where matchcode = :mCode");
				emplQ.setParameter("mCode", mCode);
				Employee employee = ((List<Employee>) emplQ.getResultList()).get(0);
				// Department
				Query depQ = em.createQuery("from Department where code = :dCode");
				depQ.setParameter("dCode", uDept.getId().getDepId());
				Department department = ((List<Department>) depQ.getResultList()).get(0);
				
				createEmplDeptPeriod(em, period, employee, department, singleAvg.getId().getRevenue());
				createSettings(em, period, employee);
				createSalary(em, period, employee);
			}
			
		}
	}
	
	
	
	private BigDecimal parseValue(BigDecimal initValue, String newValStr) {
		
		BigDecimal result = initValue;
		try {
			double parsedDoub = Double.valueOf(newValStr);
			return BigDecimal.valueOf(parsedDoub);
		} catch (Exception e)  {
			 return initValue;
		}
	}
	
	private void createEmplDeptPeriod(EntityManager em, Period period,Employee empl, Department dept, BigDecimal revenueEmpl4Period) {
		
		EmplDeptPeriod eDP = new EmplDeptPeriod();
		eDP.setDepartment(dept);
		eDP.setEmployee(empl);
		eDP.setPeriod(period);
		
		RevenueEmplPeriod rEP = new RevenueEmplPeriod();
		rEP.setEmployee(empl);
		rEP.setPeriod(period);
		rEP.setRevenue(revenueEmpl4Period);
		em.persist(rEP);
	}
	
	private void createSettings(EntityManager em, Period period,Employee empl) {
		
		EmployeeSettings settings =   new EmployeeSettings();
		settings.setPeriod(period);
		settings.setEmployee(empl);
		
		Query q = em.createQuery(" from EmployeeSettings as settings  where  settings.employee.id = :employeeId and settings.period.id =:periodId order by settings.period.dateEnd desc");
		q.setParameter("employeeId", empl.getId());
		q.setParameter("periodId", period.getId());
		List<EmployeeSettings> emplSettingsList = (List<EmployeeSettings>) q.getResultList();
		EmployeeSettings initSettings = null;
		if (emplSettingsList != null && !emplSettingsList.isEmpty()) {
			initSettings = emplSettingsList.get(0);
		}
		
		if (initSettings != null) {
			settings.setBrutoPoShtat(initSettings.getBrutoPoShtat());
			settings.setBrutoStandart(initSettings.getBrutoStandart());
			settings.setAvans(initSettings.getAvans());
			settings.setPercentAll(initSettings.getPercentAll());
			settings.setPercentGroup(initSettings.getPercentGroup());
			settings.setPercentPersonal(initSettings.getPercentPersonal());
			settings.setPersonAllOnboardingPercent(initSettings.getPersonAllOnboardingPercent() != null ? initSettings.getPersonAllOnboardingPercent() : BigDecimal.ONE);
			settings.setPersonGroupOnboardingPercent(initSettings.getPersonGroupOnboardingPercent() != null ? initSettings.getPersonGroupOnboardingPercent() : BigDecimal.ONE);
		} else {
			settings.setBrutoPoShtat(BigDecimal.ZERO);
			settings.setBrutoStandart(BigDecimal.ZERO);
			settings.setAvans(BigDecimal.ZERO);
			settings.setPercentAll(initSettings.getPercentAll());
			settings.setPercentGroup(BigDecimal.ZERO);
			settings.setPercentPersonal(BigDecimal.ZERO);
			settings.setPersonAllOnboardingPercent(BigDecimal.ONE);
			settings.setPersonGroupOnboardingPercent(BigDecimal.ONE);
		}
		
		BigDecimal tempOnBoard_all = settings.getPersonAllOnboardingPercent();
		if (BigDecimal.ONE.compareTo(tempOnBoard_all) == 1) {
			BigDecimal tempToAdd = parseValue(BigDecimal.ZERO,"0.15");
			settings.setPersonAllOnboardingPercent(tempOnBoard_all.add(tempToAdd));
		} else {
			settings.setPersonAllOnboardingPercent(tempOnBoard_all);
		}
		BigDecimal tempOnBoard_gr = settings.getPersonGroupOnboardingPercent();
		if (BigDecimal.ONE.compareTo(tempOnBoard_gr) == 1) {
			BigDecimal tempToAdd = parseValue(BigDecimal.ZERO,"0.15");
			settings.setPersonGroupOnboardingPercent(tempOnBoard_gr.add(tempToAdd));
		} else {
			settings.setPersonGroupOnboardingPercent(tempOnBoard_gr);
		}
		em.persist(settings);
	}
	
	private void createSalary(EntityManager em, Period period, Employee empl) {
		
		Query q = em.createQuery(" from EmployeeSalary as emplSalary  where  emplSalary.employee.id = :employeeId order by emplSalary.period.dateEnd desc");
		q.setParameter("employeeId", empl.getId());
		List<EmployeeSalary> emplSals = (List<EmployeeSalary>) q.getResultList();
		
		EmployeeSalary salary = new EmployeeSalary();
		if (emplSals != null && !emplSals.isEmpty()) {
			
			EmployeeSalary lastSalary = emplSals.get(0);
			salary.setV01(lastSalary.getV01());
			salary.setV02(lastSalary.getV02());
			salary.setV03(lastSalary.getV03());
			salary.setV04(lastSalary.getV04());
			salary.setV05(lastSalary.getV05());
			salary.setV06(lastSalary.getV06());
			salary.setV07(lastSalary.getV07());
			salary.setV08(lastSalary.getV08());
			salary.setV09(lastSalary.getV09());
			salary.setV10(lastSalary.getV10());
			salary.setV11(lastSalary.getV11());
			salary.setV12(lastSalary.getV12());
			salary.setV13(lastSalary.getV13());
			salary.setV14(lastSalary.getV14());
			salary.setV15(lastSalary.getV15());
			salary.setV16(lastSalary.getV16());
			salary.setV17(lastSalary.getV17());
		    salary.setV18(lastSalary.getV18());
		    salary.setV19(lastSalary.getV19());
		    salary.setV20(lastSalary.getV20());
		    salary.setV21(lastSalary.getV21());
		    salary.setV22(lastSalary.getV22());
		    salary.setV23(lastSalary.getV23());
		} 
		salary.setEmployee(empl);
		salary.setPeriod(period);
		em.persist(salary);
	}
}
