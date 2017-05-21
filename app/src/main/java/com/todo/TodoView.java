package com.todo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.allactivity.OnDataSelectionListener;

import todopac.ListAdapter2;

/**
 * Created by LG on 2015-11-23.
 */
public class TodoView extends ListView {

    private TodoListAdapter adapter;
    private OnDataSelectionListener selectionListener;


    public TodoView(Context context) {
        super(context);
        init();
    }

    public TodoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        setOnItemClickListener(new OnItemClickAdapter());
    }

    public void setAdapter(BaseAdapter adapter) {
        super.setAdapter(adapter);

    }

    public BaseAdapter getAdapter() {
        return (BaseAdapter) super.getAdapter();
    }

    public void setOnDataSelectionListener(OnDataSelectionListener listener) {
        this.selectionListener = listener;
    }

    public OnDataSelectionListener getOnDataSelectionListener() {
        return selectionListener;
    }

    class OnItemClickAdapter implements AdapterView.OnItemClickListener {

        public OnItemClickAdapter() {

        }

        public void onItemClick(AdapterView parent, View v, int position,
                                long id) {

            if (selectionListener == null) {
                return;
            }

            selectionListener.onDataSelected(parent, v, position, id);


        }

    }
}
