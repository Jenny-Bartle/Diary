package com.jenny.diary.category;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;
import android.widget.TextView;

import com.jenny.diary.R;
import com.jenny.diary.task.Task;

/**
 * Created by Jenny on 01-Apr-16.
 */
public class TaskAssociateElement extends TableRow {

    public TaskAssociateElement(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TaskAssociateElement(Context context) {
        super(context);
    }

    public void setTextValues(Task task) {
        TextView titleView = (TextView)findViewById(R.id.task_associate_list_row_header);
        TextView hiddenIdView = (TextView)findViewById(R.id.task_associate_list_row_id);

        titleView.setText(task.getHeading());
        hiddenIdView.setText(String.valueOf(task.getId()));
    }
}
