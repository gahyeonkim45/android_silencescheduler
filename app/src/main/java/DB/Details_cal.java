package DB;

/**
 * Created by LG on 2015-11-20.
 */
public class Details_cal {

    int c_id;
    String lati;
    String longi;
    int alarm;
    String repeat;


    public Details_cal() {

    }

 /*   public Details_cal(int c_id, String memo, String lati, String longi, int alarm, String repeat) {
        this.c_id = c_id;
        this.memo = memo;
        this.lati = lati;
        this.longi = longi;
        this.alarm = alarm;
        this.repeat = repeat;
    }
*/
    public Details_cal( String lati, String longi, int alarm, String repeat) {
        this.lati = lati;
        this.longi = longi;
        this.alarm = alarm;
        this.repeat = repeat;
    }

    public Details_cal(int alarm, String repeat) {
        this.alarm = alarm;
        this.repeat = repeat;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }


}
