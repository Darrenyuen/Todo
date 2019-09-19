package com.example.yuan.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

public class AlarmService extends Service {

    private final String TAG = this.getClass().getSimpleName();

    private Context context;

    private String[] date;
    private String[] time;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private AlarmManager alarmManager;
    private Calendar calendar;
    private Intent intent;
    private PendingIntent pendingIntent;
    private int alarmCount = 0;

    private String todo;
    private int code;

    private boolean isSetAlarm;

    public AlarmService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        date = intent.getStringExtra("date").split("-");
        year = Integer.parseInt(date[0]);
        month = Integer.parseInt(date[1]);
        day = Integer.parseInt(date[2]);
        time = intent.getStringExtra("time").split(":");
        hour = Integer.parseInt(time[0]);
        minute = Integer.parseInt(time[1]);

        todo = intent.getStringExtra("todo");
        code = intent.getIntExtra("remindTypeCode", 0);
        isSetAlarm = intent.getBooleanExtra("isSetAlarm", true);
        Log.d(TAG, "onStartCommand: " + todo + code);

        if (isSetAlarm) {
            setAlarm(todo, code);
        } else {
            cancelAlarm(todo, intent.getStringExtra("date"), intent.getStringExtra("time"), code);
        }
        //或者在AlarmService类中实现setAlarm和cancelAlarm(即非Binder的子类中实现)
        //但此时，服务必须通过调用 stopSelf() 自行停止运行，或者由另一个组件通过调用 stopService() 来停止它
        return super.onStartCommand(intent, flags, startId);
    }

    public void setAlarm(String todo, int code) {
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        intent = new Intent("android.intent.action.alarm");
        intent.putExtra("todo", todo);
        intent.putExtra("remindTypeCode", code);
        Log.d(TAG, "setAlarm: " + intent.getStringExtra("todo") + intent.getIntExtra("remindTypeCode", 0));
        calendar.set(year, (month-1), day, hour, minute, 0);
        pendingIntent = PendingIntent.getBroadcast(context, alarmCount++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm(String todo, String date, String time, int code) {
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("todo", todo);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("remindTypeCode", code);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + "service death");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
