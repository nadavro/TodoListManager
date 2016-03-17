package il.ac.huji.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewTodoItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item);


        Button okButton = (Button)findViewById(R.id.btnOK);
        Button cancelButton = (Button)findViewById(R.id.btnCancel);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText txt = (EditText)findViewById(R.id.edtNewItem);
                DatePicker date = (DatePicker)findViewById(R.id.datePicker);
                txt.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        txt.setHint("");
                        return false;
                    }

                });
                //Intent intent = new Intent(AddNewTodoItemActivity.this,TodoListManagerActivity.class);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("title",txt.getText().toString());

                int   day  = date.getDayOfMonth();
                int   month= date.getMonth();
                int   year = date.getYear();
                String dateStr = String.valueOf(day)+'-'+String.valueOf(month+1)+'-'+String.valueOf(year);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                //String formatedDate = sdf.format(new Date(year, month, day));
                try {
                    Date date2 = sdf.parse(dateStr);
                    returnIntent.putExtra("dueDate",date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


//                String dateString = sdf.format(date);
//                returnIntent.putExtra("date",dateString);
                // setResult(RESULT_OK);
                //finish();
                //returnIntent.putExtra("result",result);
                setResult(AppCompatActivity.RESULT_OK,returnIntent);
                finish();

                // Perform action on click
            }
        });







    }
}
