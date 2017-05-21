package com.example.allactivity.addpac;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.allactivity.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import DB.Calendar;
import DB.CalendarDBHandler;
import DB.Details_cal;

/**
 * Created by youngchae on 2015-10-09.
 */
public class AddCal extends FragmentActivity {

    Button btnDate,btnStart,btnEnd;
    Button btnLocation,btnMemo,btnRepeat,btnAlarm;
    EditText editTitle,editMemo,dlgEdtStart;
  //  CheckBox chkAlarm;

    TextView tvYear,tvMonth,tvDayofMonth;
    TextView tvStartHour,tvStartMinute,tvEndHour,tvEndMinute,tvMemo,tvLocationName;
    TimePicker startTIme,endTIme;
    DatePicker datePicker;
    TextView tvselAlarm,tvselRepeat;

    String phoneNum="";
    String locationTitle="";
    String locationLongitude="";
    String locationLatitude ="";

    String p_num ="";
    String title ="";
    int start_year=0;
    int start_month=0;
    int start_day =0;
    int start_hour =0;
    int start_min =0;
    int end_year =0;
    int end_month =0;
    int end_day =0;
    int end_hour =0;
    int end_min =0;


    String memo ="";
    String lati ="";
    String longi ="";
  //  boolean alarm =false;
    int alarm =999;
    String repeat ="";

    CalendarDBHandler db;


    String resultLocation[];
    final int B_ACTIVITY=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        editTitle = (EditText)findViewById(R.id.editTitle);
        tvYear = (TextView)findViewById(R.id.tvYear);
        tvMonth = (TextView)findViewById(R.id.tvMonth);
        tvDayofMonth = (TextView)findViewById(R.id.tvDayofMonth);
        tvStartHour = (TextView)findViewById(R.id.tvStartHour);
        tvStartMinute = (TextView)findViewById(R.id.tvStartMinute);
        tvEndHour = (TextView)findViewById(R.id.tvEndHour);
        tvEndMinute = (TextView)findViewById(R.id.tvEndMinute);
        tvMemo=(TextView)findViewById(R.id.tvMemo);
        tvselAlarm=(TextView)findViewById(R.id.tvselAlarm);
        tvselRepeat=(TextView)findViewById(R.id.tvselRepeat);
        tvLocationName=(TextView)findViewById(R.id.tvLocationName);
       //chkAlarm = (CheckBox)findViewById(R.id.chkAlarm);

        TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        phoneNum = telManager.getLine1Number();


        Button btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button btnComplete = (Button)findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                 p_num =phoneNum.toString();
                 title =editTitle.getText().toString();
         /*        start_year= tvYear.getText().toString();
                 start_month=tvMonth.getText().toString();
                 start_day =tvDayofMonth.getText().toString();
                 start_hour =tvStartHour.getText().toString();
                 start_min =tvStartMinute.getText().toString();
                 end_year = tvYear.getText().toString();
                 end_month =tvMonth.getText().toString();
                 end_day =tvDayofMonth.getText().toString();
                 end_hour =tvEndHour.getText().toString();
                 end_min =tvEndMinute.getText().toString();

*/
                 memo =tvMemo.getText().toString();
                 lati =locationLatitude;
                 longi =locationLongitude;
                if(equals(tvselAlarm.getText().toString()!=""))  alarm = Integer.parseInt(tvselAlarm.getText().toString());
                 repeat =tvselRepeat.getText().toString();

                if(pushParse()==true &&  pushDB()==true && pushCal()==true) finish();

            }
        });


        btnDate = (Button)findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = (View) View.inflate(AddCal.this, R.layout.page2_datepicker,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder (AddCal.this);
                dlg.setTitle("날짜선택");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인",

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //     dlgEdtStart = (EditText)dialogView.findViewById(R.id.dlgEdtStart);

                                datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                                start_year=datePicker.getYear();
                                start_month=(datePicker.getMonth()+1);
                                start_day=datePicker.getDayOfMonth();

                                end_year=datePicker.getYear();
                                end_month=(datePicker.getMonth())+1;
                                end_day=datePicker.getDayOfMonth();


                                tvYear.setText(Integer.toString(datePicker.getYear()));
                                tvMonth.setText(Integer.toString(datePicker.getMonth()+1));
                                tvDayofMonth.setText(Integer.toString(datePicker.getDayOfMonth()));
                              //  tvStartMinute.setText(Integer.toString(startTIme.getCurrentMinute()));






                            }
                        }
                );
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });









        btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = (View) View.inflate(AddCal.this, R.layout.page2_timepicker_start,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder (AddCal.this);
                dlg.setTitle("시작시간");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인",

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //     dlgEdtStart = (EditText)dialogView.findViewById(R.id.dlgEdtStart);

                                startTIme = (TimePicker) dialogView.findViewById(R.id.startTime);

                                start_hour=startTIme.getCurrentHour();
                                start_min=startTIme.getCurrentMinute();


                                tvStartHour.setText(Integer.toString(startTIme.getCurrentHour()));
                                tvStartMinute.setText(Integer.toString(startTIme.getCurrentMinute()));


                            }
                        }
                );
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        btnEnd = (Button)findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = (View) View.inflate(AddCal.this, R.layout.page2_timepicker_end,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder (AddCal.this);
                dlg.setTitle("끝시간");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인",

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                endTIme =(TimePicker) dialogView.findViewById(R.id.endTime);

                                end_hour=endTIme.getCurrentHour();
                                end_min=endTIme.getCurrentMinute();


                                tvEndHour.setText(Integer.toString(endTIme.getCurrentHour()));
                                tvEndMinute.setText(Integer.toString(endTIme.getCurrentMinute()));
                            }
                        }
                );
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });



        btnLocation = (Button)findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),AddCal_location.class);
                intent.putExtra("intData",5);
                intent.putExtra("stringData","안녕 안드로이드");
                startActivityForResult(intent, 201);

            }
        });


        btnMemo = (Button)findViewById(R.id.btnMemo);
        btnMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = (View) View.inflate(AddCal.this, R.layout.page2_memo,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder (AddCal.this);
                dlg.setTitle("메모입력");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인",

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editMemo = (EditText) dialogView.findViewById(R.id.editMemo);

                                tvMemo.setText(editMemo.getText());

                            }
                        }
                ).setNegativeButton("취소", null);
                dlg.show();
            }
        });


        btnAlarm = (Button)findViewById(R.id.btnAlarm);
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] alarmArray =  new String[]{"0","5","10","30","60","1440","10080"};
                final boolean[] checkArray = new boolean[]{false,false,false,false,false,false};

                AlertDialog.Builder dlg = new AlertDialog.Builder (AddCal.this);
                dlg.setTitle("알람설정");

                dlg.setItems(alarmArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // btnRepeat.setText(alarmArray[which].toString());
                       // alarm = Integer.parseInt(alarmArray[which]);
                        alarm = Integer.parseInt(alarmArray[which]);
                        tvselAlarm.setText(alarmArray[which]);
                    }
                });
                dlg.setPositiveButton("닫기", null);

                dlg.show();
            }
        });


        /* 영채야 이거 타임피커로 바꿔줄 수 있겟닝~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~????*/



        btnRepeat = (Button)findViewById(R.id.btnRepeat);
    btnRepeat.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String[] repitArray =  new String[]{"이용안함","매일","매주","매월","매년","주중 매일"};
            final boolean[] checkArray = new boolean[]{false,false,false,false,false,false};

            AlertDialog.Builder dlg = new AlertDialog.Builder (AddCal.this);
            dlg.setTitle("반복설정");

            dlg.setItems(repitArray, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    btnRepeat.setText(repitArray[which]);
                    tvselRepeat.setText(repitArray[which]);
                }
            });
            dlg.setPositiveButton("닫기", null);

            dlg.show();
        }
    });

}


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String resultData = data.getStringExtra("returnData");
        resultLocation = resultData.split("%");

        locationTitle=resultLocation[0];
        locationLongitude=resultLocation[3];
        locationLatitude =resultLocation[4];


        Toast.makeText(getApplicationContext(), resultLocation[0], Toast.LENGTH_LONG).show();


        tvLocationName.setText(locationTitle);


        super.onActivityResult(requestCode, resultCode, data);

    }



    private boolean pushParse() {





        final ParseObject calendar = new ParseObject("appcal");
        //  calendar.setACL(new ParseACL(ParseUser.getCurrentUser()));
        final ParseObject details_cal = new ParseObject("details_cal");
        //  details_cal.setACL(new ParseACL(ParseUser.getCurrentUser()));


        calendar.put("p_num",p_num );
        calendar.put("title",title );
        calendar.put("memo",memo);
         calendar.put("start_year",start_year);
         calendar.put("start_month",start_month) ;
         calendar.put("start_day",start_day);
        calendar.put("start_hour", start_hour);
        calendar.put("start_min", start_min);
        calendar.put("end_year", end_year);
        calendar.put("end_month", end_month);
        calendar.put("end_day",  end_day);
        calendar.put("end_hour",end_hour);
        calendar.put("end_min", end_min);

        calendar.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (null == e) {

                    Toast.makeText(getApplicationContext(), "calendar is pushed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        details_cal.put("c_id", 0);
        details_cal.put("locationtitle", locationTitle);
        details_cal.put("longi",longi);
        details_cal.put("lati", lati);
        details_cal.put("alarm", alarm);
        details_cal.put("repeat",repeat);

        details_cal.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (null == e) {

                    Toast.makeText(getApplicationContext(), "details are pushed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return true;


    }

    private boolean pushDB(){

    db = new CalendarDBHandler(this);

    db.addCalendar(new Calendar(p_num,title,memo,start_year,start_month,start_day,start_hour,start_min,
                        end_year,end_month,end_day,end_hour,end_min));

    db.addDetailCalendar(new Details_cal(lati,longi,alarm,repeat));



       return true;

    }


    private boolean pushCal(){

////////////////////////////////////////////////////////////////////////////////////calendar view 추가하기



        return true;
    }



}
