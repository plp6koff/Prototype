package com.consultancygrid.trz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Utility methods for Date and Time conversions.
 * 
 * @author <a href="m.murad@proxiad.com">Murad M. M.</a>
 */
public class DateTimeTools {

	public static final String DATE_PATTERN_DMY				= "dd.MM.yyyy";
	public static final String DATE_PATTERN_DMY_DASH		   = "dd-MM-yyyy";
	public static final String DATE_PATTERN_DMY_SLASH		  = "dd/MM/yyyy";
	public static final String DATE_PATTERN_DMY_FLAT		   = "ddMMyyyy";
	public static final String DATE_PATTERN_YMD				= "yyyy.MM.dd";
	public static final String DATE_PATTERN_YMD_DASH		   = "yyyy-MM-dd";
	public static final String DATE_PATTERN_YMD_SLASH		  = "yyyy/MM/dd";
	public static final String DATE_PATTERN_YMD_FLAT		   = "yyyyMMdd";

	public static final String TIME_PATTERN_HMS				= "HH:mm:ss";
	public static final String TIME_PATTERN_HM				 = "HH:mm";

	public static final String DATE_TIME_PATTERN_DMY_HMS	   = "dd.MM.yyyy HH:mm:ss";
	public static final String DATE_TIME_PATTERN_DMY_HMS_DASH  = "dd-MM-yyyy HH:mm:ss";
	public static final String DATE_TIME_PATTERN_DMY_HMS_SLASH = "dd/MM/yyyy HH:mm:ss";

	public static final String DATE_TIME_PATTERN_YMD_HMS	   = "yyyy.MM.dd HH:mm:ss";
	public static final String DATE_TIME_PATTERN_YMD_HMS_DASH  = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_PATTERN_YMD_HMS_SLASH = "yyyy/MM/dd HH:mm:ss";

	public static Date parseDate(String dateString, String pattern) {
		Date date = null;

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			date = sdf.parse(dateString);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}

		return date;
	}

	public static String formatDate(Calendar date) {
		int m = date.get(Calendar.MONTH) + 1;
		int d = date.get(Calendar.DATE);

		StringBuffer s = new StringBuffer();
		s.append(date.get(Calendar.YEAR) + "-");
		if (m < 10) {
			s.append("0");
		}
		s.append(m + "-");
		if (d < 10) {
			s.append("0");
		}
		s.append(d);

		return s.toString();
	}

	/**
	 * Format date string
	 * 
	 * @param date
	 * @param pattern
	 * 
	 * @return String
	 */
	public static String formatDate(Date date, String pattern) {
		String ds = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if (pattern.equals(DATE_PATTERN_DMY)) {
			ds = (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE) + "."
				 + (cal.get(Calendar.MONTH) + 1 < 10 ? "0" : "") + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR);
		} else if (pattern.equals(DATE_PATTERN_DMY_DASH)) {
			ds = (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE) + "-"
				 + (cal.get(Calendar.MONTH) + 1 < 10 ? "0" : "") + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
		} else if (pattern.equals(DATE_PATTERN_DMY_SLASH)) {
			ds = (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE) + "/"
				 + (cal.get(Calendar.MONTH) + 1 < 10 ? "0" : "") + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
		} else if (pattern.equals(DATE_PATTERN_YMD)) {
			ds = cal.get(Calendar.YEAR) + "." + (cal.get(Calendar.MONTH) + 1 < 10 ? "0" : "") + (cal.get(Calendar.MONTH) + 1) + "."
				 + (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE);
		} else if (pattern.equals(DATE_PATTERN_YMD_DASH)) {
			ds = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1 < 10 ? "0" : "") + (cal.get(Calendar.MONTH) + 1) + "-"
				 + (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE);
		} else if (pattern.equals(DATE_PATTERN_YMD_SLASH)) {
			ds = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1 < 10 ? "0" : "") + (cal.get(Calendar.MONTH) + 1) + "/"
				 + (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE);
		}

		return ds;
	}

	/**
	 * Format date string
	 * 
	 * @param dateString
	 * @param pattern
	 * 
	 * @return String
	 */
	public static String formatDate(String dateString, String pattern) {
		return formatDate(parseDate(dateString, pattern), pattern);
	}

	/**
	 * Format time string
	 * 
	 * @param date
	 * @param pattern
	 * 
	 * @return String
	 */
	public static String formatTime(Date date, String pattern) {
		String ts = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if (pattern.equals(TIME_PATTERN_HMS)) {
			ts = (cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + cal.get(Calendar.HOUR_OF_DAY) + ":"
				 + (cal.get(Calendar.MINUTE) < 10 ? "0" : "") + cal.get(Calendar.MINUTE) + ":"
				 + (cal.get(Calendar.SECOND) < 10 ? "0" : "") + cal.get(Calendar.SECOND);
		} else if (pattern.equals(TIME_PATTERN_HM)) {
			ts = (cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + cal.get(Calendar.HOUR_OF_DAY) + ":"
				 + (cal.get(Calendar.MINUTE) < 10 ? "0" : "") + cal.get(Calendar.MINUTE);
		}

		return ts;
	}

	/**
	 * Get current date/time
	 * 
	 * @param pattern
	 * 
	 * @return String
	 */
	public static String getNow(String pattern) {
		return formatDate(new Date(), pattern);
	}

	/**
	 * Get current year
	 * 
	 * @return int
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	/**
	 * Get days between two dates
	 * 
	 * @param startDate
	 * @param endDate
	 * @param pattern
	 * 
	 * @return Vector<Date>
	 */
	public static Vector<Date> getDateInterval(String startDate, String endDate, String pattern) {
		Vector<Date> dateInterval = new Vector<Date>();

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(parseDate(startDate, pattern));

		Calendar endCal = Calendar.getInstance();
		startCal.setTime(parseDate(endDate, pattern));
		endCal.add(Calendar.DATE, 1);

		while (startCal.before(endCal)) {
			dateInterval.add(startCal.getTime());
			startCal.add(Calendar.DATE, 1);
		}

		return dateInterval;
	}

	/**
	 * Get future or past date
	 * 
	 * @param days
	 * @param months
	 * @param years
	 * 
	 * @return Date
	 */
	public static Date getDateTill(int days, int months, int years) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		cal.add(Calendar.MONTH, months);
		cal.add(Calendar.YEAR, years);

		return cal.getTime();
	}

	/**
	 * Get duration in days between two dates
	 * 
	 * @param startDate
	 * @param endDate
	 * @param pattern
	 * 
	 * @return double
	 */
	public static double getDateDurationInDays(String startDate, String endDate, String pattern) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(parseDate(startDate, pattern));

		Calendar endCal = Calendar.getInstance();
		startCal.setTime(parseDate(endDate, pattern));

		long duration = startCal.getTime().getTime() - endCal.getTime().getTime();

		return (duration / (24 * 60 * 60 * 1000));
	}

	/**
	 * 
	 * Adds specified amount of time to a {@link Date} and returns the result in
	 * a new {@link Date} object
	 * 
	 * @param to The start date
	 * @param celendarField The field that specifies what is added. See {@link Calendar#add(int, int)}
	 * @param amount Amount to add/substract
	 * @return
	 */
	public static Date add(Date to, int celendarField, int amount) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(to);
		startCal.add(celendarField, amount);
		return startCal.getTime();
	}
}
