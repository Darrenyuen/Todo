package com.example.yuan.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTodoActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private AlarmReceiver alarmReceiver;
    private Calendar calendar;
    private AlarmService alarmService;
    private AlarmManager alarmManager;
    private IntentFilter intentFilter;

    @OnClick({R.id.addTodo, R.id.cancel})
    public void onclick(View view) {

        switch (view.getId()) {
            case R.id.addTodo: setAlarm(view); break;
            case R.id.cancel: sendATestService(); break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        ButterKnife.bind(this);
        intentFilter = new IntentFilter();
        alarmReceiver = new AlarmReceiver();
        intentFilter.addAction("TEST");
        registerReceiver(alarmReceiver, intentFilter);
        calendar = Calendar.getInstance();
        Log.d(TAG, "onCreate: " + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) +  "-" + calendar.get(Calendar.DAY_OF_MONTH));
        Log.d(TAG, "onCreate: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        Log.d(TAG, "onCreate: " + calendar.getTimeInMillis());
    }

    public void setAlarm(View v) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AddTodoActivity.this, AlarmReceiver.class);
//        intent.setAction("TEST");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTodoActivity.this, 0, intent, 0);
//        calendar.set(2019,7, 18, 21, 00);
        Log.d(TAG, "setOneAlarm: " + calendar.getTimeInMillis() + " " + (System.currentTimeMillis() + 5000));
        if(Build.VERSION.SDK_INT < 19){
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
        }
    }

    public void sendATestService() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmService.class);
//        intent.setAction(AlarmService.ACTION_ALARM);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(Build.VERSION.SDK_INT < 19){
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, pendingIntent);
        }else{
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, pendingIntent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmReceiver);
    }
}
