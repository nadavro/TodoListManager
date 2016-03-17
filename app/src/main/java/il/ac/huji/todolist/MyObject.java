package il.ac.huji.todolist;


/**
 * Created by Israel Rozen on 16/03/2016.
 */
public class MyObject {
    private String task;
    private String date;
    public MyObject(String task, String date) {
        this.task = task;
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}