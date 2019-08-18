package com.example.yuan.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmService extends Service {

    private final static String TAG = "AlarmService";

    MyBinder mBinder = new MyBinder();

    public AlarmService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: " + "test");
        ToastUtil.showLong(this, "Ser 闹钟");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    class MyBinder extends Binder {
        private String[] mTimeSplit;
        private int mHour;
        private int mMinute;

        public void setAlam(Context context, String time, int id) {
            mTimeSplit = time.split(":");
            mHour = Integer.parseInt(mTimeSplit[0]);
            mMinute = Integer.parseInt(mTimeSplit[1]);
        }
    }

}
