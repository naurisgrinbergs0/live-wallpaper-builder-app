package com.wallpaper.livewallpaper.ListAdapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> tabs;

    public TabsPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        byte size = 0;
        if(tabs != null)
            size = (byte) tabs.size();
        return size;
    }

    @Override
    public Fragment getItem(int position) {
        return new Fragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(tabs != null)
            return tabs.get(position);
        return null;
    }

    public void setTabs(String[] tabs){
        this.tabs = new ArrayList<>();
        Collections.addAll(this.tabs, tabs);
    }
}
