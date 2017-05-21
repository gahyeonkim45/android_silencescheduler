package timetablepac;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.allactivity.CalendarAdapter;
import com.example.allactivity.OnDataSelectionListener;

/**
 * Created by youngchae on 2015-11-27.
 */
public class TimeTableView extends GridView {

    private OnDataSelectionListener selectionListener;

    TimeTableAdapter adapter;

    public TimeTableView(Context context){
        super(context);
        init();
    }

    public TimeTableView(Context context,AttributeSet attr){
        super(context, attr);
        init();
    }

    private void init(){
        setBackgroundColor(Color.DKGRAY);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        setNumColumns(6);
        setVerticalSpacing(1);
        setHorizontalSpacing(1);
        setOnItemClickListener(new OnItemClickAdapter());
    }


    class OnItemClickAdapter implements OnItemClickListener {
        public OnItemClickAdapter(){

        }

        public void onItemClick(AdapterView parent, View v, int position, long id){
            if(adapter != null){
               // adapter.setSelectedPosition(position);
               adapter.notifyDataSetInvalidated();
            }

            if(selectionListener!=null){
                selectionListener.onDataSelected(parent, v,position, id);
            }
        }
    }


    public void setAdapter(BaseAdapter adapter){
        super.setAdapter(adapter);
        this.adapter = (TimeTableAdapter) adapter;
    }

    public BaseAdapter getAdapter(){
        return (BaseAdapter)super.getAdapter();
    }

    public void setOnDataSelectionListener(OnDataSelectionListener listener){
        this.selectionListener = listener;
    }

    public OnDataSelectionListener getOnDataSelectionListener(){
        return selectionListener;
    }
}