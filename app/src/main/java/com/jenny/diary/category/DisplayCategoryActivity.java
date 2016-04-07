package com.jenny.diary.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jenny.diary.R;
import com.jenny.diary.database.DiaryDatabaseHandler;

/**
 * Created by Jenny on 07-Apr-16.
 */
public class DisplayCategoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_display_category);

        long categoryId = intent.getLongExtra("CATEGORY", (long) 0);
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        Category category = db.readCategory(categoryId);
        this.setTitle(category.getHeading());

        EditText title = (EditText)findViewById(R.id.activity_display_category_title);
        TextView id = (TextView)findViewById(R.id.category_id_hidden);

        title.setText(category.getHeading());
        id.setText(category.getId());
    }

    public void updateCategory(View view) {
        DiaryDatabaseHandler db = new DiaryDatabaseHandler(this);
        EditText categoryHeader = (EditText)findViewById(R.id.activity_display_category_title);
        TextView id = (TextView)findViewById(R.id.category_id_hidden);

        Integer hiddenTimeDue = Integer.parseInt(id.getText().toString());
        Category categoryToAdd = new Category(hiddenTimeDue, categoryHeader.getText().toString());

        db.updateCategory(categoryToAdd);

        // Go back to calling page
        finish();
    }
}