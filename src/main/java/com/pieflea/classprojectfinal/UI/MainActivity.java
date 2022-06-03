package com.pieflea.classprojectfinal.UI;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.pieflea.classprojectfinal.Database.AssessmentStorage;
import com.pieflea.classprojectfinal.Database.CourseStorage;
import com.pieflea.classprojectfinal.Database.EventStorage;
import com.pieflea.classprojectfinal.Database.TermStorage;
import com.pieflea.classprojectfinal.Models.Assessment;
import com.pieflea.classprojectfinal.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static TermStorage termStorage;
    public static CourseStorage courseStorage;
    public static AssessmentStorage assessmentStorage;
    public static ArrayList<Assessment> posList;
    public static EventStorage eventStorage;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        termStorage = new TermStorage(this);
        termStorage.populateTerms();
        courseStorage = new CourseStorage(this);
        courseStorage.populateCourses();
        assessmentStorage = new AssessmentStorage(this);
        assessmentStorage.populateAssesments();
        eventStorage = new EventStorage(this);
        eventStorage.populateEvents();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbarlayout, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.helpButton:
                Intent helpIntent = new Intent(this, HelpPage.class);
                startActivity(helpIntent);
                return true;
            case R.id.aboutButton:
                Intent aboutIntent = new Intent(this, aboutPage.class);
                startActivity(aboutIntent);
                return true;

            case R.id.viewTerms:
                Intent viewTermIntent = new Intent(this, viewTerms.class);
                startActivity(viewTermIntent);
                return true;

            case R.id.viewReport:
                Intent viewReportIntent = new Intent(this, termReport.class);
                startActivity(viewReportIntent);
                return true;

            /* Deprecated features, more trouble than they were worth.
            case R.id.viewCourses:
                Intent viewCourseIntent = new Intent(this, viewCourses.class);
                startActivity(viewCourseIntent);
                return true;

            case R.id.viewAssesments:
                Intent viewAssessmentIntent = new Intent(this, viewAssessments.class);
                startActivity(viewAssessmentIntent);
                return true;*/

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void addNotification(View view) {
        Intent addNotificationIntent = new Intent(this, addNotification.class);
        startActivity(addNotificationIntent);
    }
}