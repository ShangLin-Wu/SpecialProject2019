package com.example.samwu.myapp.Chatroom.ChatModel;

public class userlist {

    String email, nowtime, message,mid,profileimg;

    public userlist() {
    }


    public userlist(String email, String nowtime, String message, String mid, String profileimg) {
        this.email = email;
        this.nowtime = nowtime;
        this.message = message;
        this.mid = mid;
        this.profileimg = profileimg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNowtime() {
        return nowtime;
    }

    public void setNowtime(String nowtime) {
        this.nowtime = nowtime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }
}