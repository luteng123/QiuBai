package com.luteng.qiubai.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.luteng.qiubai.R;
import com.luteng.qiubai.Utils.QsbkService;
import com.luteng.qiubai.adapters.FragmentExclusiveAdapter;
import com.luteng.qiubai.entity.TotalEntity;
import retrofit.*;

/**
 * Created by John on 2015/12/28.
 */
public class MainExclusiveFragment extends Fragment implements Callback<TotalEntity> ,PullToRefreshBase.OnRefreshListener2<ListView>{
    private FragmentExclusiveAdapter adapter;
    private Context context;
    private Call<TotalEntity> call;
    private int currentPage = 1;
    private Retrofit build;
    private QsbkService service;
    private PullToRefreshListView listView;
    private int countPage;

    public MainExclusiveFragment(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_exclusive,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        listView = (PullToRefreshListView) view.findViewById(R.id.fragment_exclusive_list_view);
        adapter = new FragmentExclusiveAdapter(context);
        listView.setAdapter(adapter);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);

        //下载图片的网络任务
        build = new Retrofit.Builder().baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(GsonConverterFactory.create())//json字符串转换的list<ItemEntity>
                .build();
        service = build.create(QsbkService.class);
        call = service.getTotalData("suggest", currentPage);
        call.enqueue(this);

    }
    public static MainExclusiveFragment newInstance(String text){
        Bundle args = new Bundle();
        MainExclusiveFragment fragmentHome = new MainExclusiveFragment();
        args.putString("text",text);
        fragmentHome.setArguments(args);
        return fragmentHome;
    }



    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(context,"网络问题",Toast.LENGTH_SHORT).show();
        listView.onRefreshComplete();

    }

    @Override
    public void onStop() {
        //意外退出取消
        super.onStop();
        call.cancel();
    }
    @Override
    public void onResponse(Response<TotalEntity> response, Retrofit retrofit) {
        if(currentPage == 1){
            adapter.clear();
        }
        adapter.addAll(response.body().getItems());
        countPage = response.body().getCount();
        listView.onRefreshComplete();
    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        call = service.getTotalData("suggest", 1);
        call.enqueue(this);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if(currentPage <= countPage){
            currentPage++;
            call = service.getTotalData("suggest", currentPage);
            call.enqueue(this);
        }else{
            Toast.makeText(context,"别拉了",Toast.LENGTH_SHORT).show();
        }

    }
}
