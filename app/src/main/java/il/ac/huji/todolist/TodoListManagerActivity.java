package il.ac.huji.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TodoListManagerActivity extends AppCompatActivity {
    ArrayList<MyObject> items;


    MyAdapter adapter;
    ListView myList;
    MySQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        db =new MySQLiteHelper(this);
        myList= (ListView)findViewById(R.id.lstToDoItems);
        items = db.getAllObj();
        adapter = new MyAdapter(this,0,items);
        myList.setAdapter(adapter);

        //when long click on item in the list view - delete
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long arg3) {
                final String theTask = items.get(position).getTask();
                if (theTask.indexOf("Call")!=0) // case is NOT call task
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog
                            .Builder(TodoListManagerActivity.this);
                    alertDialogBuilder.setTitle(theTask)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    MyObject cur = items.get(position);
                                    adapter.remove(items.get(position));
                                    db.deleteObj(cur);


                                    adapter.notifyDataSetChanged();

                                }
                            });
                    alertDialogBuilder.show();

                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder2 = new AlertDialog
                            .Builder(TodoListManagerActivity.this);
                    alertDialogBuilder2.setTitle(theTask)
                            .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    MyObject cur = items.get(position);
                                    adapter.remove(items.get(position));
                                    db.deleteObj(cur);
                                    adapter.notifyDataSetChanged();

                                }
                            }).setNeutralButton(theTask, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            String number = theTask.substring(theTask.indexOf(" "));
                            callIntent.setData(Uri.parse("tel:"+number));
                            startActivity(callIntent);

                        }
                    });
                    alertDialogBuilder2.show();

                }

                return false;
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAdd:
                newAdd();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This function add an item to the list
     */
    private void newAdd(){
        Intent i =new Intent(TodoListManagerActivity.this, AddNewTodoItemActivity.class);
        startActivityForResult(i, 1);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == AppCompatActivity.RESULT_OK){

                String myTask = (String)data.getStringExtra("title");
                Date date = (Date)data.getSerializableExtra("dueDate");
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH)+1;
                int year = c.get(Calendar.YEAR);
                String myDate = String.valueOf(day)+'-'+String.valueOf(month)+'-'+String.valueOf(year);

                MyObject obj = new MyObject (myTask,myDate);
                items.add(obj);
                db.addObj(obj); // add to DB
                adapter.notifyDataSetChanged();
//
            }
//
        }
    }


}
