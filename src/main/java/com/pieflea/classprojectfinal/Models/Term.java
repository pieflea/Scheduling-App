package com.pieflea.classprojectfinal.Models;

import java.util.ArrayList;

public class Term {
    public Long termID;
    public String name;
    public String startDate;
    public String endDate;
    public ArrayList<Course> courses;
    public String notes;

    public Term(Long termID, String name, String startDate, String endDate) {
        this.termID = termID;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courses = new ArrayList<>();
    }
    public Term(Long termID, String name, String startDate, String endDate, String notes){
        this.termID = termID;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
        this.courses = new ArrayList<>();
    }

    public Term(){}

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

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
