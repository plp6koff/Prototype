package com.consultancygrid.trz.util;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.consultancygrid.trz.model.Employee;
import com.consultancygrid.trz.model.EmployeeSettings;
import com.consultancygrid.trz.model.Period;

public class EmployeeSettingsUtil {

	public static void createSettings(EntityManager em, Period period, Employee empl) {

		EmployeeSettings settings = new EmployeeSettings();
		settings.setPeriod(period);
		settings.setEmployee(empl);

		Query q = em
				.createQuery(" from EmployeeSettings as settings  where  settings.employee.id = :employeeId order by settings.period.code desc");
		q.setParameter("employeeId", empl.getId());
		List<EmployeeSettings> emplSettingsList = (List<EmployeeSettings>) q
				.getResultList();
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
			settings.setPersonAllOnboardingPercent(initSettings
					.getPersonAllOnboardingPercent() != null ? initSettings
					.getPersonAllOnboardingPercent() : BigDecimal.ONE);
			settings.setPersonGroupOnboardingPercent(initSettings
					.getPersonGroupOnboardingPercent() != null ? initSettings
					.getPersonGroupOnboardingPercent() : BigDecimal.ONE);
		} else {
			settings.setBrutoPoShtat(BigDecimal.ZERO);
			settings.setBrutoStandart(BigDecimal.ZERO);
			settings.setAvans(BigDecimal.ZERO);
			settings.setPercentAll(BigDecimal.ZERO);
			settings.setPercentGroup(BigDecimal.ZERO);
			settings.setPercentPersonal(BigDecimal.ZERO);
			settings.setPersonAllOnboardingPercent(BigDecimal.ONE);
			settings.setPersonGroupOnboardingPercent(BigDecimal.ONE);
		}

		BigDecimal tempOnBoard_all = settings.getPersonAllOnboardingPercent();
		if (BigDecimal.ONE.compareTo(tempOnBoard_all) == 1) {
			BigDecimal tempToAdd = FormatUtil.parseValue(BigDecimal.ZERO, "0.15");
			settings.setPersonAllOnboardingPercent(tempOnBoard_all
					.add(tempToAdd));
		} else {
			settings.setPersonAllOnboardingPercent(tempOnBoard_all);
		}
		BigDecimal tempOnBoard_gr = settings.getPersonGroupOnboardingPercent();
		if (BigDecimal.ONE.compareTo(tempOnBoard_gr) == 1) {
			BigDecimal tempToAdd = FormatUtil.parseValue(BigDecimal.ZERO, "0.15");
			settings.setPersonGroupOnboardingPercent(tempOnBoard_gr
					.add(tempToAdd));
		} else {
			settings.setPersonGroupOnboardingPercent(tempOnBoard_gr);
		}
		em.persist(settings);
	}
}
