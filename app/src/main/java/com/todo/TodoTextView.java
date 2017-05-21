package com.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allactivity.R;

import DB.ToDoDBHandler;
import DB.Todo;

/**
 * Created by LG on 2015-11-23.
 */
public class TodoTextView extends LinearLayout {

    private Context con;
    private TextView mText01;
    private CheckBox mCheck01;
    private ToDoDBHandler db;
    Todo doit;

    private AlertDialog mDialog = null;
    private int selectnum;

    public TodoTextView(Context context, Todo todo) {
        super(context);
        con = context;
        doit = todo;

        db = new ToDoDBHandler(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.todoitem, this, true);

        mText01 = (TextView) findViewById(R.id.dataItem01);
        mText01.setText(todo.getTitle());

        mCheck01 = (CheckBox) findViewById(R.id.dataItem02);

        mCheck01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) {
                    Log.e("여기로 오니??", doit.getTitle());
                    db.updateTodo(new Todo(doit.getTodo_id(), doit.getTitle(), doit.getStart_date(), doit.getEnd_date(), doit.getNoticheck(), 1));
                }else{
                    Log.e("여기로 오니??2", doit.getTitle());
                    db.updateTodo(new Todo(doit.getTodo_id(), doit.getTitle(), doit.getStart_date(), doit.getEnd_date(), doit.getNoticheck(), 0));
                }
            }

        });

        if(todo.getComplete() == 999){
            mCheck01.setVisibility(View.GONE);
        }else if(todo.getComplete() == 1){
            setChecked();
        }

        mText01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(doit.getComplete()==999){
                    Log.e("들어와?", "" + selectnum);
                }else {
                    Log.e("들어와?", "" + selectnum);
                    mDialog = createDialog();
                    mDialog.show();
                }
            }

        });

    }

    public void setText(String data) {

            mText01.setText(data);

    }

    public void setChecked(){
        mCheck01.setChecked(true);
    }

    public void setNVisible(){
        mCheck01.setVisibility(View.GONE);
    }


    private AlertDialog createDialog() {

        AlertDialog.Builder ab = new AlertDialog.Builder(con);
        ab.setTitle("Delete");
        ab.setMessage("삭제하시겠습니까?");
        ab.setCancelable(false);

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                db.deleteTodo(doit);
                setDismiss(mDialog);
            }
        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mDialog);
            }
        });

        return ab.create();
    }


    private void setDismiss(Dialog dialog){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
