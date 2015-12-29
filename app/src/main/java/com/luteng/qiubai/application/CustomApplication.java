package com.luteng.qiubai.application;

import android.app.Application;
import android.graphics.Bitmap;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by John on 2015/12/29.
 */
public class CustomApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //设置自己的picasso  缓存10M，最多缓存30M
        Picasso build = new Picasso.Builder(this).memoryCache(new LruCache(10 << 20))
                .downloader(new OkHttpDownloader(getCacheDir(), 30 << 20))
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .build();
        Picasso.setSingletonInstance(build);
    }
}
