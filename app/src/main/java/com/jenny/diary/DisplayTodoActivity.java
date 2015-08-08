package com.jenny.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by Jenny on 01/08/2015.
 */
public class DisplayTodoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();



        long taskId = intent.getLongExtra("TASK", (long) 0);
        TaskDatabaseHandler db = new TaskDatabaseHandler(this);
        Task task = db.getTask(taskId);
        this.setTitle(task.getHeading());

        TextView title = (TextView)findViewById(R.id.activity_display_todo_title);
        TextView date = (TextView)findViewById(R.id.activity_display_todo_date);
        TextView detail = (TextView)findViewById(R.id.activity_display_todo_body);

        System.out.println(task.getHeading());

        date.setText(task.getTimestamp().toString());
        title.setText(task.getHeading());
        detail.setText(task.getDetails());
    }
}
