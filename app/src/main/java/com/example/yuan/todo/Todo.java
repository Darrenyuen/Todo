package com.example.yuan.todo;

public class Todo {

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

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getTodo() {
        return todo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Todo: todo = " + todo + ", date = " + date + ", time = "  + time + ", code = " + code;
    }
}
