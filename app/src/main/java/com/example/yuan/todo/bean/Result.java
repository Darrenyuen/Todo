package com.example.yuan.todo.bean;

/**
 * Created by jarvis yuen
 * Email: yuansssf@gmail.com
 */
public class Result {
    /**
     * {
     * "_id":"12700825",
     * "title":"法国卡佩王朝路易九世逝世",
     * "pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201108/5/108432011211845.jpg",
     * "year":1270,
     * "month":8,
     * "day":25,
     * "des":"在749年前的今天，1270年8月25日 (农历八月初八)，法国卡佩王朝路易九世逝世。",
     * "lunar":"庚午年八月初八"
     * }
     */
    String _id;
    String title;
    String pic;
    int year;
    int month;
    int day;
    String des;
    String lunar;

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getPic() {
        return pic;
    }

    public String getLunar() {
        return lunar;
    }

    public String getDes() {
        return des;
    }
}
