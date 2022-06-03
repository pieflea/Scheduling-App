package com.pieflea.classprojectfinal.Models;

import com.pieflea.classprojectfinal.Database.DataHolder;

import java.util.ArrayList;

public class Course {
    public Long courseID;
    public String name;
    public String startDate;
    public String endDate;
    public ArrayList <Assessment> assessments = new ArrayList<>();
    public String status;
    public String professorName;
    public String professorPhone;
    public String professorEmail;
    public String notes;
    public Long termID;

    public Course(Long courseID, String name, String startDate, String endDate, String status, String professorName, String professorPhone, String professorEmail, String notes, Long termID) {
        this.courseID = courseID;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.professorName = professorName;
        this.professorPhone = professorPhone;
        this.professorEmail = professorEmail;
        this.notes = notes;
        this.termID = termID;
    }

    public Course(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(ArrayList<Assessment> assessments) {
        this.assessments = assessments;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getProfessorPhone() {
        return professorPhone;
    }

    public void setProfessorPhone(String professorPhone) {
        this.professorPhone = professorPhone;
    }

    public String getProfessorEmail() {
        return professorEmail;
    }

    public void setProfessorEmail(String professorEmail) {
        this.professorEmail = professorEmail;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static Course getCourseByID(long termID, long courseID){
        Course output = new Course();
        if (termID > -1 && courseID > -1){
            for (Course course: DataHolder.getTermByID(termID).courses) {
                if (courseID == course.getCourseID()){
                    output = course;
                }
            }
        }
        return output;
    }
}
