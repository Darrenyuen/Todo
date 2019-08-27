package com.example.yuan.todo.util;

import com.example.yuan.todo.bean.Bean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jarvis yuen
 * Email: yuansssf@gmail.com
 */
public interface DataAPI {
    /**
     *  http://api.juheapi.com/japi/toh?key=48d0bc89de1ea3be90e78aa9f1d79a12&v=1.0&month=8&day=25
     *  key : 48d0bc89de1ea3be90e78aa9f1d79a12
     *  v : 1.0
     *  month
     *  day
     */
    @GET("toh?key=48d0bc89de1ea3be90e78aa9f1d79a12&v=1.0")
    Observable<Bean> getData(@Query("month") int month, @Query("day") int day);
}
