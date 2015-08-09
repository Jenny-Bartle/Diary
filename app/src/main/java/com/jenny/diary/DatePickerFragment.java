package com.jenny.diary;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Jenny on 09-Aug-15.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private TextView hiddenTodoDateView;
    private TextView visibleTodoDateView;

    public void setDateViews(TextView hiddenTodoDateView, TextView visibleTodoDateView) {
        this.hiddenTodoDateView = hiddenTodoDateView;
        this.visibleTodoDateView = visibleTodoDateView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        GregorianCalendar gc = new GregorianCalendar(year + 1900, month, day);
        hiddenTodoDateView.setText(Long.toString(gc.getTimeInMillis()));
        SimpleDateFormat formatter = DateFormatUtil.getSimpleDateFormatter(gc.getTime());
        visibleTodoDateView.setText(formatter.format(gc.getTime()).toString());
    }

}
