package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2015-11-05.
 */
public class CalendarDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "calendars";

    private static final String TABLE_CONTACTS = "calendars";

    private static final String KEY_C_ID = "c_id";
    private static final String KEY_P_NUM = "p_num";
    private static final String KEY_TITLE = "title";
    private static final String KEY_MEMO = "memo";
    private static final String KEY_S_YEAR = "s_year";
    private static final String KEY_S_MONTH = "s_month";
    private static final String KEY_S_DAY = "s_day";
    private static final String KEY_S_HOUR = "s_hour";
    private static final String KEY_S_MIN = "s_min";
    private static final String KEY_E_YEAR = "e_year";
    private static final String KEY_E_MONTH = "e_month";
    private static final String KEY_E_DAY = "e_day";
    private static final String KEY_E_HOUR = "e_hour";
    private static final String KEY_E_MIN = "e_min";

    /* 디테일!!!!*/


    private static final String TABLE_CONTACTS_2 = "details";

    private static final String KEY_C_ID_2 = "c_id";
    private static final String KEY_LATI_2 = "lati";
    private static final String KEY_LONGI_2 = "longi";
    private static final String KEY_ALARM_2 = "alarm";
    private static final String KEY_REPEAT_2 = "repeat";


    public CalendarDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_P_NUM + " TEXT,"
                +KEY_TITLE+" TEXT,"+KEY_MEMO+" TEXT,"+ KEY_S_YEAR + " INTEGER,"
                +KEY_S_MONTH+" INTEGER,"+KEY_S_DAY+" INTEGER,"+KEY_S_HOUR+" INTEGER,"
                +KEY_S_MIN+" INTEGER,"+ KEY_E_YEAR + " INTEGER,"
                +KEY_E_MONTH+" INTEGER,"+KEY_E_DAY+" INTEGER,"+KEY_E_HOUR+" INTEGER,"
                +KEY_E_MIN+" INTEGER"+")";

        String CREATE_CONTACTS_TABLE2 = "CREATE TABLE " + TABLE_CONTACTS_2 + "("
                + KEY_C_ID_2 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +KEY_LATI_2+" TEXT,"+ KEY_LONGI_2 + " TEXT,"
                +KEY_ALARM_2+" INTEGER,"+KEY_REPEAT_2+" TEXT )";
        //+" FOREIGN KEY ("+KEY_C_ID+") REFERENCES calendars(c_id) )";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public// Adding new calendar
    void addCalendar(Calendar calendar) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_C_ID, calendar.c_id);

        values.put(KEY_P_NUM, calendar.p_num);
        values.put(KEY_TITLE, calendar.title);
        values.put(KEY_MEMO, calendar.memo);

        values.put(KEY_S_YEAR, calendar.s_year);
        values.put(KEY_S_MONTH, calendar.s_month);
        values.put(KEY_S_DAY, calendar.s_day);
        values.put(KEY_S_HOUR, calendar.s_hour);
        values.put(KEY_S_MIN, calendar.s_min);

        values.put(KEY_E_YEAR, calendar.e_year);
        values.put(KEY_E_MONTH, calendar.e_month);
        values.put(KEY_E_DAY, calendar.e_day);
        values.put(KEY_E_HOUR, calendar.e_hour);
        values.put(KEY_E_MIN, calendar.e_min);

        Log.e("DBHANDLER",""+calendar.p_num);

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public int updateCalendar(Calendar calendar) {

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM calendars WHERE c_id = '"+calendar.c_id+"'";

        Log.d("update query: ",selectQuery);
        db.rawQuery(selectQuery, null);

        ContentValues values = new ContentValues();
        //values.put(KEY_C_ID, calendar.c_id);

        values.put(KEY_P_NUM, calendar.p_num);
        values.put(KEY_TITLE, calendar.title);
        values.put(KEY_MEMO, calendar.memo);

        values.put(KEY_S_YEAR, calendar.s_year);
        values.put(KEY_S_MONTH, calendar.s_month);
        values.put(KEY_S_DAY, calendar.s_day);
        values.put(KEY_S_HOUR, calendar.s_hour);
        values.put(KEY_S_MIN, calendar.s_min);

        values.put(KEY_E_YEAR, calendar.e_year);
        values.put(KEY_E_MONTH, calendar.e_month);
        values.put(KEY_E_DAY, calendar.e_day);
        values.put(KEY_E_HOUR, calendar.e_hour);
        values.put(KEY_E_MIN, calendar.e_min);

        return db.update(TABLE_CONTACTS,values,calendar.c_id+"= '"+calendar.c_id+"'",null);

/*        Calendar calendar = new Calendar();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_C_ID,
                        KEY_P_NUM,KEY_TITLE,KEY_MEMO, KEY_S_YEAR,
                        KEY_S_MONTH,KEY_S_DAY,KEY_S_HOUR,KEY_S_MIN,
                        KEY_E_YEAR,KEY_E_MONTH,KEY_E_DAY,KEY_E_HOUR,KEY_E_MIN}, KEY_C_ID + "=?",
                new String[]{String.valueOf(c_id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

            calendar = new Calendar(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),//p_num
                    cursor.getString(2), cursor.getString(3),//title,memo
                    Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),//s_year,month,day
                    Integer.parseInt(cursor.getString(7)),Integer.parseInt(cursor.getString(8)),//s_hour,min
                    Integer.parseInt(cursor.getString(9)),Integer.parseInt(cursor.getString(10)),Integer.parseInt(cursor.getString(11)),
                    Integer.parseInt(cursor.getString(12)),Integer.parseInt(cursor.getString(13)));

        return calendar;*/

    }

    public void deleteCalendar(Calendar calendar) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_C_ID + " = ?",
                new String[]{String.valueOf(calendar.getC_id())});
        db.close();
    }


    public List<Calendar> getSelectedCalendars(int year,int month,int day) {
        List<Calendar> calendarList = new ArrayList<Calendar>();
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM calendars WHERE s_year="+year+" and s_month="+(month+1)+" and s_day="+day;

        //String countQuery = "SELECT * FROM calendars WHERE s_year=2015 and s_month=11 and s_day=5";

        //Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);

        if (cursor.moveToFirst()) {
            do {
                   Log.e("여기까지","o");
                    Calendar calendar = new Calendar();
                    calendar.setC_num(Integer.parseInt(cursor.getString(0)));
                    calendar.setP_num(cursor.getString(1));
                    calendar.setTitle(cursor.getString(2));
                    calendar.setMemo(cursor.getString(3));
                    calendar.setS_year(Integer.parseInt(cursor.getString(4)));
                    calendar.setS_month(Integer.parseInt(cursor.getString(5)));
                    calendar.setS_day(Integer.parseInt(cursor.getString(6)));
                    calendar.setS_hour(Integer.parseInt(cursor.getString(7)));
                    calendar.setS_min(Integer.parseInt(cursor.getString(8)));
                    calendar.setE_year(Integer.parseInt(cursor.getString(9)));
                    calendar.setE_month(Integer.parseInt(cursor.getString(10)));
                    calendar.setE_day(Integer.parseInt(cursor.getString(11)));
                    calendar.setE_hour(Integer.parseInt(cursor.getString(12)));
                    calendar.setE_min(Integer.parseInt(cursor.getString(13)));

                    calendarList.add(calendar);

            } while (cursor.moveToNext());

            db.close();
            cursor.close();

        }
        return calendarList;
    }


    public List<Calendar> getSelectMonth(int year,int month) {
        List<Calendar> calendarList = new ArrayList<Calendar>();
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM calendars WHERE s_year="+year+" and s_month="+(month+1);

        //String countQuery = "SELECT * FROM calendars WHERE s_year=2015 and s_month=11 and s_day=5";

        Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);

        if (cursor.moveToFirst()) {
            do {
                Log.e("여기까지","o");
                Calendar calendar = new Calendar();
                calendar.setC_num(Integer.parseInt(cursor.getString(0)));
                calendar.setP_num(cursor.getString(1));
                calendar.setTitle(cursor.getString(2));
                calendar.setMemo(cursor.getString(3));
                calendar.setS_year(Integer.parseInt(cursor.getString(4)));
                calendar.setS_month(Integer.parseInt(cursor.getString(5)));
                calendar.setS_day(Integer.parseInt(cursor.getString(6)));
                calendar.setS_hour(Integer.parseInt(cursor.getString(7)));
                calendar.setS_min(Integer.parseInt(cursor.getString(8)));
                calendar.setE_year(Integer.parseInt(cursor.getString(9)));
                calendar.setE_month(Integer.parseInt(cursor.getString(10)));
                calendar.setE_day(Integer.parseInt(cursor.getString(11)));
                calendar.setE_hour(Integer.parseInt(cursor.getString(12)));
                calendar.setE_min(Integer.parseInt(cursor.getString(13)));

                calendarList.add(calendar);

            } while (cursor.moveToNext());

            db.close();
            cursor.close();

        }
        return calendarList;
    }

    public Calendar getSelectedTitle(String s) {
        Calendar calendarList = new Calendar();
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM calendars WHERE title = '"+ s +"'";

        //String countQuery = "SELECT * FROM calendars WHERE s_year=2015 and s_month=11 and s_day=5";

        Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);
        Log.v("제발",""+cursor.getCount());

        if (cursor.moveToFirst()) {

            Calendar calendar = new Calendar();
            calendar.setC_num(Integer.parseInt(cursor.getString(0)));
            calendar.setP_num(cursor.getString(1));
            calendar.setTitle(cursor.getString(2));
            calendar.setMemo(cursor.getString(3));
            calendar.setS_year(Integer.parseInt(cursor.getString(4)));
            calendar.setS_month(Integer.parseInt(cursor.getString(5)));
            calendar.setS_day(Integer.parseInt(cursor.getString(6)));
            calendar.setS_hour(Integer.parseInt(cursor.getString(7)));
            calendar.setS_min(Integer.parseInt(cursor.getString(8)));
            calendar.setE_year(Integer.parseInt(cursor.getString(9)));
            calendar.setE_month(Integer.parseInt(cursor.getString(10)));
            calendar.setE_day(Integer.parseInt(cursor.getString(11)));
            calendar.setE_hour(Integer.parseInt(cursor.getString(12)));
            calendar.setE_min(Integer.parseInt(cursor.getString(13)));

            calendarList = calendar;
        }

        db.close();
        cursor.close();

        return calendarList;
    }




    public List<CalendarPlus> Jointables(int hour) {
        List<CalendarPlus> calendarList = new ArrayList<CalendarPlus>();
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM calendars a INNER JOIN details b ON a.c_id=b.c_id where a.s_hour ='"+hour+"' or a.e_hour = '"+hour+"' and b.alarm != 999";

        //String countQuery = "SELECT * FROM calendars WHERE s_year=2015 and s_month=11 and s_day=5";

        Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);

        if (cursor.moveToFirst()) {
            do {
                Log.e("여기까지","o");

                CalendarPlus calendar = new CalendarPlus();
                calendar.setC_id(Integer.parseInt(cursor.getString(0)));
                calendar.setP_num(cursor.getString(1));
                calendar.setTitle(cursor.getString(2));
                calendar.setMemo(cursor.getString(3));
                calendar.setS_year(Integer.parseInt(cursor.getString(4)));
                calendar.setS_month(Integer.parseInt(cursor.getString(5)));
                calendar.setS_day(Integer.parseInt(cursor.getString(6)));
                calendar.setS_hour(Integer.parseInt(cursor.getString(7)));
                calendar.setS_min(Integer.parseInt(cursor.getString(8)));
                calendar.setE_year(Integer.parseInt(cursor.getString(9)));
                calendar.setE_month(Integer.parseInt(cursor.getString(10)));
                calendar.setE_day(Integer.parseInt(cursor.getString(11)));
                calendar.setE_hour(Integer.parseInt(cursor.getString(12)));
                calendar.setE_min(Integer.parseInt(cursor.getString(13)));

                Log.v("ss",cursor.getString(1));
                Log.v("ss",cursor.getString(2));
                Log.v("ss",cursor.getString(3));
                Log.v("ss",cursor.getString(4));
                Log.v("ss",cursor.getString(5));
                Log.v("ss",cursor.getString(6));
                Log.v("ss",cursor.getString(7));
                Log.v("ss",cursor.getString(8));
                Log.v("ss",cursor.getString(9));
                Log.v("ss",cursor.getString(10));
                Log.v("ss",cursor.getString(11));
                Log.v("ss",cursor.getString(12));
                Log.v("ss",cursor.getString(13));
                Log.v("ss",cursor.getString(14));
                Log.v("ss",cursor.getString(15));
                Log.v("ss",cursor.getString(16));
                Log.v("ss",cursor.getString(17));
                Log.v("ss",cursor.getString(18));

                calendar.setD_lati(cursor.getString(15));
                calendar.setD_longi(cursor.getString(16));
                calendar.setD_alarm(Integer.parseInt(cursor.getString(17)));
                calendar.setD_repeat(cursor.getString(18));

                calendarList.add(calendar);

            } while (cursor.moveToNext());

            db.close();
            cursor.close();



        }
        return calendarList;
    }


    public CalendarPlus joinweather(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM calendars a INNER JOIN details b ON a.c_id=b.c_id where a.c_id = '"+id+"'";

        //String countQuery = "SELECT * FROM calendars WHERE s_year=2015 and s_month=11 and s_day=5";

        Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);

        if (cursor.moveToFirst()) {

            CalendarPlus calendar = new CalendarPlus();
            calendar.setC_id(Integer.parseInt(cursor.getString(0)));
            calendar.setP_num(cursor.getString(1));
            calendar.setTitle(cursor.getString(2));
            calendar.setMemo(cursor.getString(3));
            calendar.setS_year(Integer.parseInt(cursor.getString(4)));
            calendar.setS_month(Integer.parseInt(cursor.getString(5)));
            calendar.setS_day(Integer.parseInt(cursor.getString(6)));
            calendar.setS_hour(Integer.parseInt(cursor.getString(7)));
            calendar.setS_min(Integer.parseInt(cursor.getString(8)));
            calendar.setE_year(Integer.parseInt(cursor.getString(9)));
            calendar.setE_month(Integer.parseInt(cursor.getString(10)));
            calendar.setE_day(Integer.parseInt(cursor.getString(11)));
            calendar.setE_hour(Integer.parseInt(cursor.getString(12)));
            calendar.setE_min(Integer.parseInt(cursor.getString(13)));

            Log.v("ss", cursor.getString(1));
            Log.v("ss", cursor.getString(2));
            Log.v("ss", cursor.getString(3));
            Log.v("ss", cursor.getString(4));
            Log.v("ss", cursor.getString(5));
            Log.v("ss", cursor.getString(6));
            Log.v("ss", cursor.getString(7));
            Log.v("ss", cursor.getString(8));
            Log.v("ss", cursor.getString(9));
            Log.v("ss", cursor.getString(10));
            Log.v("ss", cursor.getString(11));
            Log.v("ss", cursor.getString(12));
            Log.v("ss", cursor.getString(13));
            Log.v("ss", cursor.getString(14));
            Log.v("ss", cursor.getString(15));
            Log.v("ss", cursor.getString(16));
            Log.v("ss", cursor.getString(17));
            Log.v("ss", cursor.getString(18));

            calendar.setD_lati(cursor.getString(15));
            calendar.setD_longi(cursor.getString(16));
            calendar.setD_alarm(Integer.parseInt(cursor.getString(17)));
            calendar.setD_repeat(cursor.getString(18));

            db.close();
            cursor.close();

            return calendar;
        }


        return null;
    }


    /* detail 부분 */
    ///////////////////////////////

    public void addDetailCalendar(Details_cal calendar) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_C_ID, calendar.c_id);

        //values.put(KEY_C_ID, calendar.c_id);
        values.put(KEY_LATI_2, calendar.lati);
        values.put(KEY_LONGI_2, calendar.longi);
        values.put(KEY_ALARM_2, calendar.alarm);
        values.put(KEY_REPEAT_2, calendar.repeat);

        Log.e("DBHANDLER", "" + calendar.lati);

        db.insert(TABLE_CONTACTS_2, null, values);

        db.close();
    }

    public int updateDetailCalendar(Details_cal calendar) {

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM details WHERE c_id = '"+calendar.c_id+"'";

        Log.d("update query: ",selectQuery);
        db.rawQuery(selectQuery, null);

        ContentValues values = new ContentValues();

        values.put(KEY_C_ID_2, calendar.c_id);
        values.put(KEY_LATI_2, calendar.lati);
        values.put(KEY_LONGI_2, calendar.longi);
        values.put(KEY_ALARM_2, calendar.alarm);
        values.put(KEY_REPEAT_2, calendar.repeat);

        return db.update(TABLE_CONTACTS_2,values,calendar.c_id+"= '"+calendar.c_id+"'",null);

    }

    public void deleteDetailCalendar(Details_cal calendar) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS_2, KEY_C_ID_2 + " = ?",
                new String[]{String.valueOf(calendar.getC_id())});
        db.close();
    }

    public List<Details_cal> getSelectedDetailedCalendars(int year,int month,int day) {

        List<Details_cal> calendarList = new ArrayList<Details_cal>();
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM details WHERE s_year="+year+" and s_month="+(month+1)+" and s_day="+day;

        Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);

        if (cursor.moveToFirst()) {
            do {
                Log.e("여기까지","o");

                Details_cal calendar = new Details_cal();
                calendar.setC_id(Integer.parseInt(cursor.getString(0)));
                calendar.setLati(cursor.getString(1));
                calendar.setLongi(cursor.getString(2));
                calendar.setAlarm(Integer.parseInt(cursor.getString(3)));
                calendar.setRepeat(cursor.getString(4));

                calendarList.add(calendar);

            } while (cursor.moveToNext());

            db.close();

            cursor.close();

        }
        return calendarList;
    }
}
