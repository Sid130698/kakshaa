package com.example.tuitionbuddy01;

public class modelTeacherChatMenuClass {
    String teacherName,userId;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public modelTeacherChatMenuClass() {
    }

    public modelTeacherChatMenuClass(String teacherName, String userId) {
        this.teacherName = teacherName;
        this.userId = userId;
    }
}
