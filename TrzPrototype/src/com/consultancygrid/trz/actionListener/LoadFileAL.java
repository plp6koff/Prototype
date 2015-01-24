package com.consultancygrid.trz.actionListener;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;
import com.consultancygrid.trz.model.AvgInputData;
import com.consultancygrid.trz.model.Department;
import com.consultancygrid.trz.model.DepartmentRevenue;
import com.consultancygrid.trz.model.EmplDeptPeriod;
import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSalary;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.InputData;
import com.consultancygrid.trz.model.InputFileType;
import com.consultancygrid.trz.model.MatchcodeList;
import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
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

	static private final String newline = "\n";
	private static final String f1 = "order";
	private static final String f2 = "turhauptdisponent";
	private static final String f3 = "sales";
	private static final String f4 = "tur last change";
	private JFileChooser fc;
	private JTextArea log;
	private File file;
	private JLabel labelProcessing;
	private JPanel firstInnerPanel;
	private JComboBox comboBoxPeriod;
	
	public LoadFileAL(PrototypeMainFrame mainFrame, JFileChooser fc,
			JTextArea log, File file, JLabel labelProcessing, JPanel firstInnerPanel, JComboBox comboBoxPeriod) {

		super(mainFrame);
		this.fc = fc;
		this.log = log;
		this.file = file;
		this.labelProcessing = labelProcessing;
		this.firstInnerPanel = firstInnerPanel;
		this.comboBoxPeriod = comboBoxPeriod;
	}

	public void actionPerformed(ActionEvent e) {

		firstInnerPanel.add(labelProcessing);
		labelProcessing.setHorizontalTextPosition(JLabel.LEADING);
		AnimatedIcon icon2 = new AnimatedIcon(labelProcessing);
		icon2.setAlignmentX(AnimatedIcon.LEFT);
		icon2.addIcon(new TextIcon(labelProcessing, "."));
		icon2.addIcon(new TextIcon(labelProcessing, ".."));
		icon2.addIcon(new TextIcon(labelProcessing, "..."));
		icon2.addIcon(new TextIcon(labelProcessing, "...."));
		icon2.addIcon(new TextIcon(labelProcessing, "....."));
		icon2.addIcon(new TextIcon(labelProcessing, "......."));
		labelProcessing.setIcon(icon2);
		icon2.start();
		firstInnerPanel.revalidate();
		firstInnerPanel.repaint();
		EntityManagerFactory factory = null;
		EntityManager em = null;
		Period period = null;
		
		Set<String> matchCodes = null;
		try {

			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

			em = factory.createEntityManager();
			em.getTransaction().begin();

			CSVReaderUtil util = new CSVReaderUtil();
			if (file != null) {

				util.readCSVcarloFibu(file);
				// This is where a real application would save the file.
				log.append("Loading: " + file.getName() + "." + newline);
				log.setCaretPosition(log.getDocument().getLength());
			} else {

				final File folder = new File(
						ResourceLoaderUtil
								.getConfig(Constants.DEFAULT_DATA_DIR));
				for (final File fileEntry : folder.listFiles()) {

					System.out.println(fileEntry.getName());
					InputData result = new InputData();

					String name = fileEntry.getName();

					String periodCode = "";

					Query q = em
							.createQuery("from InputFileType  where prefix = :prefix");
					if (name.startsWith(f1)) {

						q.setParameter("prefix", f1);
						periodCode = name.substring(f1.length() + 1,
								name.indexOf(".Csv"));
					} else if (name.startsWith(f2)) {
						q.setParameter("prefix", f2);
						periodCode = name.substring(f2.length() + 1,
								name.indexOf(".Csv"));
					} else if (name.startsWith(f3)) {
						q.setParameter("prefix", f3);
						periodCode = name.substring(f3.length() + 1,
								name.indexOf(".Csv"));
					} else if (name.startsWith(f4)) {
						q.setParameter("prefix", f4);
						periodCode = name.substring(f4.length() + 1,
								name.indexOf(".Csv"));
					} else {
						System.err.println("Error Format!");
						// TODO trow exception
					}

					if (period == null) {

						period = new Period();
						period.setCode(periodCode.trim());
						em.persist(period);

						Query qPeriodTrzStatic = em
								.createQuery(" from TrzStatic");
						List<TrzStatic> trzResult = (List<TrzStatic>) qPeriodTrzStatic
								.getResultList();

						HashMap<TrzStatic, JTextField> map = new HashMap<TrzStatic, JTextField>();
						for (TrzStatic singleStatic : trzResult) {

							PeriodSetting ps = new PeriodSetting();
							ps.setPeriod(period);
							ps.setTrzStatic(singleStatic);
							ps.setValue(singleStatic.getValue());
							em.persist(ps);
						}
					}
					List<InputFileType> selectedFile = (List<InputFileType>) q
							.getResultList();
					matchCodes  = processSingleFile(fileEntry, em, selectedFile.get(0),
							period);

				}
			}
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

		
		PeriodComboBoxModel periodComboModel = (PeriodComboBoxModel) comboBoxPeriod.getModel();
		List<Period> allPeriods = periodComboModel.getComboBoxItemList();
		try {

			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

			em = factory.createEntityManager();
			em.getTransaction().begin();

			empl2Period2Depart(em, period, matchCodes);
			
			Query qPeriod = em.createQuery(" from Period");
			 allPeriods = (List<Period>) qPeriod.getResultList();

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
		periodComboModel.addAll(allPeriods);
		icon2.stop();
		labelProcessing.setVisible(false);
		firstInnerPanel.revalidate();
		firstInnerPanel.repaint();
		comboBoxPeriod.revalidate();
		comboBoxPeriod.repaint();
		try {
			JOptionPane.showMessageDialog(mainFrame,
					ResourceLoaderUtil.getLabels(LabelsConstants.SET_TAB_LOAD_FILE_SUCCESS),
					ResourceLoaderUtil.getLabels(LabelsConstants.ALERT_MSG_OK),
					JOptionPane.INFORMATION_MESSAGE);
		} catch (HeadlessException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private Set<String> processSingleFile(File file, EntityManager em,
			InputFileType ift, Period period) {

		
		Set<String> matchCodes = new HashSet<String>();
		
		CSVReaderUtil util = new CSVReaderUtil();
		util.readCSVcarloFibu(file);
		Map<String, String> data = util.getMatchCodeRev();
		for (Map.Entry<String, String> singleData : data.entrySet()) {
			matchCodes.add(singleData.getKey());
			InputData inputData = new InputData();
			inputData.setInputFileType(ift);
			inputData.setRevenue(BigDecimal.valueOf(Double.valueOf(singleData
					.getValue().replace(",", "."))));
			inputData.setMatchcode(singleData.getKey());
			inputData.setPeriod(period);
			em.persist(inputData);
		}

		Map<String, String> byNames = util.getMatchNameBased();
		for (Map.Entry<String, String> singleNameBasedData : byNames.entrySet()) {

			String rawName = singleNameBasedData.getKey();
			Query emplQ = em
					.createQuery("select mcl from MatchcodeList as mcl where mcl.id.firstName = :firstName and mcl.id.lastName = :lastName");
			emplQ.setParameter("firstName",
					rawName.substring(0, rawName.indexOf(" ")));
			emplQ.setParameter("lastName",
					rawName.substring(rawName.indexOf(" ") + 1));
			List<MatchcodeList> result = (List<MatchcodeList>) emplQ
					.getResultList();

			InputData inputData = new InputData();
			inputData.setInputFileType(ift);
			inputData
					.setRevenue(BigDecimal.valueOf(Double
							.valueOf(singleNameBasedData.getValue().replace(
									",", "."))));
			inputData.setMatchcode(result.get(0).getId().getMatchcode());
			matchCodes.add(result.get(0).getId().getMatchcode());
			inputData.setPeriod(period);
			em.persist(inputData);
			
		}
		return matchCodes;
	}

	public void empl2Period2Depart(EntityManager em, Period period, Set<String> matchCodes) {

		Map<String, Department> codeDeptPair = new HashMap<String, Department>();

		Query deptRevQ = em
				.createQuery(" from DepartmentRevenue  where  id.periodCode = :pCode");
		deptRevQ.setParameter("pCode", period.getCode());
		List<DepartmentRevenue> deptRevenues = (List<DepartmentRevenue>) deptRevQ
				.getResultList();
		for (DepartmentRevenue deptRev : deptRevenues) {

			Department department = null;
			String code = deptRev.getId().getCode();
			if (!codeDeptPair.keySet().contains(code)) {
				Query depQ = em
						.createQuery("from Department where code = :dCode");
				depQ.setParameter("dCode", code);
				department = ((List<Department>) depQ.getResultList()).get(0);
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

		Query avgQ = em
				.createQuery("from AvgInputData  where  id.periodCode = :pCode");
		avgQ.setParameter("pCode", period.getCode());
		List<AvgInputData> avgs = (List<AvgInputData>) avgQ.getResultList();
		BigDecimal bDecimal = BigDecimal.ZERO;
		for (AvgInputData singleAvg : avgs) {

			bDecimal = bDecimal.add(singleAvg.getId().getRevenue());
			String mCode = singleAvg.getId().getMatchcode();
			Query machDept = em
					.createQuery("from UserDepartment  where id.matchcode = :mCode");
			machDept.setParameter("mCode", mCode);

			List<UserDepartment> uDepts = (List<UserDepartment>) machDept
					.getResultList();
			if (!uDepts.isEmpty()) {
				UserDepartment uDept = uDepts.get(0);
				// Period
				// Match code
				Query emplQ = em
						.createQuery("from Employee where matchcode = :mCode");
				emplQ.setParameter("mCode", mCode);
				Employee employee = ((List<Employee>) emplQ.getResultList()).get(0);
				matchCodes.remove(employee.getMatchCode());
				// Department
				Department department = codeDeptPair.get(uDept.getId()
						.getDepId());

				createEmplDeptPeriod(em, period, employee, department,
						singleAvg.getId().getRevenue());
				EmployeeSettingsUtil.createSettings(em, period, employee);
				EmployeeSalaryUtil.createSalary(em, period, employee);

				EmplDeptPeriod emplDeptPeriod = new EmplDeptPeriod();
				emplDeptPeriod.setDepartment(department);
				emplDeptPeriod.setEmployee(employee);
				emplDeptPeriod.setPeriod(period);
				em.persist(emplDeptPeriod);
			}
		}
		
		for (String matchCode : matchCodes) {
			
			Query emplQ = em
					.createQuery("from Employee where matchcode = :mCode");
			emplQ.setParameter("mCode", matchCode);
			List<Employee> empls = (List<Employee>) emplQ.getResultList();
			if (empls!= null && !empls.isEmpty()){
				
				Employee employee = empls.get(0);
				EmployeeSettingsUtil.createSettings(em, period, employee);
				EmployeeSalaryUtil.createSalary(em, period, employee);
			}
		}
		
		period.setRevenue(bDecimal);
		em.merge(period);
	}

	private void createEmplDeptPeriod(EntityManager em, Period period,
			Employee empl, Department dept, BigDecimal revenueEmpl4Period) {

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

}
