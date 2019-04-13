package com.example.samwu.myapp.Model;

import android.provider.ContactsContract;
import android.widget.EditText;

public class User {

    String email;
    String password;
    String stdid;
    String stdclass;
    String phone;
    String profileimg;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public User(String email, String password, String stdid, String stdclass, String phone, String profileimg) {
        this.email = email;
        this.password = password;
        this.stdid = stdid;
        this.stdclass = stdclass;
        this.phone = phone;
        this.profileimg = profileimg;
    }


    public User() {


    }



}
