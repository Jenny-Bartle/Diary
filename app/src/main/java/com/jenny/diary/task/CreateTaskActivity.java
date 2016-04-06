package com.jenny.diary.task;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jenny.diary.Database.DiaryDatabaseHandler;
import com.jenny.diary.DatePickerFragment;
import com.jenny.diary.R;

import java.sql.Timestamp;

public class CreateTaskActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
    }

    public void submitTask(View view) {
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        long time = System.currentTimeMillis();
        EditText taskHeader = (EditText)findViewById(R.id.create_task_title);
        EditText taskDetail = (EditText)findViewById(R.id.create_task_body);
        Timestamp timestamp = new Timestamp(time);
        TextView hiddenDueDate = (TextView)findViewById(R.id.task_create_due_hidden);

        Task taskToAdd;
        if(hiddenDueDate.getText() != "") {
            Timestamp hiddenTimeDue = new Timestamp(Long.parseLong(hiddenDueDate.getText().toString()));
            taskToAdd = new Task(time, taskHeader.getText().toString(), taskDetail.getText().toString(), timestamp, hiddenTimeDue);
        } else {
            taskToAdd = new Task(time, taskHeader.getText().toString(), taskDetail.getText().toString(), timestamp);
        }

        db.createTask(taskToAdd);

        // Go back to calling page
        finish();
    }

    public void pickDate(View v) {
        TextView dueDate = (TextView)findViewById(R.id.task_create_due);
        TextView hiddenDueDate = (TextView)findViewById(R.id.task_create_due_hidden);
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setDateViews(hiddenDueDate, dueDate);
        datePicker.show(this.getFragmentManager(), "datePicker");
    }
}
