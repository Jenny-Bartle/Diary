package com.jenny.diary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jenny on 01/08/2015.
 */
public class TodoBrowseElement extends TableRow {

    public TodoBrowseElement(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TodoBrowseElement(Context context) {
        super(context);
    }

    public void setTextValues(Task task) {
        TextView titleView = (TextView)findViewById(R.id.todo_browse_list_row_header);
        TextView dateView = (TextView)findViewById(R.id.todo_browse_list_row_time);
        TextView hiddenIdView = (TextView)findViewById(R.id.todo_browse_list_row_id);

        titleView.setText(task.getHeading());
        Date date = new Date(task.getTimestamp().getTime());
        SimpleDateFormat dateFormat = DateFormatUtil.getSimpleDateFormatter(date);
        dateView.setText(dateFormat.format(date));
        hiddenIdView.setText(String.valueOf(task.getTimestamp().getTime()));
    }

}
