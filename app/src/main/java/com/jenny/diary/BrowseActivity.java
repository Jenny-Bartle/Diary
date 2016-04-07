package com.jenny.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.jenny.diary.category.CreateCategoryActivity;
import com.jenny.diary.task.BrowseTaskActivity;
import com.jenny.diary.task.CreateTaskActivity;

/**
 * Created by Jenny on 29/07/2015.
 */
public class BrowseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
    }

    public void createTask(View view) {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }

    public void getTasks(View view) {
        Intent intent = new Intent(this, BrowseTaskActivity.class);
        startActivity(intent);
    }
    public void createCategory(View view) {
        Intent intent = new Intent(this, CreateCategoryActivity.class);
        startActivity(intent);
    }

    public void getCategories(View view) {
        //Intent intent = new Intent(this, BrowseCategoryActivity.class);
        //startActivity(intent);
    }
}
