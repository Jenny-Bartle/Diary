package com.jenny.diary.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jenny.diary.category.Category;
import com.jenny.diary.todo.Task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DiaryDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database and table names
    private static final String DATABASE_NAME = "DiaryAppData";
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_CATEGORIES_TASKS = "categories_tasks";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TASK_ID = "task_id";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_HEADING = "name";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_DUEDATE = "due";

    public DiaryDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_HEADING + " TEXT,"
                + KEY_DETAILS + " TEXT, "
                + KEY_TIMESTAMP + " TIMESTAMP, "
                + KEY_DUEDATE + " TIMESTAMP" + ")";
        db.execSQL(CREATE_TASKS_TABLE);

        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_HEADING + " TEXT,"
                + KEY_HEADING + " TEXT,"
                + ")";
        db.execSQL(CREATE_CATEGORIES_TABLE);

        String CREATE_CATEGORIES_TODO_TABLE = "CREATE TABLE " + TABLE_CATEGORIES_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TASK_ID + " INTEGER,"
                + KEY_CATEGORY_ID + " INTEGER,"
                + ")";
        db.execSQL(CREATE_CATEGORIES_TODO_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES_TASKS);

        // Create tables again
        onCreate(db);
    }

    // Tasks
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = initialiseTask(task);

        // Inserting Row
        db.insert(TABLE_TASKS, null, values);
        db.close(); // Closing database connection
    }

    public Task getTask(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_ID,
                        KEY_HEADING, KEY_DETAILS, KEY_TIMESTAMP, KEY_DUEDATE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return getTask(cursor);
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                taskList.add(getTask(cursor));
            } while (cursor.moveToNext());
        }

        return taskList;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = initialiseTask(task);

        // updating row
        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }

    private Task getTask(Cursor cursor) {
        Timestamp dueDate = null;
        if(cursor.getCount() == 5)
            dueDate = Timestamp.valueOf(cursor.getString(4));

        return new Task(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Timestamp.valueOf(cursor.getString(3)), dueDate);
    }

    private ContentValues initialiseTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, task.getId());
        // TODO should handle if these are null
        values.put(KEY_HEADING, task.getHeading());
        values.put(KEY_DETAILS, task.getDetails());
        values.put(KEY_TIMESTAMP, task.getTimestamp().toString());
        if(task.getDueDate() != null) {
            values.put(KEY_DUEDATE, task.getDueDate().toString());
        }
        return values;
    }

    public Category getCategory(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIES, new String[]{KEY_ID,
                        KEY_HEADING}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        db.close();
        return new Category(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
    }
}