package com.example.allactivity;

public class MonthItem {
	private int dayValue;
    private int curYear;
    private int curMonth;
	
	public MonthItem(){
		
	}

    public MonthItem(int day, int Month, int Year){
        dayValue = day; curYear = Year; curMonth = Month;
    }
	
	public int getDay(){
		return dayValue;
	}
	
	public void setDay(int day){
		this.dayValue = day;
	}

    public int getYear() {
        return curYear;
    }

    public void setYear(int curYear) {
        this.curYear = curYear;
    }

    public int getMonth() {
        return curMonth;
    }

    public void setMonth(int curMonth) {
        this.curMonth = curMonth;
    }
}
