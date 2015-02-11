package com.mygdx.game.utils.calendar;

public enum Month {
	FROSTPEAK(1, 42),
	WINTERWANE(2,41),
	RAINMOOT(3, 42),
	PALESUN(4, 41),
	HIGHSUN(5, 42),
	FIREWANE(6, 42),
	REDFALL(7, 40),
	FELLNIGHT(8, 42);

	private final int daysInMonth;
	private final int monthNumber;
	
	Month (int monthNumber, int daysInMonth) {
		this.daysInMonth = daysInMonth;
		this.monthNumber = monthNumber;
	}

	public int getLength() {
		return daysInMonth;
	} 
	
	public int getMonthNumber() {
		return monthNumber;
	}
	
}
