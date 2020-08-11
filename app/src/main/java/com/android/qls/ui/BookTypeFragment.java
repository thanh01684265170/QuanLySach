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
        typeAdapter.setTypeList(database.getAllToyType(), this);
    }

    private void addListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetailBookTypeActivity.openDetailTypeActivity(getActivity(), i);
            }
        });

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                database.deleteToyType(typeAdapter.getItem(i));
//                typeAdapter.deleteType(i);
//                return false;
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailBookTypeActivity.openDetailTypeActivity(getActivity(), DetailBookTypeActivity.ADD_POSITION);
            }
        });
    }

    @Override
    public void onClick(int position) {
        database.deleteToyType(typeAdapter.getItem(position));
        typeAdapter.deleteType(position);
    }
}