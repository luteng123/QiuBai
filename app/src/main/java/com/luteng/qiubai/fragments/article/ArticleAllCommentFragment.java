package com.luteng.qiubai.fragments.article;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.luteng.qiubai.R;
import com.luteng.qiubai.Utils.QsbkService;
import com.luteng.qiubai.adapters.ArticleFragmentAdapter;
import com.luteng.qiubai.entity.CommentEntity;
import retrofit.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleAllCommentFragment extends Fragment implements Callback<CommentEntity> {
    private ListView listView;
    private Call<CommentEntity> call;
    private ArticleFragmentAdapter adapter;
    public ArticleAllCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_all_comment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.article_all_comment_list);
        //可以不让ListView获取焦点
        //listView.setFocusable(false);
        adapter = new ArticleFragmentAdapter(getContext());
        listView.setAdapter(adapter);




        int c_id = getArguments().getInt("c_id");
//        Log.d("ArticleAllCommentFragment", "c_id = 1111  " + c_id);

        //下载图片的网络任务
        Retrofit build = new Retrofit.Builder().baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(GsonConverterFactory.create())//json字符串转换的list<ItemEntity>
                .build();
        QsbkService service = build.create(QsbkService.class);
        call = service.getCommentData(String.valueOf(c_id), 1);
        call.enqueue(this);
    }

    public static ArticleAllCommentFragment newInstance(String text){
        Bundle args = new Bundle();
        ArticleAllCommentFragment fragmentHome = new ArticleAllCommentFragment();
        args.putString("text", text);
        fragmentHome.setArguments(args);
        return fragmentHome;
    }


    @Override
    public void onResponse(Response<CommentEntity> response, Retrofit retrofit) {
        Log.d("ArticleAllCommentFragment", "data 222   " + response.body().getItems().get(8).getContent());

        adapter.addAll(response.body().getItems());
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(getContext(), "网络问题", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStop() {
        //意外退出取消
        super.onStop();
        call.cancel();
    }
}
