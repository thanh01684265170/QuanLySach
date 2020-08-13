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
        bookAdapter.setBookList(database.getAllToy(), this);
    }

    private void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetailBookActivity.openDetailToyActivity(getActivity(), i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailBookActivity.openDetailToyActivity(getActivity(), DetailBookActivity.ADD_POSITION);
            }
        });
    }

    @Override
    public void onClick(int position) {
        database.deleteToy(bookAdapter.getItem(position));
        bookAdapter.deleteBook(position);
    }
}

