package com.jenny.diary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TableRow;
import android.widget.TextView;

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
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.todo_browse_list_row, context);

        TextView title = (TextView)findViewById(R.id.todo_browse_list_row_header);
        TextView date = (TextView)findViewById(R.id.todo_browse_list_row_time);

        title.setText(task.getHeading());
        date.setText(new Date(task.getTimestamp().getTime()).toString());
    }

}
