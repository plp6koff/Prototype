package com.consultancygrid.trz.data;

import static com.consultancygrid.trz.base.Constants.PERSISTENCE_UNIT_NAME;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JTextField;

import org.pmw.tinylog.Logger;

import com.consultancygrid.trz.model.Period;
import com.consultancygrid.trz.model.PeriodSetting;
import com.consultancygrid.trz.model.TrzStatic;
import com.consultancygrid.trz.util.HibernateUtil;

public class StaticDataLoader {

	private static String DOD_KEY = "DOD";
	private static String OR_KEY = "OSIGUROVKI_RABOTODATEL";
	private static String OS_KEY = "OSIGUROVKI_SLUJITEL";
	private static String CT_KEY = "CACHE_TAX";
	private static String V1_KEY =  "VAUCHER1";
	private static String V2_KEY = "VAUCHER2";
	private static String SQL1 = " from PeriodSetting as pS where period.id = :periodId";
	
	
	public static TrzStaticData load(UUID id, EntityManager em) {

		
		TrzStaticData data = new TrzStaticData();
		Query qPeriodTrzStatic = em.createQuery(SQL1);
		qPeriodTrzStatic.setParameter("periodId", id);

		@SuppressWarnings("unchecked")
		List<PeriodSetting> periodSettings = (List<PeriodSetting>) qPeriodTrzStatic
				.getResultList();

		for (PeriodSetting singlePS : periodSettings) {

			TrzStatic singleTrz = singlePS.getTrzStatic();
			Double tmpValue = 0.0d;
			if (singlePS.getValue() != null) {
				tmpValue = Double.valueOf(singlePS.getValue());
			}

			if (DOD_KEY.equals(singleTrz.getKey())) {
				data.setDOD(singleTrz);
				data.setDodValue(Double.valueOf(singlePS.getValue()));
			} else if (OR_KEY.equals(singleTrz.getKey())) {
				data.setOSIGUROVKI_RABOTODATEL(singleTrz);
				data.setoRabotodatelValue(tmpValue);
			} else if (OS_KEY.equals(singleTrz.getKey())) {
				data.setOSIGUROVKI_SLUJITEL(singleTrz);
				data.setoSlujitelValue(tmpValue);
			} else if (CT_KEY.equals(singleTrz.getKey())) {
				data.setCACHE_TAX(singleTrz);
				data.setCacheTaxValue(tmpValue);
			} else if ((V1_KEY.equals(singleTrz.getKey()))
					|| (V2_KEY.equals(singleTrz.getKey()))) {
				data.setVauchers(data.getVauchers() + tmpValue);
			}
		}
		return data;
	}

	public static  TrzStaticData load(UUID id) {

		EntityManager em = null;
		EntityTransaction trx = null;
		TrzStaticData result = new TrzStaticData();
		try {

			em = HibernateUtil.getEntityManager();
			trx = em.getTransaction();
			trx.begin();;
			result = load(id, em);

		} catch (Exception e1) {
			Logger.error(e1);
			if (trx!= null && trx.isActive()) {
				trx.rollback();
			}
		} finally {
			if (trx!= null && trx.isActive()) {
				trx.commit();
			}
		}
		return result;

	}

	public static TrzStaticData persistPersonSettings(Period period,
			Map<TrzStatic, JTextField> map) {

		EntityManager em = null;
		EntityTransaction trx = null;
		TrzStaticData result = new TrzStaticData();

		try {
			em = HibernateUtil.getEntityManager();
			trx = em.getTransaction();
			trx.begin();;
			Double vauchers = 0.0d;

			TrzStatic DOD = null;
			TrzStatic OSIGUROVKI_RABOTODATEL = null;
			TrzStatic OSIGUROVKI_SLUJITEL = null;
			TrzStatic CACHE_TAX = null;
			Double dodValue = 0.0d;
			Double oRabotodatelValue = 0.0d;
			Double oSlujitelValue = 0.0d;
			Double cacheTaxValue = 0.0d;

			for (Entry<TrzStatic, JTextField> entry : map.entrySet()) {

				PeriodSetting ps = new PeriodSetting();
				ps.setPeriod(period);
				ps.setTrzStatic(entry.getKey());
				ps.setValue(entry.getValue().getText());

				if (DOD_KEY.equals(entry.getKey().getKey())) {
					DOD = entry.getKey();
					dodValue = Double.valueOf(entry.getValue().getText());
				} else if (OR_KEY.equals(entry.getKey()
						.getKey())) {
					OSIGUROVKI_RABOTODATEL = entry.getKey();
					oRabotodatelValue = Double.valueOf(entry.getValue()
							.getText());
				} else if (OS_KEY
						.equals(entry.getKey().getKey())) {
					OSIGUROVKI_SLUJITEL = entry.getKey();
					oSlujitelValue = Double.valueOf(entry.getValue().getText());
				} else if (CT_KEY.equals(entry.getKey().getKey())) {
					CACHE_TAX = entry.getKey();
					cacheTaxValue = Double.valueOf(entry.getValue().getText());
				} else if ((V1_KEY.equals(entry.getKey().getKey()))
						|| (V2_KEY.equals(entry.getKey().getKey()))) {

					vauchers = vauchers
							+ Double.valueOf(entry.getValue().getText());
				}

				em.persist(ps);
			}
			result.setCACHE_TAX(CACHE_TAX);
			result.setCacheTaxValue(cacheTaxValue);
			result.setVauchers(vauchers);
			result.setDOD(DOD);
			result.setDodValue(dodValue);
			result.setoRabotodatelValue(oRabotodatelValue);
			result.setOSIGUROVKI_RABOTODATEL(OSIGUROVKI_RABOTODATEL);
			result.setOSIGUROVKI_SLUJITEL(OSIGUROVKI_SLUJITEL);
			result.setoSlujitelValue(oSlujitelValue);
		} catch (Exception e1) {
			Logger.error(e1);
			if (trx!= null && trx.isActive()) {
				trx.rollback();
			}
		} finally {
			if (trx!= null && trx.isActive()) {
				trx.commit();
			}
		}
		return result;
	}

	public static TrzStaticData persistPersonSettings(Period period,
			Map<TrzStatic, JTextField> map, EntityManager em) {

		TrzStaticData result = new TrzStaticData();

		Double vauchers = 0.0d;

		TrzStatic DOD = null;
		TrzStatic OSIGUROVKI_RABOTODATEL = null;
		TrzStatic OSIGUROVKI_SLUJITEL = null;
		TrzStatic CACHE_TAX = null;
		Double dodValue = 0.0d;
		Double oRabotodatelValue = 0.0d;
		Double oSlujitelValue = 0.0d;
		Double cacheTaxValue = 0.0d;

		for (Entry<TrzStatic, JTextField> entry : map.entrySet()) {

			PeriodSetting ps = new PeriodSetting();
			ps.setPeriod(period);
			ps.setTrzStatic(entry.getKey());
			ps.setValue(entry.getValue().getText());

			if (DOD_KEY.equals(entry.getKey().getKey())) {
				DOD = entry.getKey();
				dodValue = Double.valueOf(entry.getValue().getText());
			} else if (OR_KEY.equals(entry.getKey().getKey())) {
				OSIGUROVKI_RABOTODATEL = entry.getKey();
				oRabotodatelValue = Double.valueOf(entry.getValue().getText());
			} else if (OS_KEY.equals(entry.getKey().getKey())) {
				OSIGUROVKI_SLUJITEL = entry.getKey();
				oSlujitelValue = Double.valueOf(entry.getValue().getText());
			} else if (CT_KEY.equals(entry.getKey().getKey())) {
				CACHE_TAX = entry.getKey();
				cacheTaxValue = Double.valueOf(entry.getValue().getText());
			} else if ((V1_KEY.equals(entry.getKey().getKey()))
					|| (V2_KEY.equals(entry.getKey().getKey()))) {

				vauchers = vauchers
						+ Double.valueOf(entry.getValue().getText());
			}

			em.persist(ps);
		}
		result.setCACHE_TAX(CACHE_TAX);
		result.setCacheTaxValue(cacheTaxValue);
		result.setVauchers(vauchers);
		result.setDOD(DOD);
		result.setDodValue(dodValue);
		result.setoRabotodatelValue(oRabotodatelValue);
		result.setOSIGUROVKI_RABOTODATEL(OSIGUROVKI_RABOTODATEL);
		result.setOSIGUROVKI_SLUJITEL(OSIGUROVKI_SLUJITEL);
		result.setoSlujitelValue(oSlujitelValue);
		return result;
	}
}
