package com.android.qls.ui;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.qls.DatabaseHelper;
import com.android.qls.adapter.PagerAdapter;
import com.android.qls.R;
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

        //khai bao view
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

        // Bat su kien vao man loc sach
        findViewById(R.id.fab_filter).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BookFilterActivity.class));
            }
        });

    }
}