package com.example.myapp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    private Button mBtn1,mBtn2,mBtn3,mBtn4;
    private TextView mTvw1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //状态栏背景透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        mTvw1=findViewById(R.id.theme_1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        mTvw1.setText(simpleDateFormat.format(date));
        //点击切换页面
        mBtn1=findViewById(R.id.main2_1);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,BillPagerActivity.class);
                startActivity(intent);

            }
        });
        mBtn1=findViewById(R.id.main2_2);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,SQLiteWriteActivity.class);
                startActivity(intent);

            }
        });
        mBtn1=findViewById(R.id.main2_3);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,MoodActivity.class);
                startActivity(intent);

            }
        });
        mBtn1=findViewById(R.id.main2_4);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,interfaceActivity.class);
                startActivity(intent);

            }
        });
    }
}