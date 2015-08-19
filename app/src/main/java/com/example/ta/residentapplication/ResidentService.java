package com.example.ta.residentapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by ta on 2015/08/19.
 */
public class ResidentService extends Service {

    /*定義宣言*/
    public static final String TAG = "ResidentService";

    /*変数宣言*/
    private View view;
    WindowManager windowManager;

    public IBinder onBind(Intent intent){
        return null;
    }

    public void onCreate(){
        super.onCreate();
    }

    public int onStartCommand(Intent intent,int flags,int startId){
        CreateView();
        Log.d(TAG,"onStartCommand");
        return START_STICKY;
    }

    private void CreateView(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );

        windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

        view = layoutInflater.inflate(R.layout.overlay,null);

        windowManager.addView(view, params);
    }

    public void onDestroy(){
        Log.d(TAG,"onDestroy");
        windowManager.removeView(view);
        Log.d(TAG,"finished");
    }

}
