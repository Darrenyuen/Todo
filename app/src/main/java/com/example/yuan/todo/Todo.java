package com.example.yuan.todo;

import java.io.Serializable;

public class Todo implements Serializable {

    private static final long serivalVersionUID = 1234567890L;

    String todo;
    String date;
    String time;
    int code;

    public Todo() {
        super();
    }

    public Todo(String todo, String date, String time, int code) {
        super();
        this.todo = todo;
        this.date = date;
        this.time = time;
        this.code = code;
    }

    public String getTodo() {
        return todo;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Todo: todo = " + todo + ", date = " + date + ", time = "  + time + ", code = " + code;
    }
}
