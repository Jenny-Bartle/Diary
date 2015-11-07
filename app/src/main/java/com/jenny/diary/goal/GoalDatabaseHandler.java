package com.jenny.diary.goal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jenny.diary.Category;
import com.jenny.diary.Task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jenny on 18-Aug-15.
 */
public class GoalDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "goalsManager";

    // Goals table name
    private static final String TABLE_TASKS = "contacts";

    // Goals Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TASKS = "tasks";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_CATEGORY = "category";

    public GoalDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_TASKS + " TEXT, "
                + KEY_PRIORITY + " INTEGER"
                + KEY_CATEGORY + " TIMESTAMP" + ")";
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

    void addGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, goal.getId());
        values.put(KEY_NAME, goal.getName());
        values.put(KEY_TASKS, goal.getTasks());
        values.put(KEY_PRIORITY, goal.getPriority());

        if(goal.getCategory() != null) {
            values.put(KEY_CATEGORY, goal.getCategory());
        }

        // Inserting Row
        db.insert(TABLE_TASKS, null, values);
        db.close(); // Closing database connection
    }

    Goal getGoal(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_ID,
                        KEY_NAME, KEY_TASKS, KEY_PRIORITY, KEY_CATEGORY}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Goal goal = new Goal(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), Integer.valueOf(cursor.getString(2)), Timestamp.valueOf(cursor.getString(3)));
        return goal;
    }

    public List<Goal> getAllGoals() {
        List<Goal> goalList = new ArrayList<Goal>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Goal goal = new Goal();
                goal.setId(Long.parseLong(cursor.getString(0)));
                goal.setName(cursor.getString(1));
                goal.setTasks(cursor.getString(2));
                goal.setPriority(Integer.valueOf(cursor.getString(3)));
                goal.setCategory(Category.valueOf(cursor.getString(4)));
                goalList.add(goal);
            } while (cursor.moveToNext());
        }

        return goalList;
    }

    public int updateGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, goal.getId());
        values.put(KEY_NAME, goal.getName());
        values.put(KEY_TASKS, goal.getTasks());
        values.put(KEY_PRIORITY, goal.getPriority());
        if(goal.getCategory() != null) {
            values.put(KEY_CATEGORY, goal.getCategory().toString());
        }

        // updating row
        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(goal.getId()) });
    }

    public void deleteGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(goal.getId())});
        db.close();
    }

}
