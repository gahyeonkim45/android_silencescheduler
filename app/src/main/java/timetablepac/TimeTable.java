package timetablepac;

/**
 * Created by LG on 2015-11-08.
 */
public class TimeTable {
    int t_id;
    String p_num, title, memo, week;
    int s_hour, s_min, e_hour, e_min;

    public TimeTable(){

    }

    public TimeTable(String p_num, String title, String memo, String week, int s_hour, int s_min, int e_hour, int e_min) {
        this.p_num = p_num;
        this.title = title;
        this.memo = memo;
        this.week = week;
        this.s_hour = s_hour;
        this.s_min = s_min;
        this.e_hour = e_hour;
        this.e_min = e_min;
    }


    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getP_num() {
        return p_num;
    }

    public void setP_num(String p_num) {
        this.p_num = p_num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getS_hour() {
        return s_hour;
    }

    public void setS_hour(int s_hour) {
        this.s_hour = s_hour;
    }

    public int getS_min() {
        return s_min;
    }

    public void setS_min(int s_min) {
        this.s_min = s_min;
    }

    public int getE_hour() {
        return e_hour;
    }

    public void setE_hour(int e_hour) {
        this.e_hour = e_hour;
    }

    public int getE_min() {
        return e_min;
    }

    public void setE_min(int e_min) {
        this.e_min = e_min;
    }
}
