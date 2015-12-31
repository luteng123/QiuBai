package com.luteng.qiubai;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.luteng.qiubai.adapters.MainPagerAdapter;
import com.luteng.qiubai.fragments.*;
import com.luteng.qiubai.fragments.faxian.FaxianFragment;
import com.luteng.qiubai.fragments.mime.MineFragment;
import com.luteng.qiubai.fragments.qiuyouquan.QiuyouQuanFragment;
import com.luteng.qiubai.fragments.xiaozhitiao.SmallNoteFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView menu;

    private ActionBar actionBar;
    private ActionBarDrawerToggle toggle;

    private  FragmentManager fragmentManager;
    private Fragment f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.main_drawer);
        menu = (NavigationView) findViewById(R.id.main_menu);

        //显示home
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setActionBarIcon(actionBar);

        //来个开关，可以动态变化
        toggle = new ActionBarDrawerToggle(this, drawer, 0, 0);
        toggle.syncState();

        //给draw设置监听，控制toggle
        drawer.setDrawerListener(toggle);
        //给导航菜单设置监听
        menu.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        f = new MainFragment();
        transaction.add(R.id.main_container, f);
        transaction.commit();

    }

    private void setActionBarIcon(ActionBar actionBar) {
        //显示Logo和标题
        //TODO: 怎么点击退出
        actionBar.setLogo(R.mipmap.notification_icon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(R.string.title);
    }
    //选择ActionBar，菜单就会出来

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //选择导航菜单的监听事件
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (itemId) {
            case R.id.item_QiuShi:
                f = new MainFragment();
                transaction.replace(R.id.main_container,f);
                transaction.commit();
                break;
            case R.id.item_QiuYouQuan:
                f = new QiuyouQuanFragment();
                transaction.replace(R.id.main_container,f);
                transaction.commit();
                break;
            case R.id.item_select:
                f = new FaxianFragment();
                transaction.replace(R.id.main_container,f);
                transaction.commit();
                break;
            case R.id.item_small_note:
                f = new SmallNoteFragment();
                transaction.replace(R.id.main_container,f);
                transaction.commit();
                break;
            case R.id.item_mine:
                f = new MineFragment();
                transaction.replace(R.id.main_container,f);
                transaction.commit();
                break;
            case R.id.test_item_setting:
                break;
            case R.id.test_item_exit:
                ActivityCompat.finishAffinity(this);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    /*按钮的点击必须在Activity中，可以利用接口回调 setTag，通过数据改变驱动界面变化*/
//    public void btnComment(View view) {
//        startActivity(new Intent(this,ArticlesActivity.class));
//    }
}
