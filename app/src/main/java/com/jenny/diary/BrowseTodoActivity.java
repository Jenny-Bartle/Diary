package com.jenny.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        TextView hiddenIdView = (TextView)findViewById(R.id.todo_browse_list_row_id);
        Intent intent = new Intent(this, DisplayTodoActivity.class);
        intent.putExtra("TASK", Long.parseLong(hiddenIdView.getText().toString()));
        startActivity(intent);
    }

    public void deleteTodo(View view){
        TextView hiddenIdView = (TextView)findViewById(R.id.todo_browse_list_row_id);
        TaskDatabaseHandler db = new TaskDatabaseHandler(this);
        Task task = db.getTask(Long.parseLong(hiddenIdView.getText().toString()));
        TodoBrowseElement parentView = (TodoBrowseElement)view.getParent().getParent().getParent();
        ((ViewGroup)parentView.getParent()).removeView(parentView);
        db.deleteTask(task);
    }
}
