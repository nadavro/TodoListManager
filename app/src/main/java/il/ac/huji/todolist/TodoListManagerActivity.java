package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class TodoListManagerActivity extends AppCompatActivity {
    ArrayList<String> items;
    MyAdapter adapter;
    ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);


        myList= (ListView)findViewById(R.id.lstToDoItems);
        items = new ArrayList<>();

        adapter = new MyAdapter(this,android.R.layout.simple_list_item_1,items);
        myList.setAdapter(adapter);

        //when long click on item in the list view - delete
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long arg3) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog
                        .Builder(TodoListManagerActivity.this);
                alertDialogBuilder.setTitle(items.get(position).toString())
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                adapter.remove(items.get(position));
                                adapter.notifyDataSetChanged();

                            }
                        });
                alertDialogBuilder.show();
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
        EditText curr = (EditText)findViewById(R.id.edtNewItem);
        String st= curr.getText().toString();
        if (st.isEmpty())
        {
          return false;
        }
        // Handle item selection
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

        //creating the dialog box
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("You want to add this item?");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                EditText curr = (EditText)findViewById(R.id.edtNewItem);
                items.add(curr.getText().toString());
                adapter.notifyDataSetChanged();//tells the adapter-"Hey! there is a new item to add"

            }
        });

        alertDialogBuilder.show();

    }


}
