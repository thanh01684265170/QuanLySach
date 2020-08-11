package com.android.qls.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.qls.R;
import com.android.qls.model.Book;
import com.android.qls.model.BookType;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends BaseAdapter {
    private List<Book> bookList = new ArrayList<>();
    private List<BookType> typeList = new ArrayList<>();
    private OnBookDeleteClickListener mListener;
    private int position;


    public void setBookList(List<Book> bookList, List<BookType> typeList, OnBookDeleteClickListener listener) {
        this.bookList = bookList;
        this.typeList = typeList;
        this.mListener = listener;
        notifyDataSetChanged();
    }

    public void deleteBook(int position) {
        bookList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Book getItem(int i) {
        return bookList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        position = i;
        view = View.inflate(viewGroup.getContext(), R.layout.item_book, null);
        Book book = bookList.get(i);
        String nameType = "";
        for (int j = 0; j < typeList.size(); j++) {
            if (book.getIdType() == typeList.get(j).getId()) {
                nameType = typeList.get(j).getName();
            }
        }
        ((TextView) view.findViewById(R.id.txt_name)).setText(book.getName());
        ((TextView) view.findViewById(R.id.txt_description)).setText(book.getDescription());
        ((TextView) view.findViewById(R.id.txt_type)).setText(nameType);
        ((TextView) view.findViewById(R.id.txt_review)).setText(book.getReview());
        ((ImageView) view.findViewById(R.id.img_book)).setImageURI(book.getImageUri());

        (view.findViewById(R.id.img_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
            }
        });
        return view;
    }

    public interface OnBookDeleteClickListener {
        void onClick(int position);
    }
}
