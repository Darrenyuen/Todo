package com.example.yuan.todo.bean;

import java.util.List;

/**
 * Created by jarvis yuen
 * Email: yuansssf@gmail.com
 */
public class Bean {
    int error_code;
    String reason;
    List<Result> result;    //必须要与返回的数据项名一致

    public int getError_code() {
        return error_code;
    }

    public String getReason() {
        return reason;
    }

    public List<Result> getResultList() {
        return result;
    }

}
