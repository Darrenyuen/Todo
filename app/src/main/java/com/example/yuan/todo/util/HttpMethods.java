package com.example.yuan.todo.util;

import com.example.yuan.todo.bean.Bean;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;
import rx.schedulers.Schedulers;

/**
 * Created by jarvis yuen
 * Email: yuansssf@gmail.com
 */

/**
 * 单例模式实现
 */
public class HttpMethods {

    private String TAG = this.getClass().getSimpleName();

    private static final String BASE_URL = "http://api.juheapi.com/japi/";
    private static final int DEFAULT_TIMEOUT = 5;

    private DataAPI dataAPI;

    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        dataAPI = retrofit.create(DataAPI.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void getDate(Subscriber<Bean> subscriber, int month, int day) {
        dataAPI.getData(month, day)
                .subscribeOn(Schedulers.io())   //指定subscribe()发生在IO线程
                .unsubscribeOn(Schedulers.io()) //避免内存泄露
                .observeOn(AndroidSchedulers.mainThread())  //指定Subscriber的回调发生在主线程
                .subscribe(subscriber);
    }
}
