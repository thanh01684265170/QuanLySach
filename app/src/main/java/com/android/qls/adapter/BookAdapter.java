package com.android.qls.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.qls.R;
import com.android.qls.model.Book;
import com.android.qls.model.BookType;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends BaseAdapter {
    private List<Book> bookList = new ArrayList<>();
    private OnBookDeleteClickListener mListener;
    private int position;


    public void setBookList(List<Book> bookList, OnBookDeleteClickListener listener) {
        this.bookList = bookList;
        this.mListener = listener;
        notifyDataSetChanged();
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
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

        ((TextView) view.findViewById(R.id.txt_name)).setText(book.getName());
        ((TextView) view.findViewById(R.id.txt_description)).setText(book.getDescription());
        ((TextView) view.findViewById(R.id.txt_type)).setText(book.getTypeName());
        ((RatingBar) view.findViewById(R.id.rating_bar_item)).setRating(book.getReview());
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
