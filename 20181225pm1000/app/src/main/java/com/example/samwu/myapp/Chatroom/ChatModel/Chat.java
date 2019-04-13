package com.example.samwu.myapp.Chatroom.ChatModel;

public class Chat {


    String sender,receiver,message,mDate,Dadte1,mNowTime,mShowTime,mMonth;


    public Chat(){}


    public Chat(String sender, String receiver, String message, String date
            , String nowTime, String showTime, String month) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        mDate = date;
        mNowTime = nowTime;
        mShowTime = showTime;
        mMonth = month;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getNowTime() {
        return mNowTime;
    }

    public void setNowTime(String nowTime) {
        mNowTime = nowTime;
    }

    public String getShowTime() {
        return mShowTime;
    }

    public void setShowTime(String showTime) {
        mShowTime = showTime;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }
}
