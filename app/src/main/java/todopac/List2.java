package todopac;

/**
 * Created by LG on 2015-11-19.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;


import com.example.allactivity.OnDataSelectionListener;
import com.example.allactivity.R;
import com.example.allactivity.WeatherActivity;

import java.util.List;

import DB.Calendar;
import DB.CalendarDBHandler;
import DB.CalendarPlus;
import DB.GoogleCalendar;
import DB.GoogleCalendarDBHandler;

public class List2 extends Activity {
    DataListView2 list;
    public static int yy = 5;
    public static int mm = 5;
    public static int dd = 31;
    public static int tt = 24;
    public static int pp = 5;

    AlertDialog mainSectionPopupMenuDialog;
    View dia;

    /**
     * 어댑터 객체
    */

    ListAdapter2 adapter;
    CalendarDBHandler db;
    GoogleCalendarDBHandler gdb;
    List<Calendar> lists;
    List<GoogleCalendar> glists;

    String[] resultdata;
    String[] selectdata;
    GoogleCalendar re;
    Calendar re2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new CalendarDBHandler(this);
        gdb = new GoogleCalendarDBHandler(this);

        int ttt = 0;
        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos", 0);
        int d = intent.getIntExtra("d", 0);
        int y = intent.getIntExtra("y", 0);
        int m = intent.getIntExtra("m", 0);
        String k = y + "년" + (m + 1) + "월" + d + "일";
        // 타이틀 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 리스트뷰 객체 생성
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        list = new DataListView2(this);

        // 어댑터 객체 생성
        adapter = new ListAdapter2(this);

        // 아이템 데이터 만들기

        adapter.addItem(new Item2(k, "일정목록", "",y,m,d));

        // 리스트뷰에 어댑터 설정
        list.setAdapter(adapter);

        resultdata = new String[3];
        resultdata[0] = "수정";
        resultdata[1] = "삭제";
        resultdata[2] = "날씨";

        selectdata = new String[4];
        selectdata[0] = "title";
        selectdata[1] = "memo";
        selectdata[2] = "start time";
        selectdata[3] = "end time";

/*Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent);*/

        // 새로 정의한 리스너로 객체를 만들어 설정
        list.setOnDataSelectionListener(new OnDataSelectionListener() {
            public void onDataSelected(AdapterView parent, View v,
                                       int position, long id) {

                final Item2 curItem = (Item2) adapter.getItem(position);

                re2 = db.getSelectedTitle(curItem.getData(0));
                re = gdb.getSelectedTitle(curItem.getData(0));

                mainSectionPopupMenuDialog = null;

                final AlertDialog.Builder builder = new AlertDialog.Builder(List2.this);
                dia = View.inflate(List2.this, R.layout.dia, null);

                builder.setTitle("Select One")
                        .setItems(resultdata, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedIndex) {
                                if (selectedIndex == 0) {

                                    if (re2 != null) {

                                        new AlertDialog.Builder(List2.this)
                                                .setTitle("수정하고 싶은 것을 선택하세요")
                                                .setItems(selectdata, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        if (i == 0) {

                                                            AlertDialog.Builder alert = new AlertDialog.Builder(List2.this);

                                                            alert.setTitle("Title");
                                                            alert.setMessage("Message");

                                                            // Set an EditText view to get user input
                                                            final EditText input = new EditText(List2.this);
                                                            alert.setView(input);

                                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                                    String value = input.getText().toString();
                                                                    value.toString();

                                                                    re2.setTitle(value);
                                                                    db.updateCalendar(re2);
                                                                    // Do something with value!
                                                                    mainSectionPopupMenuDialog.dismiss();

                                                                }
                                                            });

                                                            alert.setNegativeButton("Cancel",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                                            mainSectionPopupMenuDialog.dismiss();
                                                                        }
                                                                    });

                                                            alert.show();


                                                            // title 변경
                                                        } else if (i == 1) {

                                                            AlertDialog.Builder alert = new AlertDialog.Builder(List2.this);

                                                            alert.setTitle("Title");
                                                            alert.setMessage("Message");

                                                            // Set an EditText view to get user input
                                                            final EditText input = new EditText(List2.this);
                                                            alert.setView(input);

                                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                                    String value = input.getText().toString();
                                                                    value.toString();

                                                                    re2.setMemo(value);
                                                                    db.updateCalendar(re2);
                                                                    // Do something with value!


                                                                    mainSectionPopupMenuDialog.dismiss();

                                                                }
                                                            });

                                                            alert.setNegativeButton("Cancel",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                                            mainSectionPopupMenuDialog.dismiss();

                                                                        }
                                                                    });

                                                            alert.show();

                                                        } else if (i == 2) {

                                                            AlertDialog.Builder alert = new AlertDialog.Builder(List2.this);

                                                            alert.setTitle("Title");
                                                            alert.setMessage("Message");

                                                            // Set an EditText view to get user input

                                                            final TimePicker tp = new TimePicker(List2.this);
                                                            alert.setView(tp);

                                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                                    int hour = tp.getCurrentHour();
                                                                    int min = tp.getCurrentMinute();

                                                                    Log.e("hour",""+hour);
                                                                    Log.e("hour",""+min);

                                                                    re2.setS_hour(hour);
                                                                    re2.setS_min(min);

                                                                    db.updateCalendar(re2);
                                                                    // Do something with value!

                                                                    mainSectionPopupMenuDialog.dismiss();

                                                                }
                                                            });

                                                            alert.setNegativeButton("Cancel",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {

                                                                            mainSectionPopupMenuDialog.dismiss();

                                                                        }
                                                                    });

                                                            alert.show();

                                                        } else if (i == 3) {

                                                            AlertDialog.Builder alert = new AlertDialog.Builder(List2.this);

                                                            alert.setTitle("Title");
                                                            alert.setMessage("Message");

                                                            // Set an EditText view to get user input

                                                            final TimePicker tp = new TimePicker(List2.this);
                                                            alert.setView(tp);

                                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                                    int hour = tp.getCurrentHour();
                                                                    int min = tp.getCurrentMinute();

                                                                    Log.e("hour",""+hour);
                                                                    Log.e("hour",""+min);

                                                                    re2.setE_hour(hour);
                                                                    re2.setE_min(min);

                                                                    db.updateCalendar(re2);
                                                                    // Do something with value!
                                                                    mainSectionPopupMenuDialog.dismiss();

                                                                }
                                                            });

                                                            alert.setNegativeButton("Cancel",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                                            mainSectionPopupMenuDialog.dismiss();

                                                                        }
                                                                    });

                                                            alert.show();
                                                        }
                                                    }
                                                });

                                        mainSectionPopupMenuDialog = builder.create();
                                        mainSectionPopupMenuDialog.show();


                                    }

                                    if (re != null) {

                                        mainSectionPopupMenuDialog = null;

                                        final AlertDialog.Builder builder = new AlertDialog.Builder(List2.this);
                                        dia = View.inflate(List2.this, R.layout.dia, null);


                                        builder.setTitle("수정하고 싶은 것을 선택하세요")
                                                .setItems(selectdata, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        if (i == 0) {

                                                            AlertDialog.Builder alert = new AlertDialog.Builder(List2.this);

                                                            alert.setTitle("Title");
                                                            alert.setMessage("Message");

                                                            // Set an EditText view to get user input
                                                            final EditText input = new EditText(List2.this);
                                                            alert.setView(input);

                                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                                    String value = input.getText().toString();
                                                                    value.toString();

                                                                    re.setTitle(value);
                                                                    gdb.updateCalendar(re);
                                                                    // Do something with value!
                                                                    mainSectionPopupMenuDialog.dismiss();

                                                                }
                                                            });

                                                            alert.setNegativeButton("Cancel",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                                            mainSectionPopupMenuDialog.dismiss();

                                                                        }
                                                                    });

                                                            alert.show();


                                                            // title 변경
                                                        } else if (i == 1) {

                                                            AlertDialog.Builder alert = new AlertDialog.Builder(List2.this);

                                                            alert.setTitle("Title");
                                                            alert.setMessage("Message");

                                                            // Set an EditText view to get user input
                                                            final EditText input = new EditText(List2.this);
                                                            alert.setView(input);

                                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                                    String value = input.getText().toString();
                                                                    value.toString();

                                                                    re.setMemo(value);
                                                                    gdb.updateCalendar(re);
                                                                    // Do something with value!


                                                                    mainSectionPopupMenuDialog.dismiss();

                                                                }
                                                            });

                                                            alert.setNegativeButton("Cancel",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                                            mainSectionPopupMenuDialog.dismiss();

                                                                        }
                                                                    });

                                                            alert.show();

                                                        } else if (i == 2) {

                                                            AlertDialog.Builder alert = new AlertDialog.Builder(List2.this);

                                                            alert.setTitle("Title");
                                                            alert.setMessage("Message");

                                                            // Set an EditText view to get user input

                                                            final TimePicker tp = new TimePicker(List2.this);
                                                            alert.setView(tp);

                                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                                    int hour = tp.getCurrentHour();
                                                                    int min = tp.getCurrentMinute();

                                                                    re.setS_hour(hour);
                                                                    re.setS_min(min);

                                                                    gdb.updateCalendar(re);
                                                                    // Do something with value!

                                                                    mainSectionPopupMenuDialog.dismiss();

                                                                }
                                                            });

                                                            alert.setNegativeButton("Cancel",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {

                                                                            mainSectionPopupMenuDialog.dismiss();

                                                                        }
                                                                    });

                                                            alert.show();

                                                        } else if (i == 3) {

                                                            AlertDialog.Builder alert = new AlertDialog.Builder(List2.this);

                                                            alert.setTitle("Title");
                                                            alert.setMessage("Message");

                                                            // Set an EditText view to get user input

                                                            final TimePicker tp = new TimePicker(List2.this);
                                                            alert.setView(tp);

                                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                                    int hour = tp.getCurrentHour();
                                                                    int min = tp.getCurrentMinute();

                                                                    re.setE_hour(hour);
                                                                    re.setE_min(min);

                                                                    gdb.updateCalendar(re);
                                                                    // Do something with value!
                                                                    mainSectionPopupMenuDialog.dismiss();

                                                                }
                                                            });

                                                            alert.setNegativeButton("Cancel",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                                            mainSectionPopupMenuDialog.dismiss();

                                                                        }
                                                                    });

                                                            alert.show();
                                                        }
                                                    }
                                                });

                                        mainSectionPopupMenuDialog = builder.create();
                                        mainSectionPopupMenuDialog.show();

                                    }

                                } else if (selectedIndex == 1) {

                                    if (re2 != null) {
                                        db.deleteCalendar(re2);

                                    }

                                    if (re != null) {
                                        gdb.deleteCalendar(re);

                                    }

                                } else if ( selectedIndex == 2){

                                    if(re2 != null){

                                        Intent i = new Intent(List2.this, WeatherActivity.class);
                                        CalendarPlus plus = db.joinweather(re2.getC_id());

                                        i.putExtra("x좌표",Double.parseDouble(plus.getD_lati()));
                                        i.putExtra("y좌표",Double.parseDouble(plus.getD_longi()));

                                        Log.e("x좌표",plus.getD_lati());
                                        Log.e("y좌표",plus.getD_longi());

                                        startActivity(i);
                                        finish();

                                    }else{

                                    }

                                }
                            }
                        });

                mainSectionPopupMenuDialog = builder.create();
                mainSectionPopupMenuDialog.show();


            }
        });

/*화면을 리스트뷰로 채우는 곳입니다.
for문을 사용하여 해당날짜의 모든 데이터를 표시합니다.*/

        setContentView(list, params);

        lists = db.getSelectedCalendars(y,m,d);
        glists = gdb.getSelectedCalendars(y,m,d);

        for (ttt = 0; ttt < 24; ttt++) {
            adapter.addItem(new Item2("","", (ttt + 1) + "시",2015,11,8));
        }

        for(int i = 0; i< lists.size(); i++ ) {

            for (ttt = 0; ttt < 24; ttt++) {
                if(lists.get(i).getS_hour() == (ttt)) {
                    for(int s = lists.get(i).getS_hour(); s <= lists.get(i).getE_hour(); s++) {
                        adapter.updateItem(new Item2(lists.get(i).getTitle(),lists.get(i).getMemo(), s + "시", lists.get(i).getS_year(),lists.get(i).getS_month(), lists.get(i).getS_day()),s);
                    }
               }
           }
        }

        for(int i = 0; i< glists.size(); i++ ) {

            for (ttt = 0; ttt < 24; ttt++) {
                if(glists.get(i).getS_hour() == (ttt+1)) {
                    for(int s = glists.get(i).getS_hour(); s <= glists.get(i).getE_hour(); s++) {
                        adapter.updateItem(new Item2(glists.get(i).getTitle(),glists.get(i).getMemo(), s + "시", glists.get(i).getS_year(),glists.get(i).getS_month(), glists.get(i).getS_day()),s);
                    }
                }else if(ttt==0 && glists.get(i).getS_hour() == 0){
                    for(int s = glists.get(i).getS_hour(); s <= glists.get(i).getE_hour(); s++) {
                        adapter.updateItem(new Item2(glists.get(i).getTitle(),glists.get(i).getMemo(), (s+1) + "시", glists.get(i).getS_year(),glists.get(i).getS_month(), glists.get(i).getS_day()),(s+1));
                    }
                }

            }
        }


    }

    public void ReloadView(){

    }
    /*밑의 두 메소드는 메인 클래스에서도 설명을 하였습니다.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(getBaseContext(), ListView.class);
                startActivity(intent);
                finish();
                break;
           /* case 2:
                Intent intent1 = new Intent(getBaseContext(), Make.class);
                startActivity(intent1);
                break;*/
        }
        return true;
    }
}