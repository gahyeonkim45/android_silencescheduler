package todopac;

/**
 * Created by LG on 2015-11-19.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.allactivity.OnDataSelectionListener;

public class DataListView2 extends ListView {

    private ListAdapter2 adapter;

    private OnDataSelectionListener selectionListener;

    public DataListView2(Context context) {
        super(context);

        init();
    }

    public DataListView2(Context context, AttributeSet attrs) {
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

    class OnItemClickAdapter implements OnItemClickListener {

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