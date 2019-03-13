package com.example.r_web.myapplication;

import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {//Android版本太高了  有问题  以前没遇到过
    TextView tVxia;
    TextView tVzhong;
    Button[] btsz = new Button[16];//创建15个按钮的数组
    Button bTclear;
    Button bTgn;
    boolean flagjisuan = false;
    boolean flagsy = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TypedArray btidar = getResources().obtainTypedArray(R.array.actions_button);//通过调用string.xml文件中的数组批量管理按钮id
        TypedArray yinparr = getResources().obtainTypedArray(R.array.actions_shengyin);//同理
        int len = btidar.length();//一共16个按钮，十六个声音，声音和按钮共用参数  清除按钮单独绑定
        int[] btid = new int[len];
        final int[] yinid = new int[len];
        for (int i = 0; i < len; i++){
            btid[i] = btidar.getResourceId(i,0);//获得按钮的id放到btid数组里面，这里可以写成两行
            yinid[i] = yinparr.getResourceId(i,0);//获得音乐id放到yinid里面
            btsz[i] = (Button)findViewById(btid[i]);//绑定id到按钮
        }
        tVxia = (TextView)findViewById(R.id.tVdown);
        tVzhong = (TextView)findViewById(R.id.tVzhong);
        bTclear = (Button)findViewById(R.id.clean);
        bTgn = (Button)findViewById(R.id.bTgongneng);

        for(int i = 0;i < 16;i++){//这里原创  哈哈哈哈
            if(i == 14) //等号单独处理
                continue;
            btsz[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str = tVxia.getText().toString();//获取最下面用于储存输入的文本框的文本
                    flagjisuan = true;//置标志为运算状态，防止在空情况下按等号
                    Button btt = (Button)findViewById(view.getId());//获取当前按钮
                    String str1 = btt.getText().toString();//获取按钮上的文本
                    if(flagsy){//如果播放开关开启 播放对应音乐 通过shengyinzhi()函数查找对应音乐在yinid中的位置
                        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this,yinid[shengyinzhi(str1)]);
                        mediaPlayer.start();
                    }
                    str = str+str1;//这里不能使用变量btsz[i]，i设成全局变量也不行,这是在类里面
                    tVxia.setText(str);
                }
            });
        }
        btsz[14].setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View view) {//调用运算函数输出结果到底栏，获取算式到上一栏
                if(flagsy){
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.dengyu);
                    mediaPlayer.start();
                }
                if(flagjisuan){
                    String str = tVxia.getText().toString();
                    String str1 = str + "=";
                    tVzhong.setText(str1);
                    tVxia.setText(eval(str));//输出结果
                }
                else
                    tVzhong.setText("0");
            }
        });
        bTclear.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View view) {
                if(flagsy){
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.clear);
                    mediaPlayer.start();
                }
                flagjisuan = false;
                tVxia.setText("");//清空底部输出
            }
        });
        bTgn.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View view) {
                if(flagsy){
                    flagsy = false;
                    bTgn.setText("声音:关");
                } else{
                    flagsy = true;
                    bTgn.setText("声音:开");
                }
            }
        });
    }
    private int shengyinzhi(String str){
        switch (str){
            case "7":
                return 0;
            case "8":
                return 1;
            case "9":
                return 2;
            case "/":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "-":
                return 7;
            case "1":
                return 8;
            case "2":
                return 9;
            case "3":
                return 10;
            case "+":
                return 11;
            case "0":
                return 12;
            case ".":
                return 13;
            case "=":
                return 14;
            case "*":
                return 15;
        }
        return 0;
    }
    public String eval(String st)//计算代码  借鉴借鉴
    {
        String b=null;
        int packet=0;
        int i=0;
        String sreslut;
        StringBuffer sb = null;
        double tmpty=0;
        execute exe = new execute();
        if(!verify(st))
        {
            return "错误";
        }
        if(st.indexOf('(')!=-1)
        {
            for(i=0;i<st.indexOf('(');i++)
                if(st.charAt(i)=='+')packet = i;
            try {
                tmpty = new execute().eval(st.substring(st.indexOf('(')+1, st.indexOf(')')));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "错误";
            }
            System.out.printf("tmpty = %f\n",tmpty);
            sreslut = st.substring(0,st.indexOf('('))+tmpty
                    + st.substring(st.indexOf(')')+1,st.length());
            sb = new StringBuffer(sreslut);

            if(tmpty < 0)
                if(packet == st.indexOf('(')-1)
                    sb.deleteCharAt(packet);
                else
                {
                    sb.setCharAt(packet, '-');
                    sb.deleteCharAt(st.indexOf('('));
                }
            sreslut = sb.toString();
        }
        else sreslut = st;
        try {
            b = exe.eval(sreslut)+"";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "错误";
        }
        return b;
    }
    private boolean verify(String str) {
        int i=0;
        if(str.charAt(0)<48&&str.charAt(0)!=45)
            return false;
        for(i = 0; i < str.length(); i++)
        {
            if(str.charAt(i)>=40 && str.charAt(i)<=58)
                return true;
            if(str.charAt(i)<48 && str.charAt(i+1)<48)
                return false;
            else return true;
        }
        return false;
    }
}