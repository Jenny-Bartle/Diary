package com.jenny.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by Jenny on 29/07/2015.
 */
public class BrowseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
    }

    public void createTodo(View view) {
        Intent intent = new Intent(this, CreateTodoActivity.class);
        startActivity(intent);
    }

    public void getTodos(View view) {
        Intent intent = new Intent(this, BrowseTodoActivity.class);
        startActivity(intent);
    }

}
