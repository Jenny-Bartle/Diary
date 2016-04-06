package com.jenny.diary.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jenny.diary.R;
import com.jenny.diary.database.DiaryDatabaseHandler;
import com.jenny.diary.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jenny on 31-Mar-16.
 */
public class AssociateTasksActivity extends ActionBarActivity {

    private List<Long> checkedIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        List<Task> tasks = db.readAllTasks();
        LayoutInflater li = LayoutInflater.from(this);
        LinearLayout tasksView = (LinearLayout)li.inflate(R.layout.activity_associate_tasks, null);
        setContentView(tasksView);

        ScrollView scrollView = (ScrollView)findViewById(R.id.associate_tasks_scrollview);
        for (Task task : tasks) {
            TaskAssociateElement row = (TaskAssociateElement)li.inflate(R.layout.task_associate_list_row, null);
            row.setTextValues(task);
            scrollView.addView(row);
        }
    }

    public void confirmAssociateTasks(View v){

        TextView hiddenIdView = (TextView)findViewById(R.id.task_associate_list_row_id);
        checkedIds.add(Long.parseLong(hiddenIdView.getText().toString()));

        Intent returnIntent = new Intent();
        returnIntent.putExtra("RESULT", checkedIds.toArray());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void cancelAssociateTasks(View v){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
