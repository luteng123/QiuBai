package com.luteng.qiubai;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.nio.InvalidMarkException;

public class FirstActivity extends AppCompatActivity implements Runnable{
    private ImageView imageView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageView.setVisibility(View.VISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ImageView image = (ImageView) findViewById(R.id.first_image_bottom);
        imageView = (ImageView) findViewById(R.id.first_image_view);
        image.setAdjustViewBounds(true);
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            Message message = handler.obtainMessage();
            handler.sendMessage(message);
            Thread.sleep(1000);
            startActivity(new Intent(this,MainActivity.class));
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
