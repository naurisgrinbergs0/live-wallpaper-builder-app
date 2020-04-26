package com.wallpaper.livewallpaper.ListAdapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wallpaper.livewallpaper.Fragments.WidgetsFragment;

import java.util.ArrayList;
import java.util.Collections;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> tabs;

    private WidgetsFragment.OnActionListener widgetsFragmentOnActionListener;

    public TabsPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
        switch (position){
            case 0:{
                WidgetsFragment fragment = new WidgetsFragment();
                fragment.setOnActionListener(widgetsFragmentOnActionListener);
                return fragment;
            }
            default:{
                return new Fragment();
            }
        }
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

    public void setWidgetsFragmentOnActionListener(WidgetsFragment.OnActionListener widgetsFragmentOnActionListener){
        this.widgetsFragmentOnActionListener = widgetsFragmentOnActionListener;
    }
}
