package com.example.yuan.todo.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yuan.todo.R;
import com.example.yuan.todo.adapter.TodayHisAdapter;
import com.example.yuan.todo.bean.Bean;
import com.example.yuan.todo.bean.Result;
import com.example.yuan.todo.bean.TodayInHistory;
import com.example.yuan.todo.util.HttpMethods;
import com.example.yuan.todo.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class TodayInHistoryActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    @BindView(R.id.listView)
    ListView listView;

    private Calendar calendar;
    private int month;
    private int day;
    private List<Result> resultList;
    private List<TodayInHistory> todayInHistoryList;
    private TodayHisAdapter todayHisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todayinhis);
        ButterKnife.bind(this);
        todayInHistoryList = new LinkedList<>();
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d(TAG, "onCreate: " + month + " " + day);
        getData(month, day);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showShort(TodayInHistoryActivity.this, todayInHistoryList.get(position).getTitle());
            }
        });
    }

    public void getData(int month, int day) {
        Log.d(TAG, "getData: " + month + day);
        HttpMethods.getInstance().getDate(new Subscriber<Bean>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: " + todayInHistoryList.size());
                todayHisAdapter = new TodayHisAdapter(TodayInHistoryActivity.this, R.layout.item_history, todayInHistoryList);
                listView.setAdapter(todayHisAdapter);
                ToastUtil.showShort(TodayInHistoryActivity.this, "加载完成");
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(TodayInHistoryActivity.this, "加载出错");
                Log.d(TAG, "onError: " + e.toString());
            }

            @Override
            public void onNext(Bean bean) {
                Log.d(TAG, "onNext: " + bean.getReason());
                Log.d(TAG, "onNext: " + bean.getError_code());
                Log.d(TAG, "onNext: " + bean.getResultList().size());
                if (bean.getError_code() == 0) {
                    resultList = bean.getResultList();
                    Log.d(TAG, "onNext: " + resultList.size());
                    for (int i=0; i<resultList.size(); i++) {
                        String title = resultList.get(i).getTitle();
                        int year = resultList.get(i).getYear();
                        int month = resultList.get(i).getMonth();
                        int day = resultList.get(i).getDay();
                        String date = String.valueOf(year + "-" + month + "-" + day);
                        TodayInHistory todayInHistory = new TodayInHistory(title, date);
                        todayInHistoryList.add(todayInHistory);
                    }
                }
                Log.d(TAG, "onNext: " + todayInHistoryList.size());
            }
        }, month, day);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + todayInHistoryList.size());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + todayInHistoryList.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + todayInHistoryList.size());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + todayInHistoryList.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + todayInHistoryList.size());
    }
}
