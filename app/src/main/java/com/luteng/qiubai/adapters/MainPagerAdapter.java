package com.luteng.qiubai.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.luteng.qiubai.fragments.MainExclusiveFragment;

import java.util.List;

/**
 * Created by John on 2015/12/28.
 */
public class MainPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> stringList;

    public MainPagerAdapter(FragmentManager fm, List<Fragment> stringList) {
        super(fm);
        this.stringList = stringList;
    }

    @Override
    public Fragment getItem(int position) {

        return stringList.get(position);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (stringList != null) {
            ret = stringList.size();
        }
        return ret;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (String)stringList.get(position).getArguments().get("text");
    }
}
