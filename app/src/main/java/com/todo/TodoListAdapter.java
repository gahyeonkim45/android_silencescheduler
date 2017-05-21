package com.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.allactivity.OnDataSelectionListener;

import java.util.ArrayList;
import java.util.List;

import DB.ToDoDBHandler;
import DB.Todo;

/**
 * Created by LG on 2015-11-23.
 */
public class TodoListAdapter extends BaseAdapter {

    private Context Cont;

    private List<Todo> Items = new ArrayList<Todo>();
    private ToDoDBHandler tdb;

    public TodoListAdapter(Context context) {
        Cont = context;
        tdb = new ToDoDBHandler(context);
    }

    public void addItem(Todo it) {
        Items.add(it);
    }

    public void deleteItem(int index) {
        tdb.deleteTodo(Items.get(index));
    }

    public void updateItem(Todo it,int index){
        //Items.add(it);
        Items.set(index,it);
        tdb.updateTodo(it);
    }

    public void removeAll(){
        //Items.add(it);
        Items.clear();
    }

    public void setListItems(List<Todo> lit) {
        Items = lit;
    }

    public int getCount() {
        return Items.size();
    }

    public Object getItem(int position) {
        return Items.get(position);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        TodoTextView itemView;

        if (convertView == null) {
            itemView = new TodoTextView(Cont,Items.get(position));

        } else {
            itemView = (TodoTextView) convertView;

            itemView.setText(Items.get(position).getTitle());

            if(Items.get(position).getComplete() == 1){
                itemView.setChecked();
            }else if(Items.get(position).getComplete() == 999){
                Log.e("여기로 들어왓냐!!!!!!!",""+999);
                itemView.setNVisible();
            }

        }

        return itemView;
    }



}
