package todopac;

/**
 * Created by LG on 2015-11-19.
 */


public class Item2 {

    private String[] mData;
    private Integer[] dayData;

    private boolean mSelectable = true;

    public Item2(String[] obj) {
        mData = obj;
    }

    public Item2(String obj01, String obj02, String obj03,int year,int month,int day) {
        mData = new String[3];
        mData[0] = obj01;
        mData[1] = obj02;
        mData[2] = obj03;
        dayData = new Integer[3];
        dayData[0] = year;
        dayData[1] = month;
        dayData[2] = day;
    }

    public boolean isSelectable() {
        return mSelectable;
    }

    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    public String[] getData() {
        return mData;
    }

    public Integer[] getDateData() {
        return dayData;
    }


    public String getData(int index) {
        if (mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];
    }


    public Integer getDateData(int index) {
        if (dayData == null || index >= dayData.length) {
            return null;
        }

        return dayData[index];
    }

    public void setData(String[] obj) {
        mData = obj;
    }

    public int compareTo(Item2 other) {
        if (mData != null) {
            String[] otherData = other.getData();
            if (mData.length == otherData.length) {
                for (int i = 0; i < mData.length; i++) {
                    if (!mData[i].equals(otherData[i])) {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        } else {
            throw new IllegalArgumentException();
        }

        return 0;
    }
}