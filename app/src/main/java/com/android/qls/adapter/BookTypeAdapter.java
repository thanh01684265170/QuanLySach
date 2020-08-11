package com.android.qls.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.qls.R;
import com.android.qls.model.BookType;

import java.util.ArrayList;
import java.util.List;

public class BookTypeAdapter extends BaseAdapter {
    private List<BookType> typeList = new ArrayList<>();
    private OnTypeDeleteClickListener mListener;
    private int position;
    private boolean mVisibale = false;

    public void setTypeList(List<BookType> typeList, boolean Visibale) {
        this.typeList = typeList;
        this.mVisibale = Visibale;
        notifyDataSetChanged();
    }

    public void setTypeList(List<BookType> typeList, OnTypeDeleteClickListener listener) {
        this.typeList = typeList;
        this.mListener = listener;
        notifyDataSetChanged();
    }

    public void deleteType(int position) {
        typeList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return typeList.size();
    }

    @Override
    public BookType getItem(int i) {
        return typeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        position = i;
        view = View.inflate(viewGroup.getContext(), R.layout.item_book_type, null);
        BookType bookType = typeList.get(i);
        ((TextView) view.findViewById(R.id.txt_name)).setText(bookType.getName());
        ((TextView) view.findViewById(R.id.txt_describe)).setText(bookType.getDescribe());
        if (mVisibale) {
            (view.findViewById(R.id.img_delete_type)).setVisibility(View.GONE);
        }
        (view.findViewById(R.id.img_delete_type)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
            }
        });
        return view;
    }

    public interface OnTypeDeleteClickListener {
        void onClick(int position);
    }
}
