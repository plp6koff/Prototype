package com.consultancygrid.trz.actionListener.loadFile;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.consultancygrid.trz.actionListener.BaseActionListener;
import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.data.StaticDataLoader;
import com.consultancygrid.trz.data.TrzStaticData;
import com.consultancygrid.trz.model.AvgInputData;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.DepartmentRevenue;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.InputData;
import com.consultancygrid.trz.model.InputFileType;
import com.consultancygrid.trz.model.MatchcodeList;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.RevenueDeptPeriod;
import com.consultancygrid.trz.model.RevenueEmplPeriod;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.model.UserDepartment;
import com.consultancygrid.trz.ui.combo.PeriodComboBoxModel;
import com.consultancygrid.trz.ui.frame.PrototypeMainFrame;
import com.consultancygrid.trz.util.CSVReaderUtil;
import com.consultancygrid.trz.util.EmployeeSalaryUtil;
import com.consultancygrid.trz.util.EmployeeSettingsUtil;
import com.consultancygrid.trz.util.ResourceLoaderUtil;

public class LoadFileAL extends BaseActionListener {

	Logger LOG  = Logger.getLogger(LoadFileAL.class);
	
	static private final String newline = "\n";
	private static final String f1 = "order";
	private static final String f2 = "turhauptdisponent";
	private static final String f3 = "sales";
	private static final String f4 = "tur last change";
	private JFileChooser fc;
	private JTextArea log;
	private File file;
	private JComboBox<Period> comboBoxPeriod;
	private JPanel createPanelMain;
	private JPanel createPanelInner;

	private JTextField fieldCode;
	private JPanel createFormPanel;
	private HashMap<TrzStatic, JTextField> map;

	public LoadFileAL(PrototypeMainFrame mainFrame, 
			JFileChooser fc,
			JTextArea log, File file, JComboBox<Period> comboBoxPeriod,
			JTextField fieldCode, HashMap<TrzStatic, JTextField> map,
			JPanel createPanelMain, JPanel createPanelInner) {

		super(mainFrame);
		this.fc = fc;
		this.log = log;
		this.file = file;
		this.createPanelMain = createPanelMain;
		this.createPanelInner = createPanelInner;
		this.comboBoxPeriod = comboBoxPeriod;
		this.fieldCode = fieldCode;
		this.map = map;

	}

	public void actionPerformed(ActionEvent e) {

		createPanelMain.revalidate();
		createPanelMain.repaint();
		PeriodComboBoxModel periodComboModel = null;
		Period period = new Period();
		period.setCode(fieldCode.getText());
		String code = this.fieldCode.getText();
		Set<String> matchCodes = new HashSet<String>();
		List<Period> allPeriods = null;
		PeriodComboBoxModel newModel =  new PeriodComboBoxModel();
		try {

			init();
			if (code == null || "".equals(code) || code.length() > 7) {
				JOptionPane
						.showMessageDialog(
								mainFrame,
								ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_CRT_PERIOD_ERR_CODE),
								ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR),
								JOptionPane.ERROR_MESSAGE);
				return;
			}

			em.persist(period);
			em.flush();

			TrzStaticData data  = 
					StaticDataLoader.persistPersonSettings(period, map, em);
			CSVReaderUtil util = new CSVReaderUtil();
				
			if (file != null) {
				
					util.readCSVcarloFibu(file);
					log.append("Loading: " + file.getName() + "." + newline);
					LOG.info("Loading: " + file.getName() + "." + newline);
					log.setCaretPosition(log.getDocument().getLength());
			} else {
					final File folder = new File(
							ResourceLoaderUtil
									.getConfig(Constants.DEFAULT_DATA_DIR));
					if (folder == null || !folder.exists()) {
						try {
							JOptionPane
									.showMessageDialog(
											mainFrame,
											"Folder : " + ResourceLoaderUtil.getConfig(Constants.DEFAULT_DATA_DIR)
											+ " does not exists !!!",
											ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_ERR),
											JOptionPane.ERROR_MESSAGE);
							return;
						} catch (HeadlessException | IOException e2) {
							LOG.error(e2);
					}
			}
					for (final File fileEntry : folder.listFiles()) {
	
						LOG.info(fileEntry.getName());
						String name = fileEntry.getName();
						Query q = em.createQuery("from InputFileType  where prefix = :prefix");
						if (name.startsWith(f1)) {
							q.setParameter("prefix", f1);
						} else if (name.startsWith(f2)) {
							q.setParameter("prefix", f2);
						} else if (name.startsWith(f3)) {
							q.setParameter("prefix", f3);
						} else if (name.startsWith(f4)) {
							q.setParameter("prefix", f4);
						} else {
							LOG.info("File : " +  name + " will be skipped!");
							continue;
						}
						List<InputFileType> selectedFile = (List<InputFileType>) q.getResultList();
						Set<String> matchCodesTmp 
							= processSingleFileMatchToInput(fileEntry, em, selectedFile.get(0), period);
						matchCodes.addAll(matchCodesTmp);
					
					}
				}
				createPanelMain.remove(createPanelInner);
				periodComboModel = (PeriodComboBoxModel) comboBoxPeriod.getModel();
				empl2Period2Depart(em, period, matchCodes, data);
				
				Query qPeriods = em.createQuery(" from Period");
				allPeriods = (List<Period>) qPeriods.getResultList();
				newModel = new PeriodComboBoxModel();
				newModel.addAll(allPeriods);
				em.flush();
				
			} catch (Exception e1) {
				LOG.error(e1);
				try {
					JOptionPane.showMessageDialog(mainFrame, ResourceLoaderUtil
							.getLabels(LabelsConstants.SET_TAB_LOAD_FILE_ERROR),
							ResourceLoaderUtil
									.getLabels(LabelsConstants.ALERT_MSG_ERR),
							JOptionPane.ERROR_MESSAGE);
				} catch (HeadlessException | IOException e2) {
					LOG.error(e2);
					e2.printStackTrace();
				}
				rollBack();
			} finally {
				commit();
			}
		
		
		comboBoxPeriod.setModel(newModel);
		comboBoxPeriod.revalidate();
		comboBoxPeriod.repaint();
		
		periodComboModel.addItem(period);
		comboBoxPeriod.revalidate();
		comboBoxPeriod.repaint();
		this.fieldCode.setText("");
		createPanelMain.revalidate();
		createPanelMain.repaint();

		try {
			JOptionPane.showMessageDialog(mainFrame, ResourceLoaderUtil
					.getLabels(LabelsConstants.SET_TAB_LOAD_FILE_SUCCESS),
					ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_OK),
					JOptionPane.INFORMATION_MESSAGE);
		} catch (HeadlessException | IOException e1) {
			e1.printStackTrace();
			LOG.error(e1);
		}
	}

	/**
	 * Method that should link Employee to Department
	 * 
	 * @param em
	 * @param period
	 * @param matchCodes
	 * @param vauchers
	 */
	public void empl2Period2Depart(EntityManager em,
								   Period period, 
								   Set<String> matchCodes, 
								   TrzStaticData data ) {

		Map<String, Department> codeDeptPair = createDepEmplPeriod(period);
		BigDecimal bPeriodRevenue = person2Department(matchCodes, codeDeptPair,	period, data);
		allPersonsNoDepartments(matchCodes, period, data);
		period.setRevenue(bPeriodRevenue);
		em.merge(period);
	}

	/**
	 * Core Functionality To process all Department related Persons
	 * 
	 * @param matchCodes
	 * @param codeDeptPair
	 * @param period
	 * @param vauchers
	 * @return
	 */
	private BigDecimal person2Department(Set<String> matchCodes,
										Map<String, Department> codeDeptPair,
										Period period, 
										TrzStaticData data) {

		
		Query avgQ = em
				.createNativeQuery("select   DISTINCT(MATCHCODE), REVENUE from AVG_INPUT_DATA where PERIOD_CODE = :pCode ");
		avgQ.setParameter("pCode", period.getCode());
		List<Object[]> avgs = (List<Object[]>) avgQ.getResultList();
		BigDecimal bDecimal = BigDecimal.ZERO;

		Map<String, BigDecimal> dataMap = new HashMap<String, BigDecimal>();
		for (Object[] singleAvg : avgs) {
			
			final String mCode = (String)singleAvg[0];
			final BigDecimal revenue = (BigDecimal)singleAvg[1];
			if (dataMap.containsKey(mCode)) {
				BigDecimal newRevenue = dataMap.get(mCode).add(revenue);
				dataMap.put(mCode, newRevenue);
			} else{
				dataMap.put(mCode, revenue);
			}
		}
		
		for (Map.Entry<String, BigDecimal> singlePair : dataMap.entrySet()) {

			final String mCode = singlePair.getKey();
			final BigDecimal revenue = singlePair.getValue();
			
			bDecimal = bDecimal.add(revenue);
			Query machDept = em
					.createQuery("from UserDepartment  where id.matchcode = :mCode");
			machDept.setParameter("mCode", mCode);

			List<UserDepartment> uDepts = (List<UserDepartment>) machDept
					.getResultList();
			if (!uDepts.isEmpty()) {

				UserDepartment uDept = uDepts.get(0);
				// Period Match code
				LOG.debug("MATCHCODE : " + mCode);
				Query emplQ = em.createQuery("from Employee where matchcode = :mCode and isActive = 'Y'");
				emplQ.setParameter("mCode", mCode);
				List<Employee> listEmployee = (List<Employee>) emplQ.getResultList();
				
				
				if (listEmployee != null && !listEmployee.isEmpty()) {

					Employee employee = listEmployee.get(0);
					matchCodes.remove(employee.getMatchCode());
					// Department
					Department department = codeDeptPair.get(uDept.getId().getDepId());
					// Setting
					LOG.debug("Start Settings create with Period : " + period.getCode());
					LOG.debug("Start Settings create with Employee : " + employee.getMatchCode());
					EmployeeSettingsUtil.createSettings(em, period, employee);
					// Salary
					LOG.debug("Start Salary");
					EmployeeSalaryUtil.createSalary(em, period, employee, data);
					// Department to Period
					LOG.debug("Start Dept Period");
					createEmplDeptPeriod(em, period, employee, department);
					
					// Period Employee Revenue
					createRevenueEmplPeriod(em, period, employee,  revenue);
					
				}
			}
		}
		return bDecimal;
	}

	/**
	 * Process all persons with no departments
	 * @param matchCodes
	 * @param period
	 * @param vauchers
	 */
	private void allPersonsNoDepartments(Set<String> matchCodes, Period period, TrzStaticData data) {
		
		if (matchCodes != null && !matchCodes.isEmpty()) {
		Query avgQ = em.createQuery("from AvgInputData  where  id.periodCode = :pCode and matchCode in (:restMatchCodes)");
		avgQ.setParameter("pCode", period.getCode());
		avgQ.setParameter("restMatchCodes", matchCodes);
		List<AvgInputData> avgsNoDept = (List<AvgInputData>) avgQ.getResultList();
		
		
		
		
		Map<String, BigDecimal> dataMap = new HashMap<String, BigDecimal>();
			for (AvgInputData singleAvg : avgsNoDept) {
				
				
				final String mCode = singleAvg.getId().getMatchcode();
				final BigDecimal revenue = singleAvg.getId().getRevenue();
				
				if (dataMap.containsKey(mCode)) {
					BigDecimal newRevenue = dataMap.get(mCode).add(revenue);
					dataMap.put(mCode, newRevenue);
				} else{
					dataMap.put(mCode, revenue);
				}
			}
			
			
			for (Map.Entry<String, BigDecimal> singlePair : dataMap.entrySet()) {	
			
				final String mCode = singlePair.getKey();
				final BigDecimal revenue = singlePair.getValue();
				
				Query emplQ = em.createQuery("from Employee where matchcode = :mCode and isActive = 'Y'");
				emplQ.setParameter("mCode", mCode);
				List<Employee> empls = (List<Employee>) emplQ.getResultList();
				
				if (empls != null && !empls.isEmpty()) {
					Employee employee = empls.get(0);
					EmployeeSettingsUtil.createSettings(em, period, employee);
					EmployeeSalaryUtil.createSalary(em, period, employee, data);
					createRevenueEmplPeriod(em, period, employee, revenue);
				}
		}
		}
	}


	/**
	 * Pupulate
	 * 
	 * @param file
	 * @param em
	 * @param ift
	 * @param period
	 * @return
	 */
	private Set<String> processSingleFileMatchToInput(File file,
			EntityManager em, InputFileType ift, Period period) {

		Set<String> matchCodes = new HashSet<String>();

		CSVReaderUtil util = new CSVReaderUtil();
		util.readCSVcarloFibu(file);
		Map<String, String> data = util.getMatchCodeRev();

		for (Map.Entry<String, String> singleData : data.entrySet()) {
			matchCodes.add(singleData.getKey());
			InputData inputData = new InputData();
			inputData.setInputFileType(ift);
			inputData.setRevenue(BigDecimal.valueOf(Double.valueOf(singleData.getValue().replace(",", "."))));
			inputData.setMatchcode(singleData.getKey());
			inputData.setPeriod(period);
			em.persist(inputData);
		}
		em.flush();
		Query emplQ = em.createQuery(" from MatchcodeList");
		List<MatchcodeList> resultMatchcodeList = (List<MatchcodeList>) emplQ.getResultList();
		Map<String, String> translationMap = new HashMap<String, String>();
		for (MatchcodeList mCL : resultMatchcodeList) {
			translationMap.put(mCL.getId().getFirstName() + ""
					+ mCL.getId().getLastName(), mCL.getId().getMatchcode());
		}

		Map<String, String> byNames = util.getMatchNameBased();
		for (Map.Entry<String, String> singleNameBasedData : byNames.entrySet()) {
			String rawName = singleNameBasedData.getKey();
			InputData inputData = new InputData();
			inputData.setInputFileType(ift);
			inputData
					.setRevenue(BigDecimal.valueOf(Double.valueOf(singleNameBasedData.getValue().replace(",", "."))));
			final String tmpMatch = translationMap.get(rawName);
			if (tmpMatch != null && !"".equals(tmpMatch)) {
				inputData.setMatchcode(tmpMatch);
				matchCodes.add(tmpMatch);
				inputData.setPeriod(period);
				em.persist(inputData);
			} else {
				LOG.info("ERRO : "  + rawName + " NOT EXISTS!!!");
			}
		}
		em.flush();
		return matchCodes;
	}

	private Map<String, Department> createDepEmplPeriod(Period period) {
		
        em.refresh(period);
		Map<String, Department> codeDeptPair = new HashMap<String, Department>();

		Query deptRevQ = em	.createQuery(" from DepartmentRevenue  where  id.periodCode = :pCode");
		deptRevQ.setParameter("pCode", period.getCode());

		List<DepartmentRevenue> deptRevenues = (List<DepartmentRevenue>) deptRevQ.getResultList();

		for (DepartmentRevenue deptRev : deptRevenues) {

			Department department = null;
			String code = deptRev.getId().getCode();
			Query depQ = em.createQuery("from Department where code = :dCode");
			depQ.setParameter("dCode", code);
			department = ((List<Department>) depQ.getResultList()).get(0);
			if (!codeDeptPair.keySet().contains(code)) {
				codeDeptPair.put(code, department);
			} else {
				department = codeDeptPair.get(code);
			}
			RevenueDeptPeriod revDeptPer = new RevenueDeptPeriod();
			revDeptPer.setDepartment(department);
			revDeptPer.setPeriod(period);
			revDeptPer.setRevenue(deptRev.getId().getDepRevenue());
			em.persist(revDeptPer);
		}
		em.flush();

		return codeDeptPair;
	}

	/**
	 * Create Employee Department Period link
	 * 
	 * @param em
	 * @param period
	 * @param empl
	 * @param dept
	 */
	private void createEmplDeptPeriod(EntityManager em, Period period,
			Employee empl, Department dept) {
		EmplDeptPeriod emplDeptPeriod = new EmplDeptPeriod();
		emplDeptPeriod.setDepartment(dept);
		emplDeptPeriod.setEmployee(empl);
		emplDeptPeriod.setPeriod(period);
		em.persist(emplDeptPeriod);
		em.flush();
	}

	/**
	 * Create Revenue Employee Period
	 * 
	 * @param em
	 * @param period
	 * @param empl
	 * @param dept
	 * @param revenueEmpl4Period
	 */
	private void createRevenueEmplPeriod(EntityManager em, Period period, Employee empl, BigDecimal revenueEmpl4Period) {

		RevenueEmplPeriod rEP = new RevenueEmplPeriod();
		rEP.setEmployee(empl);
		rEP.setPeriod(period);
		rEP.setRevenue(revenueEmpl4Period);
		em.persist(rEP);
		em.flush();
	}

}
