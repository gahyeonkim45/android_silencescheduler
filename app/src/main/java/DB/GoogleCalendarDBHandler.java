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
public class GoogleCalendarDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "googlecalendars";

    private static final String TABLE_CONTACTS = "googlecalendars";

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

    public GoogleCalendarDBHandler(Context context) {
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
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);


        onCreate(db);
    }

    public// Adding new calendar
    void addCalendar(GoogleCalendar calendar) {
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

        Log.e("DBHANDLER",""+calendar.s_hour+","+calendar.e_hour);

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public int updateCalendar(GoogleCalendar calendar) {

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM googlecalendars WHERE c_id = '"+calendar.c_id+"'";

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

        return db.update(TABLE_CONTACTS,values,KEY_C_ID+"= '"+calendar.c_id+"'",null);

      /*  GoogleCalendar calendar2 = new GoogleCalendar();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_C_ID,
                        KEY_P_NUM,KEY_TITLE,KEY_MEMO, KEY_S_YEAR,
                        KEY_S_MONTH,KEY_S_DAY,KEY_S_HOUR,KEY_S_MIN,
                        KEY_E_YEAR,KEY_E_MONTH,KEY_E_DAY,KEY_E_HOUR,KEY_E_MIN}, KEY_C_ID + "=?",
                new String[]{String.valueOf(calendar2.c_id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

            calendar2 = new GoogleCalendar(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),//p_num
                    cursor.getString(2), cursor.getString(3),//title,memo
                    Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),//s_year,month,day
                    Integer.parseInt(cursor.getString(7)),Integer.parseInt(cursor.getString(8)),//s_hour,min
                    Integer.parseInt(cursor.getString(9)),Integer.parseInt(cursor.getString(10)),Integer.parseInt(cursor.getString(11)),
                    Integer.parseInt(cursor.getString(12)),Integer.parseInt(cursor.getString(13)));

        return 0;*/

    }

    public void deleteCalendar(GoogleCalendar calendar) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_C_ID + " = ?",
                new String[]{String.valueOf(calendar.getC_id())});
        db.close();
    }

    public void deleteAllCalendar() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,null,null);
        db.close();
    }

    public List<GoogleCalendar> getSelectedCalendars(int year,int month,int day) {
        List<GoogleCalendar> calendarList = new ArrayList<GoogleCalendar>();
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM googlecalendars WHERE s_year="+year+" and s_month="+(month+1)+" and s_day="+day;

        //Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);

        if (cursor.moveToFirst()) {
            do {
                Log.e("여기까지","o");
                GoogleCalendar calendar = new GoogleCalendar();
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


    public List<GoogleCalendar> getSelectMonth(int year,int month) {
        List<GoogleCalendar> calendarList = new ArrayList<GoogleCalendar>();
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM googlecalendars WHERE s_year="+year+" and s_month="+(month+1);


        Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);
        Log.v("제발",""+cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                Log.e("여기까지","o");
                GoogleCalendar calendar = new GoogleCalendar();
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

    public int getCalendarCount() {

        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public GoogleCalendar getSelectedTitle(String s) {
        GoogleCalendar calendarList = new GoogleCalendar();
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM googlecalendars WHERE title = '"+ s +"'";

        //String countQuery = "SELECT * FROM calendars WHERE s_year=2015 and s_month=11 and s_day=5";

        Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);

        if (cursor.moveToFirst()) {

                GoogleCalendar calendar = new GoogleCalendar();
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

}
