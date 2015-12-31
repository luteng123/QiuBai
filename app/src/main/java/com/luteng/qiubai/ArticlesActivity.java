package com.luteng.qiubai;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.luteng.qiubai.Utils.CircleTransform;
import com.luteng.qiubai.Utils.Enjoiner;
import com.luteng.qiubai.adapters.ArticlePagerAdapter;
import com.luteng.qiubai.entity.TotalEntity;
import com.luteng.qiubai.fragments.article.ArticleAllCommentFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticlesActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnTouchListener {
    private PullToRefreshScrollView refreshScrollView;
    private ImageView icon;
    private TextView name;
    private ImageView type;
    private TextView typeName;
    private TextView content;
    private ImageView image;

    private LinearLayout commentLinear;
    private TextView small;
    private TextView comment;
    private TextView share;
    private RadioGroup group;
    private ImageButton commentButton;

    private TabLayout tab;
    private ViewPager pager;
    private List<Fragment> list;
    private ArticlePagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        init();//查找view  id
        Bundle bundle = getIntent().getExtras();
        TotalEntity.ItemsEntity item = (TotalEntity.ItemsEntity) bundle.get("item");
        content.setText(item.getContent());
        //评论的文章id
        int c_id = item.getId();
        if(item.getUser()==null){
            icon.setImageResource(R.mipmap.ic_launcher);
            name.setText("匿名用户");
        }else{
            name.setText(item.getUser().getLogin());
            Picasso.with(this)
                    .load(Enjoiner.getIconURL(item.getUser().getId(), item.getUser().getIcon()))
                    .transform(new CircleTransform())
                    .into(icon);
            String s =  Enjoiner.getIconURL(item.getUser().getId(), item.getUser().getIcon());
            Log.d("FragmentExclusiveAdapter", "iconUrl = " + s);

        }
        if(item.getImage()==null){
            image.setVisibility(View.GONE);
        }else{
            Picasso.with(this).load(Enjoiner.getImageURL((String)item.getImage()))
                    .placeholder(R.mipmap.ic_launcher)//下载中图片
                    .error(R.mipmap.ic_launcher)//下载失败的图片
                    .resize(image.getWidth(),0)//防止是0的情况
                    .into(image);
        }
        if(item.getType()==null){
            type.setVisibility(View.GONE);
            typeName.setVisibility(View.GONE);
        }else{
            type.setImageResource(R.mipmap.ic_rss_hot);
            typeName.setText("热门");
        }
        //评论条的Linear
        //TODO:设置监听
        small.setText(small.getText().toString() + " " + (item.getVotes().getUp() + item.getVotes().getDown()));
        comment.setText(comment.getText().toString() + " " + item.getComments_count());
        share.setText(share.getText().toString() + " " + item.getShare_count());

        list = new ArrayList<>();
        Fragment f =ArticleAllCommentFragment.newInstance("全部");
        Bundle arguments = f.getArguments();
        arguments.putInt("c_id", c_id);
        f.setArguments(arguments);
        list.add(f);
        f = ArticleAllCommentFragment.newInstance("热门");
        arguments = f.getArguments();
        arguments.putInt("c_id", c_id);
        f.setArguments(arguments);
        list.add(f);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ArticlePagerAdapter(fragmentManager,list);
        pager.setAdapter(adapter);
        //接着就是和tab联动
        tab.setupWithViewPager(pager);

        //group设置监听
        group.setOnCheckedChangeListener(this);

//        refreshScrollView.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);//手动刷新
        refreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//上拉加载
        refreshScrollView.setOnTouchListener(this);

    }

    private void init() {
        icon = (ImageView) findViewById(R.id.fragment_exclusive_user_icon);
        name = (TextView) findViewById(R.id.fragment_exclusive_user_name);
        type = (ImageView)findViewById(R.id.fragment_exclusive_type_image);
        typeName = (TextView) findViewById(R.id.fragment_exclusive_type_name);
        content = (TextView)findViewById(R.id.fragment_exclusive_content);
        image = (ImageView) findViewById(R.id.fragment_exclusive_image);
        commentLinear = (LinearLayout)findViewById(R.id.fragment_exclusive_linear_comment);
        small = (TextView)findViewById(R.id.fragment_exclusive_comment_small);
        comment = (TextView)findViewById(R.id.fragment_exclusive_comment_comment);
        share = (TextView)findViewById(R.id.fragment_exclusive_comment_share);
        group = (RadioGroup)findViewById(R.id.fragment_exclusive_comment_radio_group);
        commentButton = (ImageButton)findViewById(R.id.fragment_exclusive_comment_comment_button);

        tab = (TabLayout) findViewById(R.id.article_activity_tab);
        pager = (ViewPager) findViewById(R.id.article_activity_pager);

        refreshScrollView = (PullToRefreshScrollView) findViewById(R.id.article_activity_refresh_scroll_view);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //TODO:增加减少，需要数据库的支持
            case R.id.fragment_exclusive_comment_group_button_support:
                break;
            case R.id.fragment_exclusive_comment_group_button_unsupport:
                break;
        }
        View buttonView = group.findViewById(checkedId);
        if (buttonView != null) {
            ViewCompat.setScaleX(buttonView, 0.8f);
            ViewCompat.setScaleY(buttonView, 0.8f);
            ViewCompat.animate(buttonView).scaleX(1).scaleY(1).setDuration(300).start();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
