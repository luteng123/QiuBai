package com.luteng.qiubai.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luteng.qiubai.R;
import com.luteng.qiubai.adapters.MainPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {    private ViewPager pager;
    private List<Fragment> list;
    private MainPagerAdapter adapter;
    private TabLayout tab;
    private Context context;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        View ret = inflater.inflate(R.layout.fragment_main, container, false);
        //初始化pager和tab
        tab = (TabLayout)ret. findViewById(R.id.main_fragment_tab);
        pager = (ViewPager) ret.findViewById(R.id.main_fragment_pager);
        list = new ArrayList<>();
        Fragment f = MainExclusiveFragment.newInstance("专享");
        list.add(f);
        f = MainVideoFragment.newInstance("视频");
        list.add(f);
        f = MainFullTextFragment.newInstance("纯文");
        list.add(f);
        f = MainFullPicFragment.newInstance("纯图");
        list.add(f);
        f = MainSerumFragment.newInstance("精华");
        list.add(f);
        FragmentManager fragmentManager = getChildFragmentManager();
        adapter = new MainPagerAdapter(fragmentManager,list);
        pager.setAdapter(adapter);
        //接着就是和tab联动
        tab.setupWithViewPager(pager);
        return ret;
    }


}
