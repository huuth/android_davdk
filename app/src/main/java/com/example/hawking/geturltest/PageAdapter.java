package com.example.hawking.geturltest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by thuytien on 4/17/17.
 */

public class PageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public PageAdapter(FragmentManager fm, List<Fragment> frag) {
        super(fm);
        this.fragments=frag;
    }
    @Override
    public Fragment getItem(int position) {
        int pos = position%4;
        return this.fragments.get(pos);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
