package com.jenny.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by Jenny on 01/08/2015.
 */
public class BrowseTodoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskDatabaseHandler db = new TaskDatabaseHandler(this);
        List<Task> tasks = db.getAllTasks();
        LayoutInflater li = LayoutInflater.from(this);
        LinearLayout todosView = (LinearLayout)li.inflate(R.layout.activity_browse_todos, null);
        setContentView(todosView);

        for (Task task : tasks) {
            TodoBrowseElement row = (TodoBrowseElement)li.inflate(R.layout.todo_browse_list_row, null);
            row.setTextValues(task);
            todosView.addView(row);
        }
    }

    public void displayTodo(View view) {
        Intent intent = new Intent(this, DisplayTodoActivity.class);
        startActivity(intent);
    }

}
