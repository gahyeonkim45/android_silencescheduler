package com.example.allactivity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.example.allactivity.addpac.AddCal;
import com.example.allactivity.addpac.AddTodo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.todo.TodoActivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import DB.CalendarDBHandler;
import DB.GoogleCalendar;
import DB.GoogleCalendarDBHandler;
import timetablepac.TimeTableActivity;
import timetablepac.TimeTableDBHandler;
import todopac.List2;

public class MainActivity extends Activity implements OnTimeChangedListener {

    boolean flag ;

    CalendarView monthView;
    CalendarAdapter monthViewAdapter;

    TextView monthText;
    private final int DIALOG_CUSTOM_ID = 1;

    ArrayList<DayData> dayData;

    int curYear;
    int curMonth;
    int curDay;
    String txt ="";
    int curHour;
    int curMin;

    EditText et;
    Button btn1;
    Button refresh;

    ArrayList<String> as;
    View dia;
    AlertDialog mainSectionPopupMenuDialog;

    CalendarDBHandler db;
    GoogleCalendarDBHandler gdb;

    TimeTableDBHandler tdb;

    TelephonyManager telManager;
    String phoneNum;

    GestureDetector mGestureDetector;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    ProgressDialog mProgress;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY };

    ImageView image;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new CalendarDBHandler(getApplicationContext());


     //   db.addCalendar(new Calendar("01092514323","제발","제에에발",2015,11,21,15,22,2015,11,21,15,56));
 //       db.addDetailCalendar(new Details_cal("제발","50","50",1,"월"));

        mOutputText = new TextView(this);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Calling Google Calendar API ...");

        dayData = new ArrayList<DayData>();

        db = new CalendarDBHandler(MainActivity.this);
        tdb = new TimeTableDBHandler(MainActivity.this);
        gdb = new GoogleCalendarDBHandler(this);


        btn1 = (Button)findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.openOptionsMenu();
            }
        });

        refresh = (Button)findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gdb.deleteAllCalendar();
                flag = true;
                onResume();
            }
        });

        telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        phoneNum = telManager.getLine1Number();

        image = (ImageView) findViewById(R.id.image01);
        monthText = (TextView)findViewById(R.id.monthText);


        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {
                try {
                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                        return false;
                    // right to left swipe
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                        monthViewAdapter.setNextMonth();
                        monthViewAdapter.notifyDataSetChanged();
                        setMonthText();
                        // Log.d("right to left","안들어오나");

                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                        monthViewAdapter.setPreviousMonth();
                        monthViewAdapter.notifyDataSetChanged();
                        setMonthText();
                        //Log.d("left to right","안들어오나");
//
                    }else if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                    }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                    }

                } catch (Exception e) {

                }
                return true;

            }
        });

        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));


        SetAll();


    }


    private void SetAll(){

        monthView = (CalendarView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarAdapter(this);
        monthView.setAdapter(monthViewAdapter);

        monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
            public void onDataSelected(AdapterView parent, View v, int position, long id) {

                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);

                curDay = curItem.getDay();
                as = new ArrayList<String>();

                //List<Calendar> calendars = db.getSelectedCalendars(monthViewAdapter.getCurYear(),monthViewAdapter.getCurMonth(),curDay);

                Intent intent = new Intent(MainActivity.this, List2.class);
                intent.putExtra("d", curDay);
                intent.putExtra("y", curYear);
                intent.putExtra("m", curMonth);
                startActivity(intent);

            }
        });

        setMonthText();

    }

    private void setMonthText(){

        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년" + (curMonth+1) + "월");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    int s_hour;
    int s_min;
    int e_hour;
    int e_min;

    public boolean onOptionsItemSelected(MenuItem item){
        int curId = item.getItemId();

        if(curId == R.id.action_settings){
            Intent intent = new Intent(MainActivity.this, AddCal.class);
            startActivity(intent);


        }else if(curId == R.id.weather){

            /*Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent);*/
           // finish();

            Intent intent = new Intent(MainActivity.this, TimeTableActivity.class);
            startActivity(intent);


        }else if(curId == R.id.daylist){

            Intent intent = new Intent(MainActivity.this, TodoActivity.class);
            intent.putExtra("d", curDay);
            intent.putExtra("y", curYear);
            intent.putExtra("m", curMonth);
            startActivity(intent);

            /*Intent intent = new Intent(MainActivity.this, AddTodo.class);
            startActivity(intent);*/
        }


        return true;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        curHour = hourOfDay;
        curMin = minute;
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int i, int i2) {
            s_hour = i;
            s_min = i2;

        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int i, int i2) {
            e_hour = i;
            e_min = i2;

        }
    };

    public boolean dispatchTouchEvent(MotionEvent ev) {

        if(mGestureDetector.onTouchEvent(ev)){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onResume() {

        super.onResume();

        //startService(new Intent("com.example.allactivity"));
        Intent i = new Intent(this, ServiceClass.class);

        receiver = new RestartService();

        try
        {
            // xml에서 정의해도 됨?
            // 이것이 정확히 무슨 기능을 하지는지?
            IntentFilter mainFilter = new IntentFilter("com.example.allactivity");

            // 리시버 저장
            registerReceiver(receiver, mainFilter);

            // 서비스 시작
            startService(i);

        } catch (Exception e) {
            Log.d("MpMainActivity", e.getMessage() + "");
            e.printStackTrace();
        }

        if (isGooglePlayServicesAvailable()) {
            refreshResults();
        } else {
            mOutputText.setText("Google Play Services required: " +
                    "after installing, close and relaunch this app.");
        }


    }

    @Override
    protected void onDestroy() {
        Log.d("MpMainActivity", "Service Destroy");
        unregisterReceiver(receiver);
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    isGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        mCredential.setSelectedAccountName(accountName);
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    mOutputText.setText("Account unspecified.");
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode != RESULT_OK) {
                    chooseAccount();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshResults() {

        if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            if (isDeviceOnline()) {
                new MakeRequestTask(mCredential).execute();

            } else {
                mOutputText.setText("No network connection available.");
            }
        }
    }

    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    private void chooseAccount()     {
        startActivityForResult(
                mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date. Will
     * launch an error dialog for the user to update Google Play Services if
     * possible.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        final int connectionStatusCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        } else if (connectionStatusCode != ConnectionResult.SUCCESS ) {
            return false;
        }
        return true;
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                connectionStatusCode,
                MainActivity.this,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }


    /**
     * An asynchronous task that handles the Google Calendar API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {


            try {
               // startService(new Intent("com.example.allactivity"));
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }

        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws java.io.IOException
         */

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        private List<String> getDataFromApi() throws IOException {

                DateTime now = new DateTime(1950 - 01 - 01);
                List<String> eventStrings = new ArrayList<String>();
                Events events = mService.events().list("primary")
                        //.setMaxResults(10)
                        .setTimeMin(now)
                        .setOrderBy("startTime")
                        .setSingleEvents(true)
                        .execute();
                List<Event> items = events.getItems();

            Log.e("아이템 ", ""+items.size());
            Log.e("캘린더카운트 ", ""+gdb.getCalendarCount());
           // Log.e("flag값 ", ""+flag);

            //flag
            if(( gdb.getCalendarCount() == 0 || items.size() != gdb.getCalendarCount())) {

                gdb.deleteAllCalendar();

                Log.e("아이템 ", ""+items.size());
                Log.e("캘린더카운트 ", ""+gdb.getCalendarCount());

                    for (Event event : items) {
                        DateTime start = event.getStart().getDateTime();
                        DateTime end = event.getEnd().getDateTime();


                        if (start == null) {
                            // All-day events don't have start times, so just use
                            // the start date.
                            start = event.getStart().getDate();
                            end = event.getEnd().getDate();
                        }

                        eventStrings.add(
                                String.format("%s (%s)", event.getSummary(), start));
                        Log.e("start", String.valueOf(start));

                        Date d = null;
                        Date ed = null;

                        String formattedTime = null;
                        String eformattedTime = null;

                        try {
                            d = sdf.parse(String.valueOf(start));
                            ed = sdf.parse(String.valueOf(end));

                            formattedTime = output.format(d);
                            eformattedTime = output.format(ed);

                            Log.e("formattedTime", formattedTime);
                            Log.e("eformattedTime", eformattedTime);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String year = formattedTime.substring(0, 4);
                        String month = formattedTime.substring(5, 7);
                        String day = formattedTime.substring(8, 10);
                        String hour = formattedTime.substring(11, 13);
                        String min = formattedTime.substring(14, 16);

                        String eyear = eformattedTime.substring(0, 4);
                        String emonth = eformattedTime.substring(5, 7);
                        String eday = eformattedTime.substring(8, 10);
                        String ehour = eformattedTime.substring(11, 13);
                        String emin = eformattedTime.substring(14, 16);

                        Log.e("year", year);
                        Log.e("month", month);
                        Log.e("day", day);
                        Log.e("hour", hour);
                        Log.e("min", min);

                        Log.e("eyear", eyear);
                        Log.e("emonth", emonth);
                        Log.e("eday", eday);
                        Log.e("ehour", ehour);
                        Log.e("emin", emin);

                        gdb.addCalendar(new GoogleCalendar(phoneNum, event.getSummary(), event.getSummary(), Integer.parseInt(year),
                                Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(min),
                                Integer.parseInt(eyear),
                                Integer.parseInt(emonth), Integer.parseInt(eday), Integer.parseInt(ehour), Integer.parseInt(emin)));
                        //ddb.addDetailCalendar();


                    }

                flag = false;

                return eventStrings;

           }

            return null;
        }


        @Override
        protected void onPreExecute() {
            mOutputText.setText("");

        }

        @Override
        protected void onPostExecute(List<String> output) {
            mProgress.hide();
            if (output == null || output.size() == 0) {
                mOutputText.setText("No results returned.");
            } else {
                output.add(0, "Data retrieved using the Google Calendar API:");
                mOutputText.setText(TextUtils.join("\n", output));
                onResume();
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            MainActivity.REQUEST_AUTHORIZATION);
                } else {
                    mOutputText.setText("The following error occurred:\n"
                            + mLastError.getMessage());
                }
            } else {
                mOutputText.setText("Request cancelled.");
            }
        }
    }
}
