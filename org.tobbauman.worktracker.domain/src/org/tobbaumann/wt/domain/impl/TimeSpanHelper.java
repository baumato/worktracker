package org.tobbaumann.wt.domain.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeSpanHelper {
	private int years;
	private int days;
	private int hours;
	private int minutes;
	private int seconds;
	private long diff;

	public TimeSpanHelper(int years, int days, int hours, int minutes, int seconds) {
		this.years = years;
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		diff = seconds + 60 * minutes + 60 * 60 * hours + 24 * 60 * 60 * days + 365 * 24 * 60 * 60
				* years;
	}

	protected TimeSpanHelper(int years, int days, int hours, int minutes, int seconds, long diff) {
		this.years = years;
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		this.diff = diff;
	}

	public static TimeSpanHelper getInstance(Date start, Date end) {
		GregorianCalendar g1 = new GregorianCalendar();
		g1.setTime(start);
		GregorianCalendar g2 = new GregorianCalendar();
		g2.setTime(end);
		return getInstance(g1, g2, true, true);
	}

	// Erzeugt aus zwei GregorianCalendar-Objekten
	// ein TimeSpan-Objekt
	public static TimeSpanHelper getInstance(GregorianCalendar t1, GregorianCalendar t2, boolean summer,
			boolean leap) {
		GregorianCalendar first, last;
		TimeZone tz;
		long diff, save;

		// Immer frühere Zeit von späteren Zeit abziehen
		if (t1.getTimeInMillis() > t2.getTimeInMillis()) {
			last = t1;
			first = t2;
		} else {
			last = t2;
			first = t1;
		}

		// Differenz in Sekunden
		diff = (last.getTimeInMillis() - first.getTimeInMillis()) / 1000;

		if (summer) { // Sommerzeit ausgleichen
			tz = first.getTimeZone();
			if (!(tz.inDaylightTime(first.getTime())) && (tz.inDaylightTime(last.getTime())))
				diff += tz.getDSTSavings() / 1000;
			if ((tz.inDaylightTime(first.getTime())) && !(tz.inDaylightTime(last.getTime())))
				diff -= tz.getDSTSavings() / 1000;
		}

		save = diff;

		// Sekunden, Minuten und Stunden berechnen
		int seconds = (int) (diff % 60);
		diff /= 60;
		int minutes = (int) (diff % 60);
		diff /= 60;
		int hours = (int) (diff % 24);
		diff /= 24;

		// Jahre und Tage berechnen
		int days = 0;
		int years = 0;

		if (leap) { // Schaltjahre ausgleichen
			int startYear = 0, endYear = 0;
			int leapDays = 0; // Schalttage in Zeitraum
			int subtractLeapDays = 0; // abzuziehende Schalttage
			// (da in Jahren enthalten)

			if ((first.get(Calendar.MONTH) < 1)
					|| ((first.get(Calendar.MONTH) == 1) && (first.get(Calendar.DAY_OF_MONTH) < 29)))
				startYear = first.get(Calendar.YEAR);
			else
				startYear = first.get(Calendar.YEAR) + 1;

			if ((last.get(Calendar.MONTH) > 1)
					|| ((last.get(Calendar.MONTH) == 1) && (last.get(Calendar.DAY_OF_MONTH) == 29)))
				endYear = last.get(Calendar.YEAR);
			else
				endYear = last.get(Calendar.YEAR) - 1;

			for (int i = startYear; i <= endYear; ++i)
				if (first.isLeapYear(i))
					++leapDays;

			// Jahre berechnen
			years = (int) ((diff - leapDays) / 365);

			// in Jahren enthaltene Schalttage
			subtractLeapDays = (years + 3) / 4;
			if (subtractLeapDays > leapDays)
				subtractLeapDays = leapDays;

			// Tage berechnen
			days = (int) (diff - ((years * 365) + subtractLeapDays));

		} else {
			days = (int) (diff % 365);
			years = (int) (diff / 365);
		}

		return new TimeSpanHelper(years, days, hours, minutes, seconds, save);
	}

	public int getYears() {
		return years;
	}

	public int getDays() {
		return days;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public int inYears() {
		return (int) (diff / (60 * 60 * 24 * 365));
	}

	public int inWeeks() {
		return (int) (diff / (60 * 60 * 24 * 7));
	}

	public int inDays() {
		return (int) (diff / (60 * 60 * 24));
	}

	public int inHours() {
		return (int) (diff / (60 * 60));
	}

	public int inMinutes() {
		return (int) (diff / 60);
	}

	public int inSeconds() {
		return (int) (diff);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("");
		if (years > 0)
			s.append(years + " j  ");
		if (days > 0)
			s.append(days + " t  ");
		if (hours > 0)
			s.append(hours + " std  ");
		if (minutes > 0)
			s.append(minutes + " min  ");
		if (seconds > 0)
			s.append(seconds + " sec  ");
		if (s.toString().equals(""))
			s.append("Kein Zeitunterschied");
		return s.toString();
	}
}