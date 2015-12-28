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
 * A simple {@link Fragment} subclass.
 */
public class MainSerumFragment extends Fragment {


    public MainSerumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_serum, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.fragment_serum_text_view_1);
        String text = getArguments().getString("text");
        if (text != null) {
            textView.setText(text);
        }

    }
    public static MainSerumFragment newInstance(String text){
        Bundle args = new Bundle();
        MainSerumFragment fragmentHome = new MainSerumFragment();
        args.putString("text",text);
        fragmentHome.setArguments(args);
        return fragmentHome;
    }


}
