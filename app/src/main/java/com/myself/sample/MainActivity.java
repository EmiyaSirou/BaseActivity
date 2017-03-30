package com.myself.sample;

import android.os.Bundle;

import com.myself.baseactivity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {
        setCustomTitle("测试");
        setCustomView(R.layout.activity_main);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
