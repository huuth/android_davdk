package com.example.hawking.geturltest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by thuytien on 4/17/17.
 */

public class PageSlideAdapter extends FragmentPagerAdapter {
    public PageSlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Slide01Fragment();
            case 1: return new Slide02Fragment();
            case 2: return new Slide03Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
