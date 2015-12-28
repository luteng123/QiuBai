package com.luteng.qiubai.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.luteng.qiubai.R;

/**
 * Created by John on 2015/12/28.
 */
public class MainExclusiveFragment extends Fragment{
    public MainExclusiveFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_exclusive,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.fragment_exclusive_text_view_1);
        String text = getArguments().getString("text");
        if (text != null) {
            textView.setText(text);
        }

    }
    public static MainExclusiveFragment newInstance(String text){
        Bundle args = new Bundle();
        MainExclusiveFragment fragmentHome = new MainExclusiveFragment();
        args.putString("text",text);
        fragmentHome.setArguments(args);
        return fragmentHome;
    }
}
