package com.jenny.diary.task;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;
import android.widget.TextView;

import com.jenny.diary.DateFormatUtil;
import com.jenny.diary.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jenny on 01/08/2015.
 */
public class TaskBrowseElement extends TableRow {

    public TaskBrowseElement(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TaskBrowseElement(Context context) {
        super(context);
    }

    public void setTextValues(Task task) {
        TextView titleView = (TextView)findViewById(R.id.task_browse_list_row_header);
        TextView dateView = (TextView)findViewById(R.id.task_browse_list_row_time);
        TextView hiddenIdView = (TextView)findViewById(R.id.task_browse_list_row_id);

        titleView.setText(task.getHeading());
        Date date = new Date(task.getTimestamp().getTime());
        SimpleDateFormat dateFormat = DateFormatUtil.getSimpleDateFormatter(date);
        dateView.setText(dateFormat.format(date));
        hiddenIdView.setText(String.valueOf(task.getTimestamp().getTime()));
    }

}
