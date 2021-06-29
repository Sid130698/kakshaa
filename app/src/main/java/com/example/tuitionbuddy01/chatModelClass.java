package com.example.tuitionbuddy01;

public class chatModelClass {
    String From,To,Message;

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public chatModelClass() {
    }

    public chatModelClass(String from, String to, String message) {
        From = from;
        To = to;
        Message = message;
    }
}
