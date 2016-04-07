package com.jenny.diary.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jenny.diary.R;
import com.jenny.diary.database.DiaryDatabaseHandler;

import java.util.List;

/**
 * Created by Jenny on 07-Apr-16.
 */
public class BrowseCategoriesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        List<Category> categories = db.readAllCategories();
        LayoutInflater li = LayoutInflater.from(this);
        LinearLayout categoriesView = (LinearLayout)li.inflate(R.layout.activity_browse_categories, null);
        setContentView(categoriesView);

        for (Category category : categories) {
            CategoryBrowseElement row = (CategoryBrowseElement)li.inflate(R.layout.category_browse_list_row, null);
            row.setTextValues(category);
            categoriesView.addView(row);
        }
    }

    public void displayCategory(View view) {
        TextView hiddenIdView = (TextView)findViewById(R.id.category_browse_list_row_id);
        Intent intent = new Intent(this, DisplayCategoryActivity.class);
        intent.putExtra("CATEGORY", Long.parseLong(hiddenIdView.getText().toString()));
        startActivity(intent);
    }

    public void deleteTask(View view){
        TextView hiddenIdView = (TextView)findViewById(R.id.category_browse_list_row_id);
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        Category category = db.readCategory(Long.parseLong(hiddenIdView.getText().toString()));
        CategoryBrowseElement parentView = (CategoryBrowseElement)view.getParent().getParent().getParent();
        ((ViewGroup)parentView.getParent()).removeView(parentView);
        db.deleteCategory(category);
    }
}
