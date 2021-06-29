package com.example.tuitionbuddy01;

public class requestModelClass {
    String studentName,studentID,physics,chemistry,mathematics,isAccepted,isRejected;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getPhysics() {
        return physics;
    }

    public void setPhysics(String physics) {
        this.physics = physics;
    }

    public String getChemistry() {
        return chemistry;
    }

    public void setChemistry(String chemistry) {
        this.chemistry = chemistry;
    }

    public String getMathematics() {
        return mathematics;
    }

    public void setMathematics(String mathematics) {
        this.mathematics = mathematics;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(String isRejected) {
        this.isRejected = isRejected;
    }

    public requestModelClass(String studentName, String studentID, String physics, String chemistry, String mathematics, String isAccepted, String isRejected) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.physics = physics;
        this.chemistry = chemistry;
        this.mathematics = mathematics;
        this.isAccepted = isAccepted;
        this.isRejected = isRejected;
    }

    public requestModelClass() {
    }
}
