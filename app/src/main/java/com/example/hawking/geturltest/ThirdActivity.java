package com.example.hawking.geturltest;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuytien on 4/17/17.
 */

public class ThirdActivity extends AppCompatActivity {
    private TabLayout mTabs;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTabs = (TabLayout) findViewById(R.id.tabs);
        for (int i = 0; i < 4; i++) {
            mTabs.addTab(mTabs.newTab());
        }

        List<Fragment> listFrag = new ArrayList<>();
        listFrag.add(new hanhtrinhFragment());
        listFrag.add(new baoduongFragment());
        listFrag.add(new accountFragment());

        mPagerAdapter = new PageAdapter(this.getSupportFragmentManager(), listFrag);
        mViewPager.setAdapter(mPagerAdapter);
        mTabs.setupWithViewPager(mViewPager);
        String[] strTab = { "Hanh trinh", "Thong Tin Bao duong", "Account"};
        for (int i = 0; i < 3; i++) {
            mTabs.getTabAt(i).setText(strTab[i]);
        }


    }
}
