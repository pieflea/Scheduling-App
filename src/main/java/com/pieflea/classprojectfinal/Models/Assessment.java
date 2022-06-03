package com.pieflea.classprojectfinal.Models;

public class Assessment {
    public Long ID;
    public String name;
    public String startDate;
    public String endDate;
    public String startTime;
    public String endTime;
    public String notes;
    public Long termID;
    public Long courseID;

    public Assessment(Long ID, String name, String startDate, String endDate, String startTime, String endTime, String notes, Long termID, Long courseID) {
        this.ID = ID;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
        this.termID = termID;
        this.courseID = courseID;
    }

    public Assessment(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public static Assessment getAssessmentByID(Long termID, Long courseID, Long assessmentID){
        Course currentCourse = Course.getCourseByID(termID, courseID);
        Assessment output = new Assessment();
        for (Assessment assessment: currentCourse.assessments) {
            if (assessment.ID == assessmentID){
                output = assessment;
            }
        }
        return output;
    }
}
