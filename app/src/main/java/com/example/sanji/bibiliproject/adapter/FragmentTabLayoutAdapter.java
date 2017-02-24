package com.example.sanji.bibiliproject.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by sanji on 2017/1/30.
 */
public class FragmentTabLayoutAdapter extends FragmentPagerAdapter {


    private Context context;
    private List<Fragment> fragmentList;
    private String[] titles;

    public FragmentTabLayoutAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        if (fragmentList != null) {
            return fragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (fragmentList.size() == titles.length) {
            return fragmentList.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //标题

        if (fragmentList.size() == titles.length) {
            return titles[position];
        }
        return null;
    }
}
