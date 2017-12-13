package com.sty.first.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Steven.T on 2017/12/13/0013.
 */

public class TestService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //把我们定义的中间人对象返回
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //办证方法
    public void banZheng(int money){
        if(money > 1000) {
            Toast.makeText(getApplicationContext(), "我是领导，把证给你办了！", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "这点钱你还想办证？", Toast.LENGTH_SHORT).show();
        }
    }

    //打麻将的方法
    public void playMaJiang(){
        Toast.makeText(getApplicationContext(), "我是打麻将的方法", Toast.LENGTH_SHORT).show();
    }

    //洗桑拿的方法
    public void takeBath(){
        Toast.makeText(getApplicationContext(), "我是洗桑拿的方法", Toast.LENGTH_SHORT).show();
    }

/*    //1.定义一个中间人对象
    public class MyBinder extends Binder{
        //2.定义一个方法，调用办证的方法
        public void callBanZheng(int money){
            banZheng(money);
        }
    }*/

    //1.定义一个中间人对象
    private class MyBinder extends Binder implements IService{
        //2.定义一个方法，调用办证的方法
        public void callBanZheng(int money){
            banZheng(money);
        }

        //2.定义一个打麻将的方法
        public void callPlayMaJiang(){
            playMaJiang();
        }

        //2.定义一个洗桑拿的方法
        public void callTakeBath(){
            takeBath();
        }
    }
}
