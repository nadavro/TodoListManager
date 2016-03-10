package il.ac.huji.todolist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;


/**
 * Created by Nadav Rozen on 09/03/2016.
 */
public class MyAdapter extends ArrayAdapter<String>{

    private ArrayList<String> data;
    private int[] colors = new int[]{0x30FF0000,0x300000FF,};

    public MyAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.data=objects;
    }


    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public String getItem(int pos) {
        return data.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }




    @Override
    public View getView(int pos, View convertView, ViewGroup parent){

        View vi = super.getView(pos,convertView,parent);

        //Now determine the background color of the new list-item
        int col = pos % colors.length;
        vi.setBackgroundColor(colors[col]);

        return vi;
    }


}
