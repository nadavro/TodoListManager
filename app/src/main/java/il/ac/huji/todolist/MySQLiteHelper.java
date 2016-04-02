package il.ac.huji.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Israel Rozen on 01/04/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "todo_db";

    private static final String TABLE_TODO = "todo";


    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DUE = "due";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_DUE};
    private int counter = 1;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE todo ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "due LONG )";

        // create todo table
        db.execSQL(CREATE_TODO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older
        db.execSQL("DROP TABLE IF EXISTS todo");

        // create fresh
        this.onCreate(db);

    }

    public void addObj(MyObject currObj){


        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();
        currObj.setId(counter);
        counter=counter+1;
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, currObj.getTask()); // get title
        values.put(KEY_DUE, currObj.getDate()); // get author

        // 3. insert
        db.insert(TABLE_TODO, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public MyObject getObj(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_TODO, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build myObj object
        MyObject newObj = new MyObject();
        newObj.setId(Integer.parseInt(cursor.getString(0)));
        newObj.setTask(cursor.getString(1));
        newObj.setDate(cursor.getString(2));



        // 5. return newObj
        return newObj;
    }

    public ArrayList<MyObject> getAllObj() {
        ArrayList<MyObject> lst = new ArrayList<MyObject>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_TODO;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        MyObject currentObj = null;
        if (cursor.moveToFirst()) {
            do {
                currentObj = new MyObject();
                currentObj.setId(Integer.parseInt(cursor.getString(0)));
                currentObj.setTask(cursor.getString(1));
                currentObj.setDate(cursor.getString(2));


                lst.add(currentObj);
            } while (cursor.moveToNext());
        }




        return lst;
    }
    public int updateMyobject(MyObject obj) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", obj.getTask()); // get title
        values.put("due", obj.getDate()); // get due

        // 3. updating row
        int i = db.update(TABLE_TODO, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(obj.getId())}); //selection args

        // 4. close
        db.close();

        return i;

    }

    public void deleteObj(MyObject obj) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_TODO, //table name
                KEY_ID + " = ?",  // selections
                new String[]{String.valueOf(obj.getId())}); //selections args

        // 3. close
        db.close();



    }
}
