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
 * Use the {@link MainFullTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFullTextFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    // TODO: Rename and change types and number of parameters

    public MainFullTextFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_full_text, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.fragment_full_text_text_view_1);
        String text = getArguments().getString("text");
        if (text != null) {
            textView.setText(text);
        }

    }
    public static MainFullTextFragment newInstance(String text){
        Bundle args = new Bundle();
        MainFullTextFragment fragmentHome = new MainFullTextFragment();
        args.putString("text",text);
        fragmentHome.setArguments(args);
        return fragmentHome;
    }


}
