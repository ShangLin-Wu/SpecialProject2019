package com.example.samwu.myapp.Chatroom.ChatModel;

public class getUserInfoForChat {

    String email;
    String stdid;
    String stdclass;
    String phone;
    String profileimg;
    String message;
    String nowtime;

    public  getUserInfoForChat()
    {

    }


    public getUserInfoForChat(String email, String stdid, String stdclass, String phone, String profileimg, String message, String nowtime) {
        this.email = email;
        this.stdid = stdid;
        this.stdclass = stdclass;
        this.phone = phone;
        this.profileimg = profileimg;
        this.message = message;
        this.nowtime = nowtime;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStdid() {
        return stdid;
    }

    public void setStdid(String stdid) {
        this.stdid = stdid;
    }

    public String getStdclass() {
        return stdclass;
    }

    public void setStdclass(String stdclass) {
        this.stdclass = stdclass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNowtime() {
        return nowtime;
    }

    public void setNowtime(String nowtime) {
        this.nowtime = nowtime;
    }


}
