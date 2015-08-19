package com.example.ta.residentapplication;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{

    /*定義宣言*/
    public static final String TAG = "MainActivity";

    /*変数宣言*/
    private boolean serviceFlag;
    private boolean resumeFlag;
    private Button button1;
    private Switch switch1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        serviceFlag = false;
        resumeFlag = false;
        SetView();
        CheckServiceMoving();
    }

    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
        resumeFlag = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.button1:
                finish();
                break;
        }
    }


    public void onCheckedChanged(CompoundButton compoundButton,boolean isChecked) {
        if (resumeFlag) {
            Intent intent = new Intent(this, ResidentService.class);
            if (isChecked) {
                startService(intent);
            } else {
                stopService(intent);
            }
        }
    }

    //起動中のserviceを取得してlistに代入
    //そのlistの中にAccelerationServiceが入っているようだったら
    //スイッチをオン、そうでなければオフ
    private void CheckServiceMoving() {
        int i = 0;
        ActivityManager activityManager =
                (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        //listに起動中のserviceを代入
        List<ActivityManager.RunningServiceInfo> list =
                activityManager.getRunningServices(Integer.MAX_VALUE);
        Log.d(TAG, ResidentService.class.getName());

        //listのチェック
        while (i <= list.size() - 1) {
            Log.d(TAG, list.get(i).service.getClassName());

            //listの中のserviceの名前(String)がAccelerationServiceであるなら
            //serviceが起動中であるというフラグを立てる
            if (ResidentService.class.getName().equalsIgnoreCase
                    (list.get(i).service.getClassName())) {
                Log.d(TAG, "Service  Is Moving");
                serviceFlag = true;
                break;
            }
            i++;
        }
        switch1.setChecked(serviceFlag);

    }

    private void SetView(){
        setContentView(R.layout.activity_main);
        button1 =(Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
        switch1 = (Switch)findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(this);
    }
}
