package com.example.user.mychatappfinal;

/**
 * Created by user on 12/6/2016.
 */

public class Record {

    String name, msg, key, userId;
    int pic;


    public Record() {
    }

    public Record(String name, String msg, String key, String userId, int pic) {
        this.name = name;
        this.msg = msg;
        this.key = key;
        this.userId = userId;
        this.pic = pic;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
