package timetablepac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2015-11-08.
 */
public class TimeTableDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "timetables";


    private static final String TABLE_CONTACTS = "timetables";

    private static final String KEY_T_ID = "c_id";
    private static final String KEY_P_NUM = "p_num";
    private static final String KEY_TITLE = "title";
    private static final String KEY_MEMO = "memo";
    private static final String KEY_WEEK = "week";
    private static final String KEY_S_HOUR = "s_hour";
    private static final String KEY_S_MIN = "s_min";
    private static final String KEY_E_HOUR = "e_hour";
    private static final String KEY_E_MIN = "e_min";


    public TimeTableDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_T_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_P_NUM + " TEXT,"
                +KEY_TITLE+" TEXT,"+KEY_MEMO+" TEXT,"
                +KEY_WEEK+" TEXT,"+KEY_S_HOUR+" INTEGER,"
                +KEY_S_MIN+" INTEGER,"+KEY_E_HOUR+" INTEGER,"
                +KEY_E_MIN+" INTEGER"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }

    public// Adding new calendar
    void addTimeTable(TimeTable table) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_P_NUM, table.p_num);
        values.put(KEY_TITLE, table.title);
        values.put(KEY_MEMO, table.memo);
        values.put(KEY_WEEK, table.week);

        values.put(KEY_S_HOUR, table.s_hour);
        values.put(KEY_S_MIN, table.s_min);
        values.put(KEY_E_HOUR, table.e_hour);
        values.put(KEY_E_MIN, table.e_min);


        Log.e("DBHANDLER", "" + table.p_num);

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public int updateTimeTable(TimeTable table) {

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM timetables WHERE t_id = '"+table.t_id+"'";

        Log.d("update query: ", selectQuery);
        db.rawQuery(selectQuery, null);

        ContentValues values = new ContentValues();
        //values.put(KEY_C_ID, calendar.c_id);

        values.put(KEY_P_NUM, table.p_num);
        values.put(KEY_TITLE, table.title);
        values.put(KEY_MEMO, table.memo);
        values.put(KEY_WEEK, table.week);
        values.put(KEY_S_HOUR, table.s_hour);
        values.put(KEY_S_MIN, table.s_min);
        values.put(KEY_E_HOUR, table.e_hour);
        values.put(KEY_E_MIN, table.e_min);
        return db.update(TABLE_CONTACTS,values,table.t_id+"= '"+table.t_id+"'",null);


    }

    public void deleteTimeTable(TimeTable table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_T_ID + " = ?",
                new String[]{String.valueOf(table.getT_id())});
        db.close();
    }

    public List<TimeTable> getSelectedCTimeTable() {
        List<TimeTable> tableList = new ArrayList<TimeTable>();
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM timetables";

        //String countQuery = "SELECT * FROM calendars WHERE s_year=2015 and s_month=11 and s_day=5";

        Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);

        if (cursor.moveToFirst()) {
            do {
                Log.e("여기까지","o");
                TimeTable table = new TimeTable();
                table.setT_id(Integer.parseInt(cursor.getString(0)));
                table.setP_num(cursor.getString(1));
                table.setTitle(cursor.getString(2));
                table.setMemo(cursor.getString(3));
                table.setWeek(cursor.getString(4));
                table.setS_hour(Integer.parseInt(cursor.getString(5)));
                table.setS_min(Integer.parseInt(cursor.getString(6)));
                table.setE_hour(Integer.parseInt(cursor.getString(7)));
                table.setE_min(Integer.parseInt(cursor.getString(8)));

                tableList.add(table);

            } while (cursor.moveToNext());

            db.close();

            return tableList;

        }
        return tableList;
    }

    public List<TimeTable> getSelectedCTimeTable(int week) {
        List<TimeTable> tableList = new ArrayList<TimeTable>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] strings = {"일","월","화","수","목","금","토"};

        String countQuery = "SELECT * FROM timetables where week='"+strings[week]+"'";

        //String countQuery = "SELECT * FROM calendars WHERE s_year=2015 and s_month=11 and s_day=5";

        Log.v("s",countQuery);

        Cursor cursor = db.rawQuery(countQuery,null);

        if (cursor.moveToFirst()) {
            do {
                Log.e("여기까지","o");
                TimeTable table = new TimeTable();
                table.setT_id(Integer.parseInt(cursor.getString(0)));
                table.setP_num(cursor.getString(1));
                table.setTitle(cursor.getString(2));
                table.setMemo(cursor.getString(3));
                table.setWeek(cursor.getString(4));
                table.setS_hour(Integer.parseInt(cursor.getString(5)));
                table.setS_min(Integer.parseInt(cursor.getString(6)));
                table.setE_hour(Integer.parseInt(cursor.getString(7)));
                table.setE_min(Integer.parseInt(cursor.getString(8)));

                tableList.add(table);

            } while (cursor.moveToNext());

            db.close();

            return tableList;

        }
        return tableList;
    }


}
