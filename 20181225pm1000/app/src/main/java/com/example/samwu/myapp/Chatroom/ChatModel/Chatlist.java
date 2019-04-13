package com.example.samwu.myapp.Chatroom.ChatModel;

public class Chatlist {

    public String profileimg,sender,receiver;

    public  Chatlist(){}

    public Chatlist( String profileimg, String sender, String receiver) {

        this.profileimg = profileimg;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
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
}
