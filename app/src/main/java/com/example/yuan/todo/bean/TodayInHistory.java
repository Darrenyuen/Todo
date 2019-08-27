package com.example.yuan.todo.bean;

/**
 * Created by jarvis yuen
 * Email: yuansssf@gmail.com
 */
public class TodayInHistory {
    String title;
    String date;
    String url;
    String luar;
    String des;

    public TodayInHistory(String title, String date, String luar, String url, String des) {
        super();
        this.title = title;
        this.date = date;
        this.luar = luar;
        this.url = url;
        this.des = des;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getLuar() {
        return luar;
    }

    public String getDes() {
        return des;
    }
}
