package com.android.qls.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.android.qls.DatabaseHelper;
import com.android.qls.R;
import com.android.qls.adapter.BookAdapter;

public class BookFragment extends Fragment implements BookAdapter.OnBookDeleteClickListener {
    private DatabaseHelper database;
    private ListView listView;
    private Button button;
    private BookAdapter bookAdapter = new BookAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, container, false);
        listView = v.findViewById(R.id.list);
        button = v.findViewById(R.id.button_add);
        listView.setAdapter(bookAdapter);
        addListener();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        database = new DatabaseHelper(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Lay danh sach ra hien thi
        bookAdapter.setBookList(database.getAllBook(), this);
    }

    private void addListener() {
        //Xu ly su kien cham vao cac phan tu cua danh sach
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetailBookActivity.openDetailBookActivity(getActivity(), i);
            }
        });

        // Xu ly su kien cham vao nut them
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailBookActivity.openDetailBookActivity(getActivity(), DetailBookActivity.ADD_POSITION);
            }
        });
    }

    @Override
    public void onClick(int position) {
        //Xu ly viec xoa sach
        database.deleteBook(bookAdapter.getItem(position));
        bookAdapter.deleteBook(position);
    }
}

