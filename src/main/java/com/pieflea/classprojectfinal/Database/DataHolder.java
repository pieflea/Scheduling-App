package com.pieflea.classprojectfinal.Database;

import android.util.Log;

import com.pieflea.classprojectfinal.Models.Assessment;
import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.Models.Event;
import com.pieflea.classprojectfinal.Models.Term;

import java.util.ArrayList;

public class DataHolder {
    public static ArrayList<Assessment> allAssessments = new ArrayList<>();
    public static ArrayList<Course> allCourses = new ArrayList<>();
    public static ArrayList<Term> terms = new ArrayList<>();
    public static ArrayList<Event> events = new ArrayList<>();

    public static ArrayList<Term> getTerms() {
        return terms;
    }

    public static void setTerms(ArrayList<Term> terms) {
        DataHolder.terms = terms;
    }

    public static void setEvents(ArrayList<Event> events) {DataHolder.events = events;}

    public static Term getTermByID(long ID){
        Term output = null;
        for (Term term: terms) {
            if (term.termID == ID){
                output = term;
            }
        }
        if (output == null){
            output = new Term(0L, "nullTerm", "nullTerm", "nullTerm");
        }
        return output;
    }




}
