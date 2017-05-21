package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;
import android.util.Log;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2015-11-22.
 */
public class ToDoDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "todos";

    private static final String TABLE_CONTACTS = "todos";

    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_TITLE = "title";
    private static final String KEY_NOTICHECK = "noticheck";
    private static final String KEY_COMPLETE = "complete";


    public ToDoDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT,"
                +KEY_START_DATE+" DATE,"+KEY_END_DATE+" DATE,"+ KEY_NOTICHECK + " INTEGER,"
                +KEY_COMPLETE+" INTEGER )";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public// Adding new calendar
    void addTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_C_ID, calendar.c_id);

       // values.put(KEY_TODO_ID, todo.todo_id);

        values.put(KEY_TITLE, todo.title);
        values.put(KEY_START_DATE, todo.start_date.toString());
        values.put(KEY_END_DATE, todo.end_date.toString());
        values.put(KEY_NOTICHECK, todo.noticheck);
        values.put(KEY_COMPLETE, todo.complete);

        Log.e("DBHANDLER", "" + todo.todo_id);
        Log.e("DBHANDLER", "" + todo.title);
        Log.e("DBHANDLER", "" + todo.start_date.toString());
        Log.e("DBHANDLER", "" + todo.end_date.toString());
        Log.e("DBHANDLER", "" + todo.noticheck);
        Log.e("DBHANDLER", "" + todo.complete);

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public int updateTodo(Todo todo) {

       SQLiteDatabase db = this.getWritableDatabase();

  /*      String selectQuery = "Update todos SET start_date ='"+todo.start_date+"' and "+
        "end_date = '"+todo.end_date+"' and noticheck = '"+todo.noticheck+"' and "+
        "complete = '"+todo.complete+"' "
        +"WHERE todo_id = '"+todo.todo_id+"'";

        db.rawQuery(selectQuery, null);
        Log.e("s",selectQuery);

        return 0;
       */

       /*String selectQuery = "SELECT * FROM todos WHERE todo_id = '"+todo.todo_id+"'";

        Log.d("update query: ",selectQuery);
        db.rawQuery(selectQuery, null);*/

        ContentValues values = new ContentValues();

        values.put(KEY_TODO_ID, todo.todo_id);
        values.put(KEY_TITLE, todo.title);
        values.put(KEY_START_DATE, todo.start_date.toString());

        values.put(KEY_END_DATE, todo.end_date.toString());
        values.put(KEY_NOTICHECK, todo.noticheck);
        values.put(KEY_COMPLETE, todo.complete);


        Log.e("please!!!",""+todo.todo_id);
        Log.e("please!!!",todo.title);
        Log.e("please!!!",todo.start_date.toString());
        Log.e("please!!!",todo.end_date.toString());
        Log.e("please!!!",""+todo.noticheck);
        Log.e("please!!!",""+todo.complete);

        return db.update(TABLE_CONTACTS,values,KEY_TODO_ID+" = ? ",new String[]{ String.valueOf(todo.todo_id) });


    }

    Todo getTodo(int id) {
        Todo todo = new Todo();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_TODO_ID,
                        KEY_TITLE,KEY_START_DATE,KEY_END_DATE, KEY_NOTICHECK,KEY_COMPLETE}, KEY_TODO_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try{
            java.util.Date sparsed = format.parse(cursor.getString(2));
            java.sql.Date startddt = new java.sql.Date(sparsed.getTime());

            java.util.Date eparsed = format.parse(cursor.getString(3));
            java.sql.Date endddt = new java.sql.Date(eparsed.getTime());

            todo = new Todo(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), startddt,endddt,Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)));

            return todo;
        }catch(ParseException e){
            e.printStackTrace();
        }

        return todo;

    }

    public void deleteTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_TODO_ID + " = ?",
                new String[]{String.valueOf(todo.getTodo_id())});
        db.close();
    }

    public List<Todo> getSelectedTodos() {
        List<Todo> TodoList = new ArrayList<Todo>();
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        String s =today.year+"-"+(today.month+1)+"-"+today.monthDay;
        Date query;

        try{
            java.util.Date parsed = format.parse(s);
            query = new java.sql.Date(parsed.getTime());
            String countQuery = "SELECT  * FROM todos WHERE noticheck = 1 and complete = 0 and start_date = '"+query+"'";
            Log.d("query: ",countQuery);

            Cursor cursor = db.rawQuery(countQuery,null);

            if (cursor.moveToFirst()) {
                do {
                    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                    try{
                        java.util.Date parsed3 = format2.parse(cursor.getString(2));
                        java.sql.Date s_ddt = new java.sql.Date(parsed3.getTime());

                        java.util.Date parsed4 = format2.parse(cursor.getString(3));
                        java.sql.Date e_ddt = new java.sql.Date(parsed4.getTime());

                        Todo todo = new Todo();

                        todo.setTodo_id(Integer.parseInt(cursor.getString(0)));
                        todo.setTitle(cursor.getString(1));
                        todo.setStart_date(s_ddt);
                        todo.setEnd_date(e_ddt);
                        todo.setNoticheck(Integer.parseInt(cursor.getString(4)));
                        todo.setComplete(Integer.parseInt(cursor.getString(5)));


                        Log.e("Todo list : ",""+Integer.parseInt(cursor.getString(0)) );
                        Log.e("Todo list : ",""+cursor.getString(1) );
                        Log.e("Todo list : ",""+s_ddt);
                        Log.e("Todo list : ",""+e_ddt );
                        Log.e("Todo list : ",""+Integer.parseInt(cursor.getString(4)) );
                        Log.e("Todo list : ",""+Integer.parseInt(cursor.getString(5)));

                        TodoList.add(todo);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
                db.close();

                return TodoList;
            }
        }catch(ParseException e){
            e.printStackTrace();
        }
        return TodoList;
    }

    public List<Todo> getSelectedTodos(int year,int month,int day) {

        List<Todo> TodoList = new ArrayList<Todo>();
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        String s =year+"-"+month+"-"+day;
        Date query;

        try{
            java.util.Date parsed = format.parse(s);
            query = new java.sql.Date(parsed.getTime());
            String countQuery = "SELECT  * FROM todos WHERE start_date = '"+query+"'";
            Log.d("query: ",countQuery);

            Cursor cursor = db.rawQuery(countQuery,null);

            if (cursor.moveToFirst()) {
                do {
                    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                    try{
                        java.util.Date parsed3 = format2.parse(cursor.getString(2));
                        java.sql.Date s_ddt = new java.sql.Date(parsed3.getTime());

                        java.util.Date parsed4 = format2.parse(cursor.getString(3));
                        java.sql.Date e_ddt = new java.sql.Date(parsed4.getTime());

                        Todo todo = new Todo();

                        todo.setTodo_id(Integer.parseInt(cursor.getString(0)));
                        todo.setTitle(cursor.getString(1));
                        todo.setStart_date(s_ddt);
                        todo.setEnd_date(e_ddt);
                        todo.setNoticheck(Integer.parseInt(cursor.getString(4)));
                        todo.setComplete(Integer.parseInt(cursor.getString(5)));

                        TodoList.add(todo);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
                db.close();

                return TodoList;
            }
        }catch(ParseException e){
            e.printStackTrace();
        }
        return TodoList;
    }


}
