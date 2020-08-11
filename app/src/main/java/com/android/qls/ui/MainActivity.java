package com.android.qls.ui;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.android.qls.DatabaseHelper;
import com.android.qls.adapter.PagerAdapter;
import com.android.qls.R;
import com.android.qls.model.Book;
import com.android.qls.model.BookType;
import com.google.android.material.tabs.TabLayout;

import static com.google.android.material.tabs.TabLayout.*;

public class MainActivity extends FragmentActivity {
    TabLayout tabLayout;
    ViewPager pager;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(getApplicationContext());
//        initToyList();
//        initTypeList();
        
        tabLayout = findViewById(R.id.tablayout);
        pager = findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText("Truyện"));
        tabLayout.addTab(tabLayout.newTab().setText("Thể loại"));
        tabLayout.setTabGravity(GRAVITY_FILL);

        pager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        pager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });

    }

    public void initTypeList() {
        if (db.getAllToyType().size() == 0) {
            db.addToyType(new BookType("Trinh thám", "Nói về thám tử phá án"));
            db.addToyType(new BookType("Ngôn tình", "Nói về tình yêu"));
        }
    }

    public void initToyList() {
        if (db.getAllToy().size() == 0) {
            db.addBook(new Book(1, "Conan", 001, "conan", R.drawable.sach, "3 sao"));
            db.addBook(new Book(2, "Love", 002, "love", R.drawable.sach, "3 sao"));
            db.addBook(new Book(3, "Sherlock holmes", 001, "holmes", R.drawable.sach, "5 sao"));
        }
    }
}