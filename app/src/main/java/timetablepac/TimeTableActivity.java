package timetablepac;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.allactivity.R;

import java.util.List;

/**
 * Created by youngchae on 2015-11-27.
 */
public class TimeTableActivity extends Activity {
    TimeTableView timeView;
    TimeTableAdapter timeAdapter;
    TimeTableDBHandler db;
    List<TimeTable> timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_activity);

        db = new TimeTableDBHandler(this);

        timeView = (TimeTableView) findViewById(R.id.monthView);
        timeAdapter = new TimeTableAdapter(this);
        timeView.setAdapter(timeAdapter);

        if(!(timetable = db.getSelectedCTimeTable()).isEmpty()){
            for(int i=0; i<timetable.size(); i++){
                int position = timeAdapter.returnposition(timetable.get(i).getWeek(),timetable.get(i).getS_hour());

                Log.e("week", timetable.get(i).getWeek());
                Log.e("hour",""+timetable.get(i).getS_hour());
                Log.e("position",""+position);

                timeAdapter.updateItem(timetable.get(i),position);
            }
        }


    }
}
