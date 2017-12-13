package com.sty.first.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sty.first.service.service.FirstService;
import com.sty.first.service.service.IService;
import com.sty.first.service.service.TestService;

/**
 *  以startService方式开启服务的特点：
 *      1.第一次点击按钮开启服务时会执行服务的onCreate和onStartCommand方法
 *      2.第二次及之后点击按钮开启服务时执行服务的onStartCommand方法
 *      3.服务开启后可以在设置页面的running中找到这个服务 ※
 *      4.此种方式开启的服务会在后台长期运行，直到用户手工停止或调用stopService方法后该服务才会被销毁 ※
 *  以bindService方式开启服务的特点：
 *      1.第一次点击按钮开启服务时会执行服务的onCreate和onBind方法
 *      2.第二次及之后点击按钮开启服务时服务没有响应
 *      3.当Activity销毁的时候服务也会销毁---不求同时生但求同时死
 *      4.通过该方式启动的服务不能在设置页面找到，相当于是一个隐形的服务 ※
 *      5.bindService不能多次解绑，多次解绑会报错
 *
 *  引入bindService的目的：
 *      为了调用服务中的方法
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnStartService;
    private Button btnStopService;
    private Button btnBindService;
    private Button btnUnbindService;
    private Button btnCallMethodInService;

    private Intent intent;
    private MyConn conn;

    //private TestService.MyBinder myBinder;
    private IService myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();

        //绑定测试在Service中调用方法的服务
        bindTestService();
    }

    @Override
    protected void onDestroy() {
        //当Activity销毁的时候取消服务的绑定
        unbindService(conn);
        super.onDestroy();
    }

    private void initViews(){
        btnStartService = findViewById(R.id.btn_start_service);
        btnStopService = findViewById(R.id.btn_stop_service);
        btnBindService = findViewById(R.id.btn_bind_service);
        btnUnbindService = findViewById(R.id.btn_unbind_service);
        btnCallMethodInService = findViewById(R.id.btn_call_method_in_service);

        intent = new Intent(this, FirstService.class);
        conn = new MyConn();
    }

    private void setListeners(){
        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        btnBindService.setOnClickListener(this);
        btnUnbindService.setOnClickListener(this);
        btnCallMethodInService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_service:
                startService(intent);
                break;
            case R.id.btn_stop_service:
                stopService(intent);
                break;
            case R.id.btn_bind_service:
                Log.i("Tag", "service bind ---- " + conn);
                bindService(intent, conn, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                Log.i("Tag", "service unbind ---- " + conn);
                unbindService(conn);
                break;
            case R.id.btn_call_method_in_service:
                //通过我们定义的中间人对象间接调用服务中的方法
                myBinder.callBanZheng(1002);
                myBinder.callPlayMaJiang();
                break;
            default:
                break;
        }
    }

    private class MyConn implements ServiceConnection{
        //连接成功后调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("TAg", "service connect success!");
            if(service != null){ //获取中间人对象
                //myBinder = (TestService.MyBinder) service;
                myBinder = (IService) service;
            }
        }

        //失去连接时调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("TAg", "service lost connect!");
        }
    }

    private void bindTestService(){
        Intent intent = new Intent(this, TestService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }
}
