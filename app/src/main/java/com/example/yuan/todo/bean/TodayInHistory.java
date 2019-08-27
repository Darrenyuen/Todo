package com.example.yuan.todo.bean;

/**
 * Created by jarvis yuen
 * Email: yuansssf@gmail.com
 */
public class TodayInHistory {
    String title;
    String date;

    public TodayInHistory() {
        super();
    }

    public TodayInHistory(String title, String date) {
        super();
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
