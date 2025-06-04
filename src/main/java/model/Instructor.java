package model;

import java.sql.Date;

/**
 * Represents an instructor in the system
 */
public class Instructor {

    private String instructorId;
    private String instructorName;
    private Date dob;
    private boolean gender;
    private String subjectName;

    public Instructor() {
    }

    public Instructor(String instructorId, String instructorName, Date dob, boolean gender, String subjectName) {
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.dob = dob;
        this.gender = gender;
        this.subjectName = subjectName;
    }

    public Instructor(int id, String name, String email, String phone, String address, Date dob) {
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Date getdob() {
        return dob;
    }

    public void setdob(Date dob) {
        this.dob = dob;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
