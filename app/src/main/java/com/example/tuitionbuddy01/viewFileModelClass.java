package com.example.tuitionbuddy01;

public class viewFileModelClass {
    String fileName,fileURL;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public viewFileModelClass(String fileName, String fileURL) {
        this.fileName = fileName;
        this.fileURL = fileURL;
    }

    public viewFileModelClass() {
    }
}
