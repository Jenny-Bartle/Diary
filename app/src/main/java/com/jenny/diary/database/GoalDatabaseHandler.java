package com.jenny.diary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jenny.diary.category.Category;
import com.jenny.diary.goal.Goal;

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
    private static final String TABLE_GOALS = "goals";

    // Goals Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TASKS = "tasks";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_CATEGORY = "category";
    private final Context context;

    public GoalDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GOALS_TABLE = "CREATE TABLE " + TABLE_GOALS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_TASKS + " ARRAY, "
                + KEY_PRIORITY + " INTEGER"
                + KEY_CATEGORY + " INTEGER" + ")";
        db.execSQL(CREATE_GOALS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);

        // Create tables again
        onCreate(db);
    }

    public void addGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = initialiseContentValues(goal);

        // Inserting Row
        db.insert(TABLE_GOALS, null, values);
        db.close(); // Closing database connection
    }

    public Goal getGoal(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GOALS, new String[]{KEY_ID,
                        KEY_NAME, KEY_TASKS, KEY_PRIORITY, KEY_CATEGORY}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return constructGoal(cursor);
    }

    public List<Goal> getAllGoals() {
        List<Goal> goalList = new ArrayList<Goal>();
        String selectQuery = "SELECT  * FROM " + TABLE_GOALS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                goalList.add(constructGoal(cursor));
            } while (cursor.moveToNext());
        }

        return goalList;
    }

    public int updateGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = initialiseContentValues(goal);

        // updating row
        return db.update(TABLE_GOALS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(goal.getId()) });
    }

    public void deleteGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GOALS, KEY_ID + " = ?",
                new String[]{String.valueOf(goal.getId())});
        db.close();
    }

    private Goal constructGoal(Cursor cursor) {
        Category category = null;
        if(cursor.getCount() == 5) {
            DiaryDatabaseHandler categoryDatabaseHandler = new DiaryDatabaseHandler(context);
            category = categoryDatabaseHandler.readCategory(Integer.parseInt(cursor.getString(4)));
        }

        List tasks = new ArrayList();
        //tasks = (Array)cursor.getString(2);
        return new Goal(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                tasks,
                Integer.valueOf(cursor.getString(3)),
                category);
    }

    private ContentValues initialiseContentValues(Goal goal) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, goal.getId());
        values.put(KEY_NAME, goal.getName());
        values.put(KEY_TASKS, goal.getTasks().toArray().toString());
        values.put(KEY_PRIORITY, goal.getPriority());
        if(goal.getCategory() != null) {
            values.put(KEY_CATEGORY, goal.getCategory().getId());
        }
        return values;
    }
}
