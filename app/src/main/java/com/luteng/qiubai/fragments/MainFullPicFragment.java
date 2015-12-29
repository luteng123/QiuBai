package com.luteng.qiubai.fragments;


import android.content.Context;
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
import com.luteng.qiubai.entity.TotalEntity;
import retrofit.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFullPicFragment extends Fragment implements Callback<TotalEntity> {

    private FragmentExclusiveAdapter adapter;
    private Context context;
    private Call<TotalEntity> call;
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
        context = view.getContext();
        ListView listView = (ListView) view.findViewById(R.id.fragment_full_piv_list_view);
        adapter = new FragmentExclusiveAdapter(context);
        listView.setAdapter(adapter);

        //下载图片的网络任务
        Retrofit build = new Retrofit.Builder().baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(GsonConverterFactory.create())//json字符串转换的list<ItemEntity>
                .build();
        QsbkService service = build.create(QsbkService.class);
        call = service.getTotalData("image", 1);
        call.enqueue(this);

    }
    public static MainFullPicFragment newInstance(String text){
        Bundle args = new Bundle();
        MainFullPicFragment fragmentHome = new MainFullPicFragment();
        args.putString("text",text);
        fragmentHome.setArguments(args);
        return fragmentHome;
    }
    public void onResponse(Response<TotalEntity> response, Retrofit retrofit) {
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
