package com.example.ta.residentapplication;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by ta on 2015/08/19.
 */
public class ResidentService extends Service implements View.OnClickListener{

    /*定義宣言*/
    public static final String TAG = "ResidentService";

    /*変数宣言*/
    private View view;
    private WindowManager windowManager;
    private Button button1,button2;

    public IBinder onBind(Intent intent){
        return null;
    }

    public void onCreate(){
        super.onCreate();
    }

    public int onStartCommand(Intent intent,int flags,int startId){
        CreateView();
        Log.d(TAG, "onStartCommand");
        return START_STICKY;
    }

    private void CreateView(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

        view = layoutInflater.inflate(R.layout.overlay,null);

        windowManager.addView(view, params);

        button1 = (Button)view.findViewById(R.id.button2);
        button1.setOnClickListener(this);
    }

    public void onClick(View view){
        Log.d(TAG,"onClick");
        switch (view.getId()) {
            case R.id.button2:
                Toast.makeText(this, "Pushed The Button", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onDestroy(){
        Log.d(TAG,"onDestroy");
        windowManager.removeView(view);
        Log.d(TAG,"finished");
    }

}
