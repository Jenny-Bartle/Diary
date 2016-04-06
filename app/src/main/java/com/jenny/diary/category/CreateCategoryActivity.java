package com.jenny.diary.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.jenny.diary.R;
import com.jenny.diary.Database.DiaryDatabaseHandler;
import com.jenny.diary.task.BrowseTaskActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jenny on 29-Mar-16.
 */
public class CreateCategoryActivity extends ActionBarActivity {

    private List<Long> associatedTasks = new ArrayList<>();
    public int ASSOCIATE_CATEGORY_REQUEST_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
    }

    public void submitCategory(View view) {
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        int time = (int)System.currentTimeMillis();

        EditText categoryHeader = (EditText)findViewById(R.id.create_category_title);
        Category category = new Category(time, categoryHeader.getText().toString());
        if (!associatedTasks.isEmpty()) {
            category.addTasks(associatedTasks);
        }

        db.createCategory(category);
        finish();
    }

    public void associateTasksToCategory(View v){
        Intent intent = new Intent(this, BrowseTaskActivity.class);
        startActivityForResult(intent, ASSOCIATE_CATEGORY_REQUEST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Check which request we're responding to
        if (requestCode == ASSOCIATE_CATEGORY_REQUEST_ID) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                long[] taskIds = intent.getLongArrayExtra("RESULT");
                associatedTasks.clear();
                for (long task : taskIds) {
                    associatedTasks.add(task);
                }
            }
        }
    }
}
