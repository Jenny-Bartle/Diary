package com.jenny.diary.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jenny.diary.Task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tasksManager";

    // Tasks table name
    private static final String TABLE_TASKS = "contacts";

    // Tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_HEADING = "name";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_DUEDATE = "due";

    public TaskDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_HEADING + " TEXT,"
                + KEY_DETAILS + " TEXT, "
                + KEY_TIMESTAMP + " TIMESTAMP"
                + KEY_DUEDATE + " TIMESTAMP" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        // Create tables again
        onCreate(db);
    }

    void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, task.getID());
        values.put(KEY_HEADING, task.getHeading());
        values.put(KEY_DETAILS, task.getDetails());
        values.put(KEY_TIMESTAMP, task.getTimestamp().toString());

        if(task.getDueDate() != null) {
            values.put(KEY_DUEDATE, task.getDueDate().toString());
        }

        // Inserting Row
        db.insert(TABLE_TASKS, null, values);
        db.close(); // Closing database connection
    }

    Task getTask(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_ID,
                        KEY_HEADING, KEY_DETAILS, KEY_TIMESTAMP, KEY_DUEDATE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Timestamp.valueOf(cursor.getString(3)));
        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setID(Long.parseLong(cursor.getString(0)));
                task.setHeading(cursor.getString(1));
                task.setDetails(cursor.getString(2));
                task.setTimestamp(Timestamp.valueOf(cursor.getString(3)));
                task.setDueDate(Timestamp.valueOf(cursor.getString(4)));
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        return taskList;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, task.getID());
        values.put(KEY_HEADING, task.getHeading());
        values.put(KEY_DETAILS, task.getDetails());
        values.put(KEY_TIMESTAMP, task.getTimestamp().toString());
        if(task.getDueDate() != null) {
            values.put(KEY_DUEDATE, task.getDueDate().toString());
        }

        // updating row
        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getID()) });
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getID())});
        db.close();
    }

}