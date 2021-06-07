package com.example.lipsticktest;

import android.app.Application;

import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;

public class MyApplicatioin extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,
                "pHtsmiMtUQiSteY9CsKnpC5E-gzGzoHsz",
                "3gIayjWUEVVSqTWDc8TA9A3f",
                "https://phtsmimt.lc-cn-n1-shared.com");


    }
}
