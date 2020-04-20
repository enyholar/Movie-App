/*
 * Copyright (C) 2015 Naman Dwivedi
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.behruz.magmovie.ui.fragment;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.behruz.magmovie.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static final String[] tabArray = {"LATEST", "TRENDING", "NOW SHOWING", "UPCOMING"};//Tab title array

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_main, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        return rootView;

    }

    private void setUpCustomTabs() {
        for (int i = 0; i < tabArray.length; i++) {
            TextView customTab = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_view, null);//get custom view
            customTab.setText(tabArray[i]);//set text over view
            TabLayout.Tab tab = tabLayout.getTabAt(i);//get tab via position
            if (tab != null)
                tab.setCustomView(customTab);//set custom view
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (viewPager != null) {
//            setupViewPager(viewPager);
//            viewPager.setOffscreenPageLimit(4);
//        }
//        tabLayout.setupWithViewPager(viewPager);
//        setUpCustomTabs();
    }

    private void setupViewPager(ViewPager viewPager) {
//        Adapter adapter = new Adapter(getChildFragmentManager());
//        adapter.addFragment(new MovieAutoScrollFragment(), this.getString(R.string.most_popular));
//        adapter.addFragment(new MostRatedMovieActivity(), this.getString(R.string.top_rated));
//        adapter.addFragment(new AiringMovieActivity(), this.getString(R.string.now_playing));
//        adapter.addFragment(new UpcomingMovieActivity(), "");
//        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
    }


    //To search only for music not involved radio module
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
