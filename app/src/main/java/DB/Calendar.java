package DB;

/**
 * Created by LG on 2015-11-05.
 */
public class Calendar {
    int c_id;
    String p_num,title,memo;
    int s_year,s_month,s_day,s_hour,s_min;
    int e_year,e_month,e_day,e_hour,e_min;

    public Calendar(){

    }

/*    public Calendar(int c_id, String p_num,String title,String memo,int s_year,
                    int s_month,int s_day,int s_hour,int s_min,
                    int e_year,int e_month,int e_day,int e_hour,int e_min){
        this.c_id = c_id;
        this.p_num = p_num;
        this.title = title;
        this.memo = memo;
        this.s_year = s_year;
        this.s_month = s_month;
        this.s_day = s_day;
        this.s_hour = s_hour;
        this.s_min = s_min;
        this.e_year = e_year;
        this.e_month = e_month;
        this.e_day = e_day;
        this.e_hour = e_hour;
        this.e_min = e_min;
    }
*/
    public Calendar(String p_num,String title,String memo, int s_year,
                    int s_month,int s_day,int s_hour,int s_min,
                    int e_year,int e_month,int e_day,int e_hour,int e_min){
        this.p_num = p_num;
        this.title = title;
        this.memo = memo;
        this.s_year = s_year;
        this.s_month = s_month;
        this.s_day = s_day;
        this.s_hour = s_hour;
        this.s_min = s_min;
        this.e_year = e_year;
        this.e_month = e_month;
        this.e_day = e_day;
        this.e_hour = e_hour;
        this.e_min = e_min;
    }



    public int getC_id() {
        return c_id;
    }

    public void setC_num(int c_id) {
        this.c_id = c_id;
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

    public int getS_year() {
        return s_year;
    }

    public void setS_year(int s_year) {
        this.s_year = s_year;
    }

    public int getS_day() {
        return s_day;
    }

    public void setS_day(int s_day) {
        this.s_day = s_day;
    }

    public int getS_month() {
        return s_month;
    }

    public void setS_month(int s_month) {
        this.s_month = s_month;
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

    public int getE_year() {
        return e_year;
    }

    public void setE_year(int e_year) {
        this.e_year = e_year;
    }

    public int getE_month() {
        return e_month;
    }

    public void setE_month(int e_month) {
        this.e_month = e_month;
    }

    public int getE_day() {
        return e_day;
    }

    public void setE_day(int e_day) {
        this.e_day = e_day;
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
