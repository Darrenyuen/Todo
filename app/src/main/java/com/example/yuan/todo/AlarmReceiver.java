package com.example.yuan.todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.yuan.todo.activity.AlertActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getStringExtra("todo") + intent.getIntExtra("remindTypeCode", 0));
        Log.d(TAG, "onReceive:  提醒");
        Intent toAlertAct = new Intent(context, AlertActivity.class);
        toAlertAct.putExtra("todo", intent.getStringExtra("todo"));
        toAlertAct.putExtra("remindTypeCode", intent.getIntExtra("remindTypeCode", 0));
        Log.d(TAG, "onReceive: " + intent.getStringExtra("todo") + intent.getIntExtra("remindTypeCode", 0));
        toAlertAct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(toAlertAct);
    }
}
