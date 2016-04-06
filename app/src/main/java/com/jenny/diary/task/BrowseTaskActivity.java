package com.jenny.diary.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jenny.diary.database.DiaryDatabaseHandler;
import com.jenny.diary.R;

import java.util.List;

/**
 * Created by Jenny on 01/08/2015.
 */
public class BrowseTaskActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        List<Task> tasks = db.readAllTasks();
        LayoutInflater li = LayoutInflater.from(this);
        LinearLayout tasksView = (LinearLayout)li.inflate(R.layout.activity_browse_tasks, null);
        setContentView(tasksView);

        for (Task task : tasks) {
            TaskBrowseElement row = (TaskBrowseElement)li.inflate(R.layout.task_browse_list_row, null);
            row.setTextValues(task);
            tasksView.addView(row);
        }
    }

    public void displayTask(View view) {
        TextView hiddenIdView = (TextView)findViewById(R.id.task_browse_list_row_id);
        Intent intent = new Intent(this, DisplayTaskActivity.class);
        intent.putExtra("TASK", Long.parseLong(hiddenIdView.getText().toString()));
        startActivity(intent);
    }

    public void deleteTask(View view){
        TextView hiddenIdView = (TextView)findViewById(R.id.task_browse_list_row_id);
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        Task task = db.readTask(Long.parseLong(hiddenIdView.getText().toString()));
        TaskBrowseElement parentView = (TaskBrowseElement)view.getParent().getParent().getParent();
        ((ViewGroup)parentView.getParent()).removeView(parentView);
        db.deleteTask(task);
    }
}
