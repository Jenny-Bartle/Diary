package com.jenny.diary;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import java.sql.Timestamp;
import java.util.Calendar;

public class CreateTodoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
    }

    public void submitTodo(View view) {
        TaskDatabaseHandler db = new TaskDatabaseHandler(this);
        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.MILLISECOND);
        EditText todoHeader = (EditText)findViewById(R.id.todo_create_item_title);
        EditText todoDetail = (EditText)findViewById(R.id.todo_create_item_body);
        Timestamp timestamp = new Timestamp(time);

        db.addTask(new Task(time, todoHeader.getText().toString(), todoDetail.getText().toString(), timestamp));

        // Go back to calling page
        finish();
    }
}
