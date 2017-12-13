package com.sty.first.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 定义服务，需要在清单文件中配置
 * Created by Steven.T on 2017/12/13/0013.
 */

public class FirstService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Tag", "onBind");
        return null;
    }

    //当服务第一次被开启的时候调用
    @Override
    public void onCreate() {
        Log.i("Tag", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Tag", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i("Tag", "onDestroy");
        super.onDestroy();
    }
}
