package com.sxdtdx.aitou.model.bean;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by xinyuan on 17-5-7.
 */

public class Votes extends BmobObject{
    private String userName;
    private String phone;
    private String title;
    private String describe;
    private String coverUrl;
    private List<String> options;
    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCover() {
        return coverUrl;
    }

    public void setCover(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Votes{" +
                "userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", coverUrl=" + coverUrl +
                ", options=" + options +
                ", time='" + time + '\'' +
                '}';
    }
}
