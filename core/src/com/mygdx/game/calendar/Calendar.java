package com.mygdx.game.calendar;

public class Calendar {

	private Day day;
	private Month month;
	private int date;
	private int year;
	
	private static Calendar instance;
	
	static {
		instance = new Calendar();
	}
	
	public static Calendar getInstance() {
		return instance;
	}
	
	private Calendar() {
		day = Day.MOONSDAY;
		month = Month.FROSTPEAK;
		date = 1;
		year = 556;
	}

	public int getYear() {
		return year;
	}

	public Month getMonth() {
		return month;
	}

	public Day getDay() {
		return day;
	}

	public String getFullDate() {
		return getDay().toString() + " "
				+ date + " "
				+ getMonth().toString()+ " "
				+ getYear();
	}

	public void incrementDay(int daysPassed) {
		if (date + daysPassed > month.getLength())
		{
			int daysOver = (date + daysPassed) - month.getLength();
			incrementMonth();
			setDate(daysOver);
			for (int i = 0; i < daysPassed; i++)
			{
				incrementDay();
			}
		}
		else
		{
			date += daysPassed;
			for (int i = 0; i < daysPassed; i++)
			{
				incrementDay();
			}
		}
	}


	//Below are for internal use. Only pass time using incrementDay(int);
	private void incrementDay() {
		switch (day)
		{
		case MOONSDAY:
			this.day = Day.TENSDAY;
			break;
		case TENSDAY:
			this.day = Day.WOODSDAY;
			break;
		case WOODSDAY:
			this.day = Day.THUNSDAY;
			break;
		case THUNSDAY:
			this.day = Day.FIRESDAY;
			break;
		case FIRESDAY:
			this.day = Day.SAINTSDAY;
			break;
		case SAINTSDAY:
			this.day = Day.SUNSDAY;
			break;
		case SUNSDAY:
			this.day = Day.MOONSDAY;
			break;	
		}
	}

	private void incrementMonth() {
		switch (month)
		{
		case FROSTPEAK:
			this.month = Month.WINTERWANE;
			break;
		case WINTERWANE:
			this.month = Month.RAINMOOT;
			break;
		case RAINMOOT:
			this.month = Month.PALESUN;
			break;
		case PALESUN:
			this.month = Month.HIGHSUN;
			break;
		case HIGHSUN:
			this.month = Month.FIREWANE;
			break;
		case FIREWANE:
			this.month = Month.REDFALL;
			break;
		case REDFALL:
			this.month = Month.FELLNIGHT;
			break;
		case FELLNIGHT:
			this.month = Month.FROSTPEAK;
			year += 1;
			break;
		}
	}

	private void setDate(int date) {
		this.date = date;
	}
}
