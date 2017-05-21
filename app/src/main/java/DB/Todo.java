package DB;

import java.util.Date;

/**
 * Created by LG on 2015-11-22.
 */
public class Todo {

    int todo_id;
    String title;
    Date start_date;
    Date end_date;
    int noticheck;
    int complete;

    public Todo() {
    }

    public Todo(int todo_id,  String title, Date start_date, Date end_date, int noticheck,int complete) {
        this.todo_id = todo_id;
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.noticheck = noticheck;
        this.complete = complete;
    }

    public Todo(String title, Date start_date, Date end_date, int noticheck,int complete) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.noticheck = noticheck;
        this.complete = complete;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(int todo_id) {
        this.todo_id = todo_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNoticheck() {
        return noticheck;
    }

    public void setNoticheck(int noticheck) {
        this.noticheck = noticheck;
    }
}
