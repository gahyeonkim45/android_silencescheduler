package timetablepac;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.allactivity.R;

import java.util.ArrayList;
import java.util.List;

import timetablepac.TimeTable;
import timetablepac.TimeTableDBHandler;

/**
 * Created by youngchae on 2015-11-27.
 */
public class TimeTableAdapter extends BaseAdapter {

    private Context Cont;
    private List<TimeTable> Items =  new ArrayList<TimeTable>();
    private TimeTableDBHandler db;

    TimeTableTextView itemView;

    EditText edittableTime,edittableTitle,edittableClass;
    TextView tvtableTime  ,tvtableTitle,tvtableClass;

    String tabletime="";
    String tableTitle="";
    String tableClass="";

    TelephonyManager telManager;
    String phoneNum;


    String db_title = "";
    String db_class = "";
    String db_week = "";
    int db_s_hour = 0;
    int db_s_min = 0;
    int db_e_hour = 0;
    int db_e_min = 0;

    public TimeTableAdapter(Context context) {
        Cont = context;
        db = new TimeTableDBHandler(context);

        telManager = (TelephonyManager)(Cont.getSystemService(Context.TELEPHONY_SERVICE));
        phoneNum = telManager.getLine1Number();

        for(int i = 0; i<48 ; i++){
            Items.add(new TimeTable("","","","",999,999,999,999));
        }

        Items.set(0, new TimeTable("", "", "", "", 999, 999, 999, 999));
        Items.set(1, new TimeTable("","월","","",999,999,999,999));
        Items.set(2, new TimeTable("","화","","",999,999,999,999));
        Items.set(3, new TimeTable("","수","","",999,999,999,999));
        Items.set(4, new TimeTable("","목","","",999,999,999,999));
        Items.set(5, new TimeTable("","금","","",999,999,999,999));


        Items.set(6, new TimeTable("","A","","",999,999,999,999));
        Items.set(12, new TimeTable("","B","","",999,999,999,999));
        Items.set(18, new TimeTable("","C","","",999,999,999,999));
        Items.set(24, new TimeTable("","D","","",999,999,999,999));
        Items.set(30, new TimeTable("","E","","",999,999,999,999));
        Items.set(36, new TimeTable("","F","","",999,999,999,999));
        Items.set(42, new TimeTable("","G","","",999,999,999,999));

       // Items.set(7, new TimeTable("010-9251-4323","오규환겜디","산학원","",999,999,999,999));
    }

    public void addItem(TimeTable it) {
       Items.add(it);
    }

    public void updateItem(TimeTable it,int index){
       // Items.add(it);
        Items.set(index,it);
    }

    public void removeAll(){
        //Items.add(it);
       Items.clear();
    }

    public void setListItems(List<TimeTable> lit) {
        Items = lit;
    }

    public int getCount() {

        return Items.size();

    }

    public Object getItem(int position) {
        return Items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //Log.d(TAG,"getView(" + position + ") called.");

        if(convertView ==null){
            itemView = new TimeTableTextView(Cont);
        }else{
            itemView = (TimeTableTextView) convertView;
        }

        itemView.setTag(position);

        GridView.LayoutParams params = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 320);

        int rowIndex = position / 6;
        int columnIndex = position % 6;


        //Log.d(TAG, "Index ��ȣ : " + rowIndex + "," + columnIndex);

        itemView.setText1(Items.get(position).getTitle());
        itemView.setText2(Items.get(position).getMemo());
        itemView.setLayoutParams(params);

        //itemView.setPadding(2,2,2,2);
        itemView.setBackgroundColor(Color.WHITE);
        //itemView.set

        itemView.setGravity(Gravity.TOP);

       if(columnIndex == 0){
            itemView.setTextColor(Color.BLACK);
        }

        if(rowIndex == 0){
            itemView.setTextColor(Color.BLACK);
        }


        if(!Items.get(position).getP_num().equals("")){
            itemView.setTextColor(Color.BLACK);
            itemView.setTextColor2(Color.BLACK);
            itemView.setBackgroundColor(Color.rgb(238,197,145));
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int num = Integer.parseInt(view.getTag().toString());
                findTablaTime(Integer.parseInt(view.getTag().toString()));

                final View dialogView = (View) View.inflate((Activity)Cont, R.layout.addtable,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder ((Activity)Cont);


                dlg.setTitle("시간표선택");
                dlg.setView(dialogView);

                TextView tvtimetext = (TextView)dialogView.findViewById(R.id.tvtimetext);
                String min ;
                String emin;

                if(db_s_min == 0){
                   min = "00";
                }else{
                    min = ""+db_s_min;
                }

                if(db_e_min == 0){
                    emin = "00";
                }else{
                    emin = ""+db_e_min;
                }

                tvtimetext.setText(db_s_hour+":"+min+ " ~ "+db_e_hour+":"+emin);

                dlg.setPositiveButton("확인",

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                edittableTitle=(EditText)dialogView.findViewById(R.id.editTitle);
                                tableTitle = edittableTitle.getText().toString();
                                //    update((TextView)v,tableTitle,position);

                                edittableClass=(EditText)dialogView.findViewById(R.id.editClass);
                                tableClass = edittableClass.getText().toString();


                                Log.e("db_week",db_week);
                                Log.e("db_s_hour",""+db_s_hour);
                                Log.e("db_s_min",""+db_s_min);
                                Log.e("db_e_hour",""+db_e_hour);
                                Log.e("db_e_min",""+db_e_hour);

                                Items.set(num,new TimeTable(phoneNum, tableTitle, tableClass, db_week, db_s_hour, db_s_min, db_e_hour, db_e_min));

                                db.addTimeTable(new TimeTable(phoneNum, tableTitle, tableClass, db_week, db_s_hour, db_s_min, db_e_hour, db_e_min));
                                itemView.setText1(tableTitle);
                                itemView.setText2(tableClass);

                                itemView.setBackgroundColor(Color.rgb(255, 64, 64));

                            }
                        }
                );
                dlg.setNegativeButton("취소", null);
                dlg.show();


            }
        });

        return itemView;
    }

    private void findTablaTime(int position){



        int whatTime=position/6;
        int when=position%6; // 1�� 2ȭ 3�� 4�� 5��




        if(whatTime==1){ //a����

            db_s_hour = 9;
            db_s_min= 0;
            db_e_hour= 10;
            db_e_min= 30;
        }

        else if(whatTime==2){ //b����

            db_s_hour = 10 ;
            db_s_min= 30;
            db_e_hour=12;
            db_e_min=0;
        }

        else if(whatTime==3){ //c����

            db_s_hour =12;
            db_s_min=0;
            db_e_hour=13;
            db_e_min=30;
        }

        else if(whatTime==4){ //d����

            db_s_hour =13;
            db_s_min=30;
            db_e_hour=15;
            db_e_min=0;
        }

        else if(whatTime==5){ //e����

            db_s_hour = 15;
            db_s_min=0;
            db_e_hour=16;
            db_e_min=30;
        }

        else if(whatTime==6){ //f����

            db_s_hour =16;
            db_s_min=30;
            db_e_hour=18;
            db_e_min=0;
        }

        else if(whatTime==7){ //g����

            db_s_hour = 18;
            db_s_min=0;
            db_e_hour=19;
            db_e_min=30;
        }

////////////////////////////////////////////////////////���������������ϱ�


        if(when == 1){//�����
            db_week="월";

        }

        else if(when == 2){//ȭ����
            db_week="화";

        }
        else if(when == 3){//������
            db_week="수";

        }

        else if(when == 4){//�����
            db_week="목";

        }

        else if(when == 5){//�ݿ���
            db_week="금";

        }

    }

    public int returnposition(String get_db_week, int get_db_s_hour){
        if (get_db_s_hour == 9) {
            if (get_db_week.equals("월")) {
                return 7;
            }
            if (get_db_week.equals("화")) {
                return 8;
            }
            if (get_db_week.equals("수")) {
                return 9;
            }
            if (get_db_week.equals("목")) {
                return 10;
            }
            if (get_db_week.equals("금")) {
                return 11;
            }
        }

        if (get_db_s_hour == 10) {
            if (get_db_week.equals("월")) {
                return 13;
            }
            if (get_db_week.equals("화")) {
                return 14;
            }
            if (get_db_week.equals("수")) {
                return 15;
            }
            if (get_db_week.equals("목")) {
                return 16;
            }
            if (get_db_week.equals("금")) {
                return 17;
            }
        }

        if (get_db_s_hour == 12) {
            if (get_db_week.equals("월")) {
                return 19;
            }
            if (get_db_week.equals("화")) {
                return 20;
            }
            if (get_db_week.equals("수")) {
                return 21;
            }
            if (get_db_week.equals("목")) {
                return 22;
            }
            if (get_db_week.equals("금")) {
                return 23;
            }
        }


        if (get_db_s_hour == 13) {
            if (get_db_week.equals("월")) {
                return 25;
            }
            if (get_db_week.equals("화")) {
                return 26;
            }
            if (get_db_week.equals("수")) {
                return 27;
            }
            if (get_db_week.equals("목")) {
                return 28;
            }
            if (get_db_week.equals("금")) {
                return 29;
            }
        }



        if (get_db_s_hour == 15) {
            if (get_db_week.equals("월")) {
                return 31;
            }
            if (get_db_week.equals("화")) {
                return 32;
            }
            if (get_db_week.equals("수")) {
                return 33;
            }
            if (get_db_week.equals("목")) {
                return 34;
            }
            if (get_db_week.equals("금")) {
                return 35;
            }
        }



        if (get_db_s_hour == 16) {
            if (get_db_week.equals("월")) {
                return 37;
            }
            if (get_db_week.equals("화")) {
                return 38;
            }
            if (get_db_week.equals("수")) {
                return 39;
            }
            if (get_db_week.equals("목")) {
                return 40;
            }
            if (get_db_week.equals("금")) {
                return 41;
            }
        }



        if (get_db_s_hour == 18) {
            if (get_db_week.equals("월")) {
                return 42;
            }
            if (get_db_week.equals("화")) {
                return 43;
            }
            if (get_db_week.equals("수")) {
                return 44;
            }
            if (get_db_week.equals("목")) {
                return 45;
            }
            if (get_db_week.equals("금")) {
                return 46;
            }
        }

     return 0;
    }

}
