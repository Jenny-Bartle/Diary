package com.jenny.diary.category;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.jenny.diary.R;
import com.jenny.diary.database.DiaryDatabaseHandler;
import com.jenny.diary.task.Task;
import com.jenny.diary.task.TaskBrowseElement;

import java.util.List;

/**
 * Created by Jenny on 31-Mar-16.
 */
public class AssociateTasks extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        List<Task> tasks = db.readAllTasks();
        LayoutInflater li = LayoutInflater.from(this);
        LinearLayout todosView = (LinearLayout)li.inflate(R.layout.activity_associate_tasks, null);
        setContentView(todosView);

        for (Task task : tasks) {
            TaskBrowseElement row = (TaskBrowseElement)li.inflate(R.layout.task_associate_list_row, null);
            row.setTextValues(task);
            todosView.addView(row);
        }
    }

}
