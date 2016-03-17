package il.ac.huji.todolist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class MyAdapter extends ArrayAdapter<MyObject>{
    private Activity activity;

    private static LayoutInflater inflater = null;

    private ArrayList<MyObject> data = null;

    public MyAdapter(Context context, int resource, ArrayList<MyObject> objects) {
        super(context, resource, objects);

        try {
            this.activity = (Activity) context;
            this.data = objects;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }


    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public MyObject getItem(int pos) {
        return data.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView task;
        public TextView taskDate;
    }
    /**
     * Checks if the date argument (represent by string) is before current date
     * @param dateStr
     * @return true if dateStr is before current date, false otherwise.
     * @throws ParseException
     */
    private boolean dateIsPassed(String dateStr) throws ParseException {
        Calendar c = Calendar.getInstance();
        int currDay = c.get(Calendar.DAY_OF_MONTH);
        int currMonth = c.get(Calendar.MONTH)+1;
        int currYear = c.get(Calendar.YEAR);
        String currStr = String.valueOf(currDay)+'-'+String.valueOf(currMonth)+'-'+String.valueOf(currYear);
        DateFormat formatteri ;
        Date currd ;
        formatteri = new SimpleDateFormat("dd-MM-yyyy");
        currd = (Date)formatteri.parse(currStr);

        DateFormat formatter ;
        Date d ;
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        d = (Date)formatter.parse(dateStr);

        return currd.compareTo(d)>0;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.row, null);
                holder = new ViewHolder();
                holder.task = (TextView) vi.findViewById(R.id.txtTodoTitle);
                holder.taskDate = (TextView) vi.findViewById(R.id.txtTodoDueDate);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.task.setText(data.get(position).getTask());
            String dateStr = data.get(position).getDate();
            holder.taskDate.setText(dateStr);

            if (dateIsPassed(dateStr)) //check if the date had passed
            {
                holder.taskDate.setTextColor(Color.RED);

            }

        } catch (Exception e) {

        }
        return vi;
    }




}
