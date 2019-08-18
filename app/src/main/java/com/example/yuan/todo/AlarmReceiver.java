package com.example.yuan.todo;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

public class AlarmReceiver extends BroadcastReceiver {

    private final String TAG = this.getClass().getSimpleName();

    private AlertDialog.Builder builder;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive:  闹钟响了");
        Toast.makeText(context, "Receiver 闹钟响了", Toast.LENGTH_SHORT).show();

    }

    private void showConfirmDialog(Context context) {
        builder = new AlertDialog.Builder(context);

    }
}
