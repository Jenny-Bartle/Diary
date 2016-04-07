package com.jenny.diary.category;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;
import android.widget.TextView;

import com.jenny.diary.R;

/**
 * Created by Jenny on 07-Apr-16.
 */
public class CategoryBrowseElement extends TableRow {

    public CategoryBrowseElement(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CategoryBrowseElement(Context context) {
        super(context);
    }

    public void setTextValues(Category category) {
        TextView titleView = (TextView)findViewById(R.id.category_browse_list_row_header);
        TextView hiddenIdView = (TextView)findViewById(R.id.category_browse_list_row_id);

        titleView.setText(category.getHeading());
        hiddenIdView.setText(String.valueOf(category.getId()));
    }
}