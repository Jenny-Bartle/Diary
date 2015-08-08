package com.jenny.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jenny on 01/08/2015.
 */
public class DisplayTodoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_display_todo);

        long taskId = intent.getLongExtra("TASK", (long) 0);
        TaskDatabaseHandler db = new TaskDatabaseHandler(this);
        Task task = db.getTask(taskId);
        this.setTitle(task.getHeading());

        EditText title = (EditText)findViewById(R.id.activity_display_todo_title);
        EditText timestamp = (EditText)findViewById(R.id.activity_display_todo_timestamp);
        EditText detail = (EditText)findViewById(R.id.activity_display_todo_body);

        Date date = new Date(task.getTimestamp().getTime());
        SimpleDateFormat dateFormat = DateFormatUtil.getSimpleDateFormatter(date);
        timestamp.setText(dateFormat.format(date));
        title.setText(task.getHeading());
        detail.setText(task.getDetails());
    }

    public void updateTodo(){

    }
}
