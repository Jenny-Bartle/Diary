package com.jenny.diary;

import android.content.Context;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
        LayoutInflater inflater = LayoutInflater.from(getContext());

        TextView titleView = (TextView)findViewById(R.id.todo_browse_list_row_header);
        TextView dateView = (TextView)findViewById(R.id.todo_browse_list_row_time);

        titleView.setText(task.getHeading());
        Date date = new Date(task.getTimestamp().getTime());
        SimpleDateFormat dateFormat = getSimpleDateFormatter(date);
        dateView.setText(dateFormat.format(date));
    }

    private SimpleDateFormat getSimpleDateFormatter(Date date) {

        Time startTime = new Time();
        startTime.set(date.getTime());
        int startDay = Time.getJulianDay(date.getTime(), startTime.gmtoff);

        Date now = new Date(System.currentTimeMillis());
        Time currentTime = new Time();
        currentTime.set(now.getTime());
        int currentDay = Time.getJulianDay(now.getTime(), currentTime.gmtoff);

        int days = Math.abs(currentDay - startDay);

        if (days == 0) {
            return new SimpleDateFormat("h:mm a");
        } else if (days <7) {
            return new SimpleDateFormat("EEE");
        } else {
            return new SimpleDateFormat("d MMM yy");
        }
    }
}
