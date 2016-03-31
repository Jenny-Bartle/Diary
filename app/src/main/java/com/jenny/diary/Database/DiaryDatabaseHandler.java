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
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_TASK_ID = "task_id";
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

        String CREATE_CATEGORIES_TASKS_TABLE = "CREATE TABLE " + TABLE_CATEGORIES_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CATEGORY_ID + " INTEGER,"
                + KEY_TASK_ID + " INTEGER,"
                + ")";
        db.execSQL(CREATE_CATEGORIES_TASKS_TABLE);
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

    // Tasks CRUD
    public void createTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = initialiseTask(task);

        // Inserting Row
        //long todo_id =
        db.insert(TABLE_TASKS, null, values);
        db.close();
        if(task.getCategories().isEmpty())
            return;

        for (Long category : task.getCategories()) {
            createCategoryTask(task.getId(), category);
        }
    }

    public Task readTask(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_ID,
                        KEY_HEADING, KEY_DETAILS, KEY_TIMESTAMP, KEY_DUEDATE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        db.close();
        if (cursor != null)
            cursor.moveToFirst();

        Task task =  getTaskFromCursor(cursor);
        task.addCategories(getAllCategoriesForTask(task.getId()));
        return task;
    }

    public List<Task> readAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task =  getTaskFromCursor(cursor);
                task.addCategories(getAllCategoriesForTask(task.getId()));
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        db.close();
        return taskList;
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues taskValues = initialiseTask(task);
        db.update(TABLE_TASKS, taskValues, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
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

    private Task getTaskFromCursor(Cursor cursor) {
        Timestamp dueDate = null;
        if(cursor.getCount() == 5)
            dueDate = Timestamp.valueOf(cursor.getString(4));

        return new Task(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Timestamp.valueOf(cursor.getString(3)), dueDate);
    }

    // Categories CRUD
    public void createCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = initialiseCategory(category);

        db.insert(TABLE_CATEGORIES, null, values);
        db.close();
        if(category.getTasks().isEmpty())
            return;

        for (Long task : category.getTasks()) {
            createCategoryTask(category.getId(), task);
        }
    }

    public Category readCategory(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIES, new String[]{KEY_ID,
                        KEY_HEADING}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        db.close();
        Category category = new Category(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        category.addTasks(getAllTasksForCategory(category.getId()));
        return category;
    }

    public void updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues categoryValues = initialiseCategory(category);
        db.update(TABLE_CATEGORIES, categoryValues, KEY_ID + " = ?",
                new String[]{String.valueOf(category.getId())});
        db.close();
    }

    public void deleteCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIES, KEY_ID + " = ?",
                new String[]{String.valueOf(category.getId())});
        db.close();
    }

    private ContentValues initialiseCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, category.getId());
        values.put(KEY_HEADING, category.getHeading());
        return values;
    }

    // Category-Tasks CRUD
    private void createCategoryTask(long categoryId, long taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_ID, categoryId);
        values.put(KEY_TASK_ID, taskId);
        db.insert(TABLE_CATEGORIES_TASKS, null, values);
        db.close(); // Closing database connection
    }

    private List<Long> getAllTasksForCategory(long categoryId) {
        List<Long> tasks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES_TASKS + "tt WHERE tt." + KEY_CATEGORY_ID +" = " + categoryId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                tasks.add(c.getLong(c.getColumnIndex(KEY_TASK_ID)));
            } while (c.moveToNext());
        }

        return tasks;
    }

    private List<Long> getAllCategoriesForTask(long taskId) {
        List<Long> tasks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES_TASKS + "tt WHERE tt." + KEY_TASK_ID +" = " + taskId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                tasks.add(c.getLong(c.getColumnIndex(KEY_CATEGORY_ID)));
            } while (c.moveToNext());
        }

        db.close();
        return tasks;
    }

    public void updateCategoryTask(long categoryId, long taskId) {
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES_TASKS + "tt WHERE tt." + KEY_CATEGORY_ID +" = " + categoryId + "AND tt." + KEY_TASK_ID + " = " + taskId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        long id = c.getLong(c.getColumnIndex(KEY_ID));

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_ID, categoryId);
        values.put(KEY_TASK_ID, taskId);

        db.update(TABLE_CATEGORIES_TASKS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteCategoryTask(long categoryId, long taskId){
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES_TASKS + "tt WHERE tt." + KEY_CATEGORY_ID +" = " + categoryId + "AND tt." + KEY_TASK_ID + " = " + taskId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        long id = c.getLong(c.getColumnIndex(KEY_ID));
        db.delete(TABLE_CATEGORIES_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}