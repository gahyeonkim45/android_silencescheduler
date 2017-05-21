package todopac;

/**
 * Created by LG on 2015-11-19.
 */

import java.io.Serializable;

class ScheduleList {
    public static int yy = 10;
    public static int mm = 12;
    public static int dd = 5;
    public static int tt = 24;
    public static int pp = 42;
    public static String[] title = new String[24];
    public static String[] Message = new String[24];

    public static Schedule[][][] Sch;

    public static void Load() {
        Sch = new Schedule[yy][mm][pp];
        for (int yyy = 0; yyy < yy; yyy++) {
            for (int mmm = 0; mmm < mm; mmm++) {
                for (int ppp = 0; ppp < pp; ppp++) {
                    for (int ttt = 0; ttt < tt; ttt++) {
                        Sch[yyy][mmm][ppp] = new Schedule();
                        Sch[yyy][mmm][ppp].Sch("", ttt, "");
                    }
                }
            }
        }
    }
}

public class Schedule implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */

    public String time;
    private String[] tt = new String[24];
    private String[] me = new String[24];

    public void Sch(String title, int time, String message) {
        this.me[time] = message;
        this.tt[time] = title;

    }

    public String gettitle(int time) {
        return tt[time];
    }

    public String getmessage(int time) {
        return me[time];
    }

    public Schedule() {

    }

}
