package todopac;

/**
 * Created by LG on 2015-11-19.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allactivity.R;


public class dayTextView2 extends LinearLayout {

    private TextView mText01;

    private TextView mText02;

    private TextView mText03;

    public dayTextView2(Context context, Item2 aItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listitem, this, true);

        mText01 = (TextView) findViewById(R.id.dataItem01);
        mText01.setText(aItem.getData(0));

        mText02 = (TextView) findViewById(R.id.dataItem02);
        mText02.setText(aItem.getData(1));

        mText03 = (TextView) findViewById(R.id.dataItem03);
        mText03.setText(aItem.getData(2));


    }

    public void setText(int index, String data) {
        if (index == 0) {
            mText01.setText(data);
        } else if (index == 1) {
            mText02.setText(data);
        } else if (index == 2) {
            mText03.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }

}
