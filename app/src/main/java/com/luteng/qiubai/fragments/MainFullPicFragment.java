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
public class MainFullPicFragment extends Fragment {


    public MainFullPicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_full_pic, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.fragment_full_text_pic_view_1);
        String text = getArguments().getString("text");
        if (text != null) {
            textView.setText(text);
        }

    }
    public static MainFullPicFragment newInstance(String text){
        Bundle args = new Bundle();
        MainFullPicFragment fragmentHome = new MainFullPicFragment();
        args.putString("text",text);
        fragmentHome.setArguments(args);
        return fragmentHome;
    }


}
