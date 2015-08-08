package com.jenny.diary;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import java.sql.Timestamp;

public class CreateTodoActivity extends ActionBarActivity {

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
        Timestamp timestamp = new Timestamp(time);

        db.addTask(new Task(time, todoHeader.getText().toString(), todoDetail.getText().toString(), timestamp));

        // Go back to calling page
        finish();
    }
}
