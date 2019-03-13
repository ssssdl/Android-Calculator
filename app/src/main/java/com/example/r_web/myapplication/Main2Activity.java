package com.example.r_web.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {//欢迎界面
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //接收到消息后跳转
            goMain();
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //延迟两秒发送消息
        handler.sendEmptyMessageDelayed(0,2000);
    }
    private void goMain() {
        //设定调动MainActivity
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);  //将控制权交给MainActivity
        finish();   //结束
    }
}
