package timetablepac;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allactivity.R;

/**
 * Created by youngchae on 2015-11-27.
 */
public class TimeTableTextView extends LinearLayout {

    private TextView mText01;
    private TextView mText02;

    public TimeTableTextView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.timetableitemview, this, true);

        mText01 = (TextView) findViewById(R.id.timetabletitle);
    //    mText01.setText(todo.getTitle());

        mText02 = (TextView) findViewById(R.id.timetableclass);
    //m

    }

    public void setText1(String data) {

        mText01.setText(data);

    }


    public void setText2(String data) {

        mText02.setText(data);

    }

    public void setTextColor(int color){
        mText01.setTextColor(color);
    }

    public void setTextColor2(int color){
        mText02.setTextColor(color);
    }
}
