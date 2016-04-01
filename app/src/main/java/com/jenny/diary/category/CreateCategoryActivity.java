package com.jenny.diary.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.jenny.diary.R;
import com.jenny.diary.database.DiaryDatabaseHandler;
import com.jenny.diary.task.BrowseTaskActivity;

/**
 * Created by Jenny on 29-Mar-16.
 */
public class CreateCategoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
    }

    public void submitCategory(View view) {
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        int time = (int)System.currentTimeMillis();

        EditText categoryHeader = (EditText)findViewById(R.id.create_category_title);
        Category taskToAdd = new Category(time, categoryHeader.getText().toString());

        db.createCategory(taskToAdd);

        // Go back to calling page
        finish();
    }

    public void associateTasksToCategory(View v){
        Intent intent = new Intent(this, BrowseTaskActivity.class);
        startActivity(intent);
    }
}
