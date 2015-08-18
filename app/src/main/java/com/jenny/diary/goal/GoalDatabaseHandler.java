package com.jenny.diary.goal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String KEY_HEADING = "name";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_DUEDATE = "due";

    public GoalDatabaseHandler(Context context) {
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

    void addGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, goal.getID());
        values.put(KEY_HEADING, goal.getHeading());
        values.put(KEY_DETAILS, goal.getDetails());
        values.put(KEY_TIMESTAMP, goal.getTimestamp().toString());

        if(goal.getDueDate() != null) {
            values.put(KEY_DUEDATE, goal.getDueDate().toString());
        }

        // Inserting Row
        db.insert(TABLE_TASKS, null, values);
        db.close(); // Closing database connection
    }

    Goal getGoal(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_ID,
                        KEY_HEADING, KEY_DETAILS, KEY_TIMESTAMP, KEY_DUEDATE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Goal goal = new Goal(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Timestamp.valueOf(cursor.getString(3)));
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
                goal.setID(Long.parseLong(cursor.getString(0)));
                goal.setHeading(cursor.getString(1));
                goal.setDetails(cursor.getString(2));
                goal.setTimestamp(Timestamp.valueOf(cursor.getString(3)));
                goal.setDueDate(Timestamp.valueOf(cursor.getString(4)));
                goalList.add(goal);
            } while (cursor.moveToNext());
        }

        return goalList;
    }

    public int updateGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, goal.getID());
        values.put(KEY_HEADING, goal.getHeading());
        values.put(KEY_DETAILS, goal.getDetails());
        values.put(KEY_TIMESTAMP, goal.getTimestamp().toString());
        if(goal.getDueDate() != null) {
            values.put(KEY_DUEDATE, goal.getDueDate().toString());
        }

        // updating row
        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(goal.getID()) });
    }

    public void deleteGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(goal.getID())});
        db.close();
    }

}
