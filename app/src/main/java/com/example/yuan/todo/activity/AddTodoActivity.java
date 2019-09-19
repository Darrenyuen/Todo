package com.example.yuan.todo.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.yuan.todo.AlarmService;
import com.example.yuan.todo.DatabaseHelper;
import com.example.yuan.todo.R;
import com.example.yuan.todo.util.ToastUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTodoActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.todo)
    EditText editText;
    @BindView(R.id.date)
    TextView dateText;
    @BindView(R.id.time)
    TextView timeText;
    @BindView(R.id.remindType)
    TextView remindType;

    private Calendar calendar;

    private String todo;
    private StringBuffer date = new StringBuffer();
    private int year;
    private int month;
    private int day;
    private StringBuffer time = new StringBuffer();
    private int hour;
    private int minute;
    int remindTypeCode = 0; //默认为响铃
    final String[] styles = {"响铃", "振动", "不提醒"};

    @OnClick({R.id.date, R.id.time, R.id.remindType, R.id.sureAdd, R.id.cancellAdd})
    public void onclick(View view) {

        switch (view.getId()) {
            case R.id.date: showDatePickDlg(); break;
            case R.id.time: showTimePickDlg(); break;
            case R.id.remindType: showChoise(); break;
            case R.id.sureAdd: addTodo(); break;
            case R.id.cancellAdd: cancellAdd(); break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month < 10) {
            date.append(year + "-0" + month);
        } else date.append(year + "-" + month);
        if (day < 10) {
            date.append("-0" + day);
        } else date.append("-" + day);
//        date = String.valueOf(year + "-" + month + "-" + day);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        if (hour < 10) {
            time.append("0" + hour);
        } else time.append(hour);
        if (minute < 10) {
            time.append(":0" + minute);
        } else time.append(":"  + minute);
//        time = String.valueOf(hour + ":" + minute);
        // TODO: 2019/8/20 调整初步显示时间的格式
        dateText.setText(date);
        timeText.setText(time);
        Log.d(TAG, "onCreate: " + date + " " + time);
    }

    public void showDatePickDlg() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mon = "", day = "";
                if (month < 9) {
                    mon = "0" + (month + 1);
                } else mon = String.valueOf((month+1));
                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth;
                } else day = String.valueOf(dayOfMonth);
                dateText.setText(year + "-" + mon + "-" + day);
                Log.d(TAG, "onDateSet: " + year + " " + month + " " + dayOfMonth);
            }
        }, year, month-1, day);
        datePickerDialog.show();
    }

    public void showTimePickDlg() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour = "", min = "";
                if (hourOfDay < 10) {
                    hour = "0" + hourOfDay;
                } else hour = String.valueOf(hourOfDay);
                if (minute < 10) {
                    min = "0" + minute;
                } else min = String.valueOf(minute);
                timeText.setText(hour + ":" + min);
            }
        }, hour, minute, true).show();
    }

    public void showChoise() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddTodoActivity.this);
        builder.setTitle("请选择提醒方式");
        builder.setItems(styles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: " + which);
                remindType.setText(styles[which]);
                remindTypeCode = which;
            }
        });
        builder.create().show();
    }

    public void addTodo() {
        if (editText.getText().toString().equals("")) {
            ToastUtil.showShort(this, "事项不能为空");
            return;
        }
        //判断日期选择是否合理
        String[] dateSeleted = dateText.getText().toString().split("-");
        int yearSeleted = Integer.parseInt(dateSeleted[0]);
        int monthSeleted = Integer.parseInt(dateSeleted[1]);
        int daySeleted = Integer.parseInt(dateSeleted[2]);
        if (yearSeleted < year || (yearSeleted == year && monthSeleted < month) || (yearSeleted == year && monthSeleted == month && daySeleted < day)) {
            ToastUtil.showShort(this, "请选择合理日期");
            return;
        }
        //判断时间选择是否合理
        String[] timeSeleted = timeText.getText().toString().split(":");
        Log.d(TAG, "addTodo: " + timeSeleted[0] + ":" + timeSeleted[1]);
        int hourSeleted = Integer.parseInt(timeSeleted[0]);
        int minuteSelete = Integer.parseInt(timeSeleted[1]);
        Log.d(TAG, "addTodo: " + yearSeleted + year + " " + monthSeleted + month + " " + daySeleted + day + " " + hourSeleted + calendar.get(Calendar.HOUR_OF_DAY) + " " + minuteSelete + calendar.get(Calendar.MINUTE));
        if (yearSeleted == year && monthSeleted == month && daySeleted == day) {
            if (hourSeleted < calendar.get(Calendar.HOUR_OF_DAY) || (hourSeleted == calendar.get(Calendar.HOUR_OF_DAY) && minuteSelete <= calendar.get(Calendar.MINUTE))) {
                ToastUtil.showShort(this, "请选择合理时间");
                return;
            }
        }
        Log.d(TAG, "addTodo: " + yearSeleted + "-" + monthSeleted + "-" + daySeleted + " " + hourSeleted + ":" + minuteSelete);

        // TODO: 2019/8/21 将数据写入SQLite
        SQLiteOpenHelper dbHelper = new DatabaseHelper(this, "TODO", null, 1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("id", 1); //只有在第一次提交时需要执行或者用一个布尔变量判断是否是第一次上传数据
        values.put("title", editText.getText().toString());
        values.put("date", dateText.getText().toString());
        values.put("time", timeText.getText().toString());
        values.put("code", remindTypeCode);
        sqLiteDatabase.insert("todo", null, values);
        Log.d(TAG, "addTodo: " + "写入数据库");
        sqLiteDatabase.close();

        Intent intentToService = new Intent(AddTodoActivity.this, AlarmService.class);
        intentToService.putExtra("todo", editText.getText().toString());
        intentToService.putExtra("date", dateText.getText().toString());
        intentToService.putExtra("time", timeText.getText().toString());
        intentToService.putExtra("remindTypeCode", remindTypeCode);
        intentToService.putExtra("isSetAlarm", true);
        Log.d(TAG, "addTodo: " + editText.getText().toString() + remindTypeCode);
        startService(intentToService);

        Intent intentToMainAct = new Intent(AddTodoActivity.this, MainActivity.class);
        startActivity(intentToMainAct);
    }

    public void cancellAdd() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
