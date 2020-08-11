package com.android.qls.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.qls.ui.BookFragment;
import com.android.qls.ui.BookTypeFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int countTab;

    public PagerAdapter(FragmentManager fm, int countTab) {
        super(fm);
        this.countTab = countTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BookFragment bookFragment = new BookFragment();
                return bookFragment;
            case 1:
                BookTypeFragment bookTypeFragment = new BookTypeFragment();
                return bookTypeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
