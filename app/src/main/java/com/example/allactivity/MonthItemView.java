package com.example.allactivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import DB.CalendarDBHandler;
import DB.GoogleCalendarDBHandler;

public class MonthItemView extends LinearLayout{
	
	private MonthItem item;
    TextView text01;
    ImageView image01;

    CalendarDBHandler db;
    GoogleCalendarDBHandler gdb;

    public MonthItemView(Context context){
		super(context);
		
		init(context);
	}
	
	public MonthItemView(Context context, AttributeSet attrs){
		super(context,attrs);
		init(context);
	}
	
	private void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.itemview,this,true);

        text01=(TextView)findViewById(R.id.text01);
        image01=(ImageView)findViewById(R.id.image01);

        db = new CalendarDBHandler(getContext());
        gdb = new GoogleCalendarDBHandler(getContext());

        setBackgroundColor(Color.WHITE);
	}
	
	public MonthItem getItem(){
		return item;
	}

    public void setItem(MonthItem item){
        this.item = item;

        int day = item.getDay();
        if(day !=0){
            text01.setText(String.valueOf(day));
            if(!db.getSelectedCalendars(item.getYear(),item.getMonth(),item.getDay()).isEmpty() || !gdb.getSelectedCalendars(item.getYear(),item.getMonth(),item.getDay()).isEmpty() ){
                image01.setImageResource(R.drawable.check);
            }else{
                image01.setImageResource(R.drawable.back);
            }
        }else{
            text01.setText("");
        }


    }

    public void setTextColor(int color){
        text01.setTextColor(color);
    }

}
