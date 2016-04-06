package com.jenny.diary.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jenny.diary.database.DiaryDatabaseHandler;
import com.jenny.diary.DateFormatUtil;
import com.jenny.diary.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jenny on 01/08/2015.
 */
public class DisplayTaskActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_display_task);

        long taskId = intent.getLongExtra("TASK", (long) 0);
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        Task task = db.readTask(taskId);
        this.setTitle(task.getHeading());

        EditText title = (EditText)findViewById(R.id.activity_display_task_title);
        EditText timestamp = (EditText)findViewById(R.id.activity_display_task_timestamp);
        EditText detail = (EditText)findViewById(R.id.activity_display_task_body);
        TextView due = (TextView)findViewById(R.id.task_display_due);

        Date date = new Date(task.getTimestamp().getTime());
        SimpleDateFormat dateFormat = DateFormatUtil.getSimpleDateFormatter(date);
        timestamp.setText(dateFormat.format(date));
        title.setText(task.getHeading());
        detail.setText(task.getDetails());
        due.setText(dateFormat.format(task.getDueDate().getTime()));
    }

    public void updateTask(View view) {
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        EditText taskHeader = (EditText)findViewById(R.id.activity_display_task_title);
        EditText taskDetail = (EditText)findViewById(R.id.activity_display_task_body);
        TextView hiddenDueDate = (TextView)findViewById(R.id.task_display_due);

        Task taskToAdd;
        if(hiddenDueDate != null) {
            Timestamp hiddenTimeDue = new Timestamp(Long.parseLong(hiddenDueDate.getText().toString()));
            taskToAdd = new Task(time, taskHeader.getText().toString(), taskDetail.getText().toString(), timestamp, hiddenTimeDue);
        } else {
            taskToAdd = new Task(time, taskHeader.getText().toString(), taskDetail.getText().toString(), timestamp);
        }

        db.updateTask(taskToAdd);

        // Go back to calling page
        finish();
    }
}
