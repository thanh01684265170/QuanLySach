package com.android.qls.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.qls.DatabaseHelper;
import com.android.qls.R;
import com.android.qls.adapter.BookAdapter;
import com.android.qls.adapter.BookTypeAdapter;
import com.android.qls.model.BookType;

import java.util.ArrayList;
import java.util.List;

public class BookFilterActivity extends AppCompatActivity implements BookAdapter.OnBookDeleteClickListener {

    private List<BookType> bookTypes;
    private List<BookType> bookRating;
    private DatabaseHelper database;
    private BookType bookType;
    private BookType bookStar;
    private TextView tvType;
    private TextView tvRating;
    private ListView listFilter;
    private Button btnFilter;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initView();
        initData();
        handleFilter();
    }

    private void initView() {
        tvType = findViewById(R.id.tv_type);
        tvRating = findViewById(R.id.tv_rating);
        listFilter = findViewById(R.id.list_filter);
        btnFilter = findViewById(R.id.btn_filter);
    }

    private void initData() {
        bookAdapter = new BookAdapter();
        listFilter.setAdapter(bookAdapter);
        database = new DatabaseHelper(this);
        bookTypes = new ArrayList<>();
        bookRating = new ArrayList<>();
        bookRating.add(new BookType(1, "1 Sao"));
        bookRating.add(new BookType(2, "2 Sao"));
        bookRating.add(new BookType(3, "3 Sao"));
        bookRating.add(new BookType(4, "4 Sao"));
        bookRating.add(new BookType(5, "5 Sao"));
        bookTypes = database.getAllBookType();
    }

    private void handleFilter() {
        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(BookFilterActivity.this);
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View content = inflater.inflate(R.layout.custom_dialog_type, null);
                dialog.setContentView(content);
                ListView listView = content.findViewById(R.id.lv_type);

                BookTypeAdapter typeAdapter = new BookTypeAdapter();
                typeAdapter.setTypeList(bookTypes, true);
                listView.setAdapter(typeAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        bookType = bookTypes.get(i);
                        tvType.setText(bookType.getName());
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        //Hien thi danh sach rating
        tvRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(BookFilterActivity.this);
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View content = inflater.inflate(R.layout.custom_dialog_type, null);
                dialog.setContentView(content);
                ListView listView = content.findViewById(R.id.lv_type);

                BookTypeAdapter typeAdapter = new BookTypeAdapter();
                typeAdapter.setTypeList(bookRating, true);
                listView.setAdapter(typeAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        bookStar = bookRating.get(i);
                        tvRating.setText(bookStar.getName());
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookAdapter.setBookList(database.getBookByStartAndType(bookStar.getId(), bookType.getName()));
            }
        });
    }

    @Override
    public void onClick(int position) {

    }
}
