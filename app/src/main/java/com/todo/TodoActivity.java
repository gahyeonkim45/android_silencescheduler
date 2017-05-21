package com.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allactivity.R;
import com.example.allactivity.addpac.AddTodo;

import java.util.List;

import DB.ToDoDBHandler;
import DB.Todo;

/**
 * Created by LG on 2015-11-23.
 */
public class TodoActivity extends Activity {

    TodoView todo;
    TodoListAdapter adapter;
    ToDoDBHandler handler;
    List<Todo> list;

    GestureDetector mGestureDetector;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    int d;
    int y;
    int m;
    ViewGroup.LayoutParams params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        handler = new ToDoDBHandler(this);

        super.onCreate(savedInstanceState);


        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        d = today.monthDay;
        m = today.month;
        y = today.year;

        params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);

        todo = new TodoView(this);
        adapter = new TodoListAdapter(this);

       adapter.addItem(new Todo(999,""+y+"년"+(m+1)+"월"+d+"일",null,null,999,999));

        if(!(list = handler.getSelectedTodos(y,(m+1),d)).isEmpty()){
           // list = handler.getSelectedTodos();
            for(int i=0; i< list.size(); i++){
                adapter.addItem(list.get(i));
            }
        }




        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {
                try {
                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                        return false;
                    // right to left swipe
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                        adapter.removeAll();

                        d++;

                        adapter.addItem(new Todo(999,""+y+"년"+(m+1)+"월"+d+"일",null,null,999,999));

                        if(!(list = handler.getSelectedTodos(y,(m+1),d)).isEmpty()){

                           // list = handler.getSelectedTodos();
                            for(int i=0; i< list.size(); i++){
                                adapter.addItem(list.get(i));
                            }

                        }

                        todo.setAdapter(adapter);
                        setContentView(todo, params);

                        // Log.d("right to left","안들어오나");

                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                        adapter.removeAll();
                        d--;

                        adapter.addItem(new Todo(999,""+y+"년"+m+"월"+d+"일",null,null,999,999));

                        if(!(list = handler.getSelectedTodos(y,(m+1),d)).isEmpty()){

                            for(int i=0; i< list.size(); i++){
                                adapter.addItem(list.get(i));
                            }
                        }

                        todo.setAdapter(adapter);
                        setContentView(todo, params);

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

        todo.setAdapter(adapter);
        setContentView(todo,params);

        todo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                {
                    Intent t = new Intent(TodoActivity.this, AddTodo.class);
                    startActivity(t);

                }
            }
        });


    }


}
