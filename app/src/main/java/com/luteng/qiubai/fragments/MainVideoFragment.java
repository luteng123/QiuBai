package com.luteng.qiubai.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.luteng.qiubai.R;
import com.luteng.qiubai.Utils.QsbkService;
import com.luteng.qiubai.adapters.FragmentExclusiveAdapter;
import com.luteng.qiubai.adapters.VideoAdapter;
import com.luteng.qiubai.entity.TotalEntity;
import com.luteng.qiubai.entity.VideoEntity;
import retrofit.*;

public class MainVideoFragment extends Fragment implements Callback<VideoEntity> {
    private VideoAdapter adapter;
    private Context context;
    private Call<VideoEntity> call;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        ListView listView = (ListView) view.findViewById(R.id.fragment_video_list_view);
        adapter = new VideoAdapter(context);
        listView.setAdapter(adapter);

//        //下载图片的网络任务
//        Retrofit build = new Retrofit.Builder()
//                .build();
//        QsbkService service = build.create(QsbkService.class);
//        call = service.getVideoData("video", 1);
//        call.enqueue(this);

    }
    public static MainVideoFragment newInstance(String text){
        Bundle args = new Bundle();
        MainVideoFragment fragmentHome = new MainVideoFragment();
        args.putString("text",text);
        fragmentHome.setArguments(args);
        return fragmentHome;
    }
    public MainVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_video, container, false);
    }
    public void onResponse(Response<VideoEntity> response, Retrofit retrofit) {
        //TODO:在写个adapter
        adapter.addAll(response.body().getItems());
    }

    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(context, "网络问题", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        //意外退出取消
        super.onStop();
        call.cancel();
    }


}
