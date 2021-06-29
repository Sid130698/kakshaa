package com.example.tuitionbuddy01;

public class ModelAnnouncementClass {
    String Message,Name;
    ModelAnnouncementClass(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ModelAnnouncementClass(String name, String message) {
        Name = name;
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
