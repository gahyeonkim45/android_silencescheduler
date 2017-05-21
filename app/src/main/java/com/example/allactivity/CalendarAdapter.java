package com.example.allactivity;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class CalendarAdapter extends BaseAdapter{

	public static final String TAG = "CalandarMonthAdapter";
	
	Context mContext;
	
	public static int oddColor = Color.rgb(225, 225, 225);
	public static int headColor = Color.rgb(12, 32, 158);
	
	private int selectedPosition = -1;
	
	private MonthItem[] items;
	
	private int countColumn = 7;
	
	int mStartDay;
	int curYear;
	int curMonth;
	
	int firstDay;
	int lastDay;
	int startDay;
	
	Calendar mCalendar;
	boolean recreateItems = false;


	public CalendarAdapter(Context context){
		super();
		mContext = context;
		
		init();
	}
	
	public CalendarAdapter(Context context,AttributeSet attr){
		super();
		mContext = context;
		init();
	}


	private void init(){
		items = new MonthItem[7 * 6];
		
		mCalendar = Calendar.getInstance();
		recalculate();
		resetDayNumbers();
	}
	
	public void recalculate(){

		mCalendar.set(Calendar.DAY_OF_MONTH, 1);

		int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
		firstDay = getFirstDay(dayOfWeek);
		//Log.d(TAG,"firstDay : " + firstDay);
		
		mStartDay = mCalendar.getFirstDayOfWeek();
		curYear = mCalendar.get(Calendar.YEAR);
		curMonth = mCalendar.get(Calendar.MONTH);
		lastDay = getMonthLastDay(curYear, curMonth);
		
		//Log.d(TAG, "curYear : " + curYear +", curMonth : " + curMonth + ", lastDay : "+ lastDay);
		
		int diff = mStartDay - Calendar.SUNDAY -1;
		startDay = getFirstDayOfWeek();
		//Log.d(TAG,"mStartDay :" + mStartDay + ", startDay : " + startDay);
		
	}
	
	private int getFirstDay(int dayOfWeek){
		int result = 0;
		if(dayOfWeek == Calendar.SUNDAY){
			result = 0;
		}else if (dayOfWeek == Calendar.MONDAY){
			result = 1;
		}else if (dayOfWeek == Calendar.TUESDAY){
			result = 2;
		}else if (dayOfWeek == Calendar.WEDNESDAY){
			result = 3;
		}else if (dayOfWeek == Calendar.THURSDAY){
			result = 4;
		}else if (dayOfWeek == Calendar.FRIDAY){
			result = 5;
		}else if (dayOfWeek == Calendar.SATURDAY){
			result = 6;
		}
		
		return result;
	}
	
	private int getFirstDayOfWeek(){
		int startDay = Calendar.getInstance().getFirstDayOfWeek();
		if(startDay == Calendar.SATURDAY){
			return Time.SATURDAY;
		}else if(startDay == Calendar.MONDAY){
			return Time.MONDAY;
		}else{
			return Time.SUNDAY;
		}
	}
	
	private int getMonthLastDay(int curYear, int curMonth){
		switch(curMonth){
		case 0:
		case 2:
		case 4:
		case 6:
		case 7:
		case 9:
		case 11:
			return(31);
		case 3:
		case 5:
		case 8:
		case 10:
			return(30);
			
			default:
				if(((curYear%4==0)&&(curYear%100!=0)) ||(curYear%400==0)){
					return (29);
				}else{
					return (28);
				}
			
		}
	}

	
	public void resetDayNumbers(){

		for(int i =0; i<42; i++){
			int dayNumber = (i+1) - firstDay;
			if(dayNumber <1 || dayNumber > lastDay){
				dayNumber = 0;
			}
			
			items[i] = new MonthItem(dayNumber,curMonth,curYear);

		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 7*6;
	}
	
	

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return items[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//Log.d(TAG,"getView(" + position + ") called.");
		
		MonthItemView itemView;

		if(convertView ==null){
			itemView = new MonthItemView(mContext);
		}else{
			itemView = (MonthItemView) convertView;
		}
		
		GridView.LayoutParams params = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 320);
		
		int rowIndex = position / countColumn;
		int columnIndex = position % countColumn;
		
		//Log.d(TAG, "Index ��ȣ : " + rowIndex + "," + columnIndex);
		
		itemView.setItem(items[position]);
		itemView.setLayoutParams(params);
		itemView.setPadding(2,2,2,2);

		
	itemView.setGravity(Gravity.LEFT);
	
	if(columnIndex == 0){
		itemView.setTextColor(Color.RED);

	}else if(columnIndex == 6) {
        itemView.setTextColor(Color.BLUE);
    }else{
        itemView.setTextColor(Color.BLACK);
    }
	
	
	if(position == getSelectedPosition()){
		itemView.setBackgroundColor(Color.BLUE);
        //itemView.setTextSize(10);
	}else{
        itemView.setBackgroundColor(Color.WHITE);
    }
		
		return itemView;
	}
	
	public void setSelectedPosition(int selectedPosition){
		this.selectedPosition = selectedPosition;
	}
	
	public int getSelectedPosition(){
		return selectedPosition;
	}
	
	public void setPreviousMonth(){
		mCalendar.add(Calendar.MONTH, -1);
		recalculate();
		resetDayNumbers();
		selectedPosition = -1;
	}
	
	public void setNextMonth(){
		mCalendar.add(Calendar.MONTH, 1);
		recalculate();
		
		resetDayNumbers();
		selectedPosition = -1;
	}
	
	public int getCurYear(){
		return curYear;
	}
	public int getCurMonth(){
		return curMonth;
	}
	


}
