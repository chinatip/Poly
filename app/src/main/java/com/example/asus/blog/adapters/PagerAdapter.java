package com.example.asus.blog.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.asus.blog.activities.pages.Following;
import com.example.asus.blog.activities.pages.History;
import com.example.asus.blog.activities.pages.Reminder;
import com.example.asus.blog.activities.pages.Timeline;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Timeline tab1 = new Timeline();
                return tab1;
            case 1:
                Following tab2 = new Following();
                return tab2;
            case 2:
                Reminder tab3 = new Reminder();
                return tab3;
            case 3:
                History tab4 = new History();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}