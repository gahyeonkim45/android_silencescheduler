package com.example.allactivity.addpac;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allactivity.R;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.todo.TodoListAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import DB.ToDoDBHandler;
import DB.Todo;

/**
 * Created by youngchae on 2015-11-04.
 */
public class AddTodo extends Activity {


    Button btnMemo,btnAlarm,btnRepeat,btnDate;
    EditText editTitle;
    View dialogView;

    CheckBox  chkbar;
    DatePicker datePicker;
    TextView tvYear,tvMonth,tvDayofMonth;

    int start_year=0;
    int start_month=0;
    int start_day =0;
    int end_year =0;
    int end_month =0;
    int end_day =0;

    Date startdate;
    Date enddate;

    String starttemp;
    String endtemp;

    int checked;

    java.sql.Date endddt;
    java.sql.Date startddt;

    ToDoDBHandler db;

  //  TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

     //   adapter = new TodoListAdapter(this);
        db = new ToDoDBHandler(this);

        editTitle = (EditText)findViewById(R.id.editTitle);
        chkbar = (CheckBox)findViewById(R.id.chkbar);
        tvYear = (TextView)findViewById(R.id.tvYear);
        tvMonth = (TextView)findViewById(R.id.tvMonth);
        tvDayofMonth = (TextView)findViewById(R.id.tvDayofMonth);

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


                final ParseObject ToDo = new ParseObject("ToDo");


                ToDo.put("title", editTitle.getText().toString());
                ToDo.put("start date",startdate.toString());
                ToDo.put("end date",enddate.toString());

                ToDo.put("bar",chkbar.isChecked());

                if(chkbar.isChecked()){
                    checked = 1;
                } // check 되면 1 아니면 0

               // adapter.addItem(new Todo(editTitle.getText().toString(),startddt,endddt,checked,0));
               // adapter.notifyDataSetChanged();
                db.addTodo(new Todo(editTitle.getText().toString(),startddt,endddt,checked,0));

                ToDo.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (null == e) {

                            Toast.makeText(getApplicationContext(), "ToDo is pushed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                finish();

            }
        });


        btnDate = (Button)findViewById(R.id.btnDate);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View dialogView = (View) View.inflate(AddTodo.this, R.layout.page2_datepicker,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder (AddTodo.this);
                dlg.setTitle("날짜선택");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인",

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //     dlgEdtStart = (EditText)dialogView.findViewById(R.id.dlgEdtStart);

                                datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                                start_year=datePicker.getYear();
                                start_month=(datePicker.getMonth())+1;
                                start_day=datePicker.getDayOfMonth();

                                end_year=datePicker.getYear();
                                end_month=(datePicker.getMonth())+1;
                                end_day=datePicker.getDayOfMonth();

                                starttemp = ""+start_year+"-"+start_month+"-"+start_day;
                                endtemp = ""+end_year+"-"+end_month+"-"+end_day;

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                                try {


                                    startdate = format.parse(starttemp);
                                    enddate = format.parse(endtemp);

                                    startddt = new java.sql.Date(startdate.getTime());
                                    endddt = new java.sql.Date(enddate.getTime());

                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }


                                tvYear.setText(Integer.toString(datePicker.getYear()));
                                tvMonth.setText(Integer.toString(datePicker.getMonth()));
                                tvDayofMonth.setText(Integer.toString(datePicker.getDayOfMonth()));
                                //  tvStartMinute.setText(Integer.toString(startTIme.getCurrentMinute()));


                            }
                        }
                );
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });







    }





}