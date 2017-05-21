package todopac;

/**
 * Created by LG on 2015-11-19.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ListAdapter2 extends BaseAdapter {

    private Context Cont;

    private List<Item2> Items = new ArrayList<Item2>();

    public ListAdapter2(Context context) {
        Cont = context;
    }

    public void addItem(Item2 it) {
       Items.add(it);
    }

    public void updateItem(Item2 it,int index){

        //Items.add(it);
        Items.set(index,it);

    }

    public void setListItems(List<Item2> lit) {
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

    public boolean isSelectable(int position) {
        try {
            return Items.get(position).isSelectable();
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        dayTextView2 itemView;

        if (convertView == null) {
            itemView = new dayTextView2(Cont,Items.get(position));

        } else {
            itemView = (dayTextView2) convertView;

            itemView.setText(0, Items.get(position).getData(0));
            itemView.setText(1, Items.get(position).getData(1));
            itemView.setText(2, Items.get(position).getData(2));

        }

        return itemView;
    }

}