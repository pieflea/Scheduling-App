package com.pieflea.classprojectfinal.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Event {
    String Description;
    String user;
    LocalDateTime now;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Event(String description) {
        Description = "Term " + description + " added.";
        this.user = "test";
        this.now = LocalDateTime.now();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Event(String description, String time){
        Description = "Term " + description + " added.";
        this.user = "test";
        this.now = LocalDateTime.parse(time);
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }
}
