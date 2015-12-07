package com.jenny.diary.todo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jenny.diary.DatePickerFragment;
import com.jenny.diary.R;
import com.jenny.diary.Task;

import java.sql.Timestamp;

public class CreateTodoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
    }

    public void submitTodo(View view) {
        TaskDatabaseHandler db = new TaskDatabaseHandler(this);
        long time = System.currentTimeMillis();
        EditText todoHeader = (EditText)findViewById(R.id.create_todo_title);
        EditText todoDetail = (EditText)findViewById(R.id.create_todo_body);
        TextView hiddenDueDate = (TextView)findViewById(R.id.todo_create_due_hidden);
        Timestamp timestamp = new Timestamp(time);

        Task taskToAdd;
        if(hiddenDueDate != null) {
            Timestamp hiddenTimeDue = new Timestamp(Long.parseLong(hiddenDueDate.getText().toString()));
            taskToAdd = new Task(time, todoHeader.getText().toString(), todoDetail.getText().toString(), timestamp, hiddenTimeDue);
        } else {
            taskToAdd = new Task(time, todoHeader.getText().toString(), todoDetail.getText().toString(), timestamp);
        }

        db.addTask(taskToAdd);

        // Go back to calling page
        finish();
    }

    public void pickDate(View v) {
        TextView dueDate = (TextView)findViewById(R.id.todo_create_due);
        TextView hiddenDueDate = (TextView)findViewById(R.id.todo_create_due_hidden);
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setDateViews(hiddenDueDate, dueDate);
        datePicker.show(this.getFragmentManager(), "datePicker");
    }
}
