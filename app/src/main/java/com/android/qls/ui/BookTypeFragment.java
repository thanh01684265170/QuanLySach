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
import com.android.qls.adapter.BookTypeAdapter;

public class BookTypeFragment extends Fragment implements BookTypeAdapter.OnTypeDeleteClickListener {
    private DatabaseHelper database;
    private ListView listView;
    private Button button;
    private BookTypeAdapter typeAdapter = new BookTypeAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, container, false);
        listView = v.findViewById(R.id.list);
        button = v.findViewById(R.id.button_add);
        listView.setAdapter(typeAdapter);
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
        //Lay danh sach loai truyen
        typeAdapter.setTypeList(database.getAllBookType(), this);
    }

    private void addListener() {
        //Bat su kien cham vao tung the loai truyen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetailBookTypeActivity.openDetailTypeActivity(getActivity(), i);
            }
        });

        //Bat su kien them loai truyen
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailBookTypeActivity.openDetailTypeActivity(getActivity(), DetailBookTypeActivity.ADD_POSITION);
            }
        });
    }

    @Override
    public void onClick(int position) {
        //Xu ly su kien xoa tung the loai truyen
        database.deleteBookType(typeAdapter.getItem(position));
        typeAdapter.deleteType(position);
    }
}