package com.example.allactivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import DB.CalendarDBHandler;
import DB.CalendarPlus;
import DB.ToDoDBHandler;
import DB.Todo;
import timetablepac.TimeTable;
import timetablepac.TimeTableDBHandler;

/**
 * Created by LG on 2015-11-21.
 */
public class ServiceClass extends Service implements Runnable {

    // 서비스 종료시 재부팅 딜레이 시간, activity의 활성 시간을 벌어야 한다.
    private static final int REBOOT_DELAY_TIMER = 60 * 1000;

    // 주기 시간
    private static final int LOCATION_UPDATE_DELAY = 60 * 1000; // 5 * 60 * 1000
/* 나중에 바꾸기! */

    private CalendarDBHandler db;
    private ToDoDBHandler tdb;
    private TimeTableDBHandler timedb;

    private static final String INTENT_ACTION = "com.example.allactivity";
    private String title;
    private String memo;

    List<CalendarPlus> checks;
    List<Todo> todos;
    List<TimeTable> ttable;

    TelephonyManager telephony;
    SmsManager mSmsManager;

    private static final String TAG = "Phone call";
    private ITelephony telephonyService;

    int flag;
    int tflag;
    int nflag;

    private Handler handler;
    private boolean mIsRunning;
    private int mStartId = 0;

    static final int NOTI_ID = 101;
    NotificationManager mNotiMgr;



    public void onCreate() {

        unregisterRestartAlarm();
        super.onCreate();

        // 알림 관리자를 구해서 멤버변수에 저장
        mNotiMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        mIsRunning = false;
    }

    @Override
    public void onDestroy() {

        // 서비스가 죽었을때 알람 등록
        Log.d("ServiceClass", "onDestroy()");
        registerRestartAlarm();

        super.onDestroy();

        mIsRunning = false;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("PersistentService", "onStart()");
        super.onStart(intent, startId);

        mStartId = startId;

        flag = 1;
        tflag = 1;
        nflag = 1;

        handler = new Handler();
        handler.postDelayed(this, LOCATION_UPDATE_DELAY);
        mIsRunning = true;

        return 0;
    }

    private void SendMessage(String incomingNumber) {


        if(flag == 1) {
            mSmsManager = SmsManager.getDefault();
            mSmsManager.sendTextMessage(incomingNumber, null, "지금은 " + title + " 중이니 전화 받을 수 없습니다.", null, null);
            flag = 0;
        }

    }

    @Override
    public void run() {

        Log.e(TAG, "run()");

        if(!mIsRunning)
        {
            Log.d("PersistentService", "run(), mIsRunning is false");
            Log.d("PersistentService", "run(), alarm service end");
            return;

        } else {

            Log.d("PersistentService", "run(), mIsRunning is true");
            Log.d("PersistentService", "run(), alarm repeat after five minutes");

            function();

            mHandler.postDelayed(this, LOCATION_UPDATE_DELAY);
            mIsRunning = true;
        }

    }

    private void function(){


        db = new CalendarDBHandler(getApplicationContext());
        tdb = new ToDoDBHandler(getApplicationContext());
        timedb = new TimeTableDBHandler(getApplicationContext());

        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        AudioManager mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

       // notificationBar();

        if(today.hour==0 && today.minute==2){

            Log.e("또하루가 갔다네......ㅠ", "" + today.hour);

            if(nflag == 1) {
               notificationBar();
               nflag = 0;
           }

        }

        int s_year ;
        int s_month;
        int s_day;
        String s_all;

        int e_year ;
        int e_month;
        int e_day;
        String e_all;

        Date s_date = null;
        Date e_date = null;
        Date today_date = null;

            Log.e("week",""+today.weekDay);
        if(!(ttable = timedb.getSelectedCTimeTable(today.weekDay)).isEmpty()){

            for(int i=0; i< ttable.size(); i++){
                int checker1 = ttable.get(i).getS_hour() * 60;
                int checker2 = ttable.get(i).getE_hour() * 60;
                int checker3 = ttable.get(i).getS_min();
                int checker4 = ttable.get(i).getE_min();

                int current = today.hour * 60 + today.minute;

                if((checker1 + checker3) <= current && current <= (checker2 + checker4)) {
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
            }


        }


        String todayall = ""+today.year+(today.month+1)+today.monthDay+"";

        if(!(checks = db.Jointables(today.hour)).isEmpty()) {


            Log.e("시간", "" + today.hour);
            Log.e("디비시간", "" + checks.get(0).getS_hour());

            Log.e("분", "" + today.minute);
            Log.e("디비분", "" + checks.get(0).getS_min());

            Log.e("마감 디비시간", "" + checks.get(0).getE_hour());
            Log.e("마감 디비분", "" + checks.get(0).getE_min());

            Log.e("알람", "" + checks.get(0).getD_alarm());


            for (int k = 0; k < checks.size(); k++) {
                s_year = checks.get(k).getS_year();
                e_year = checks.get(k).getE_year();

                s_month = checks.get(k).getS_month();
                e_month = checks.get(k).getE_month();

                s_day = checks.get(k).getS_day();
                e_day = checks.get(k).getE_day();

                s_all = ""+s_year+s_month+s_day+"";
                e_all = ""+e_year+e_month+e_day+"";

                title = checks.get(k).getTitle();
                memo = checks.get(k).getMemo();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                try {
                    s_date = sdf.parse(s_all);
                    e_date = sdf.parse(e_all);
                    today_date = sdf.parse(todayall);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.e("날짜 출력1", "" + s_date.toString());
                Log.e("날짜 출력2", "" + e_date.toString());
                Log.e("날짜 출력3", "" + today_date.toString());

                if (s_date.compareTo(today_date) >= 0 && e_date.compareTo(today_date) <= 0) {
                    if (today.hour == checks.get(k).getS_hour()) {
                        Log.e("여기", "" + today.hour);
                        if ((checks.get(k).getS_min() - today.minute) == checks.get(k).getD_alarm()) {
                            //알람
                            Log.e("여기2", "" + today.minute);

                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    mRunning = true;
                                    while (mRunning) {

                                        if(tflag == 1) {
                                            SystemClock.sleep(1000);
                                            mHandler.sendEmptyMessage(0);
                                            tflag = 0;
                                        }

                                    }
                                }

                            }).start();


                        }
                    }

                    int checker1 = checks.get(k).getS_hour() * 60;
                    int checker2 = checks.get(k).getE_hour() * 60;
                    int checker3 = checks.get(k).getS_min();
                    int checker4 = checks.get(k).getE_min();
                    int current = today.hour * 60 + today.minute;


                    if ((checker1 + checker3) <= current && current <= (checker2 + checker4)) {
                        Log.e("여기로 옵니까", "yes");
                        // 전화 거부해야하는 곳

                        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        // 무음모드 전환

                        Log.e("여기로 옵니까2", "yes2");

                        telephony = (TelephonyManager)
                                getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);



                        telephony.listen(new PhoneStateListener() {
                            @Override
                            public void onCallStateChanged(int state, String incomingNumber) {

                                Log.e("여기로 옵니까3", "yes3");

                                try {
                                    Class c = Class.forName(telephony.getClass().getName());
                                    Method m = c.getDeclaredMethod("getITelephony");
                                    m.setAccessible(true);
                                    telephonyService = (ITelephony) m.invoke(telephony);
                                    telephonyService.endCall();

                                    SendMessage(incomingNumber);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                super.onCallStateChanged(state, incomingNumber);
                            }
                        }, PhoneStateListener.LISTEN_CALL_STATE);



                    } else {

                    }
                }
            }

        }
    }



    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
//               switch(msg.what) {}
//               Toast.makeText(getApplicationContext(), "알림!", 0).show();
            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(ServiceClass.this);

            builder.setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(memo)
                    .setAutoCancel(true) // 알림바에서 자동 삭제
                    .setVibrate(new long[]{1000,2000,1000,3000,1000,4000});

            // autoCancel : 한번 누르면 알림바에서 사라진다.
            // vibrate : 쉬고, 울리고, 쉬고, 울리고... 밀리세컨
            // 진동이 되려면 AndroidManifest.xml에 진동 권한을 줘야 한다.

            // 알람 클릭시 MainActivity를 화면에 띄운다.

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext()
                    , 0
                    , intent
                    , PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pIntent);
            manager.notify(1, builder.build());
        };
    };

    private void registerRestartAlarm() {

        Log.d("PersistentService", "registerRestartAlarm()");

        Intent intent = new Intent(ServiceClass.this, RestartService.class);
        intent.setAction(RestartService.ACTION_RESTART_PERSISTENTSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(ServiceClass.this, 0, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += REBOOT_DELAY_TIMER; // 10초 후에 알람이벤트 발생

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,REBOOT_DELAY_TIMER, sender);
    }


    /**
     * 기존 등록되어있는 알람을 해제한다.
     */
    private void unregisterRestartAlarm() {

        Log.d("PersistentService", "unregisterRestartAlarm()");
        Intent intent = new Intent(ServiceClass.this, RestartService.class);
        intent.setAction(RestartService.ACTION_RESTART_PERSISTENTSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(ServiceClass.this, 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }



    protected boolean mRunning;

    private boolean notificationBar(){


        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setTicker("Notification.Builder");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setContentTitle("오늘의 할일 List");
        mBuilder.setContentText("오늘의 할일을 체크하세요.");
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        Notification.InboxStyle style = new Notification.InboxStyle(mBuilder);
        addBar(style);


        todos = tdb.getSelectedTodos();

        for(int i=0; i <todos.size(); i++){
            style.addLine(todos.get(i).getTitle());
        }

  /*     style.addLine("술래잡기 가기");
        style.addLine("크라켄 가기");
        style.addLine("전화하기");
        style.addLine("종합관12층....");
        style.setSummaryText("오늘의 할일");
*/

        mBuilder.setStyle(style);
        nm.notify(111, mBuilder.build());
        return true;
    }

    private Notification.InboxStyle addBar(Notification.InboxStyle style){
        return style;
    }

}
