package com.android.qls.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.qls.DatabaseHelper;
import com.android.qls.R;
import com.android.qls.model.BookType;

public class DetailBookTypeActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    public static final int ADD_POSITION = -1;
    public static boolean EDIT = false;

    public static void openDetailTypeActivity(Activity activity, int position) {
        Intent intent = new Intent(activity, DetailBookTypeActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        activity.startActivity(intent);
    }

    private DatabaseHelper database;
    private BookType bookType;
    private TextView inputName;
    private TextView inputDescribe;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_toy_book);
        database = new DatabaseHelper(this);

        int position = getIntent().getIntExtra(EXTRA_POSITION, ADD_POSITION);
        if (position != ADD_POSITION) {
            bookType = database.getAllToyType().get(position);
            EDIT = true;
        } else EDIT = false;
        addViews();
        addListeners();
    }

    private void addViews() {
        buttonUpdate = findViewById(R.id.button_update);
        inputName = findViewById(R.id.input_name);
        inputDescribe = findViewById(R.id.input_describe);
        if (EDIT) {
            inputName.setText(bookType.getName());
            inputDescribe.setText(bookType.getDescribe());
            buttonUpdate.setText("Update");
        } else {
            buttonUpdate.setText("Add");
        }
    }

    private void addListeners() {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EDIT) editToyType();
                else addToyType();
            }
        });
    }

    private void addToyType() {
        BookType bookType = new BookType(inputName.getText().toString(), inputDescribe.getText().toString());
        database.addToyType(bookType);
        onBackPressed();
        database.closeDB();
    }
    private void editToyType() {
        bookType.setName(inputName.getText().toString());
        bookType.setDescribe(inputDescribe.getText().toString());
        database.updateToyType(bookType);
        onBackPressed();
        database.closeDB();
    }
}
