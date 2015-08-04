package com.jenny.diary;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Jenny on 01/08/2015.
 */
public class TodoBrowseElement extends TableRow {

    public TodoBrowseElement(Context context, Task task) {
        super(context);
        TextView title = (TextView)findViewById(R.id.todo_browse_list_row_header);
        TextView date = (TextView)findViewById(R.id.todo_browse_list_row_time);

        title.setText(task.getHeading());
        date.setText(new Date(task.getTimestamp().getTime()).toString());
    }

}
