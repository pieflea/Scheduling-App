package com.pieflea.classprojectfinal.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pieflea.classprojectfinal.Models.Assessment;
import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.R;

public class addAssessment extends AppCompatActivity {
    Long termID;
    Long courseID;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_assessment);
        termID = getIntent().getLongExtra("TermID", -1L);
        courseID = getIntent().getLongExtra("CourseID", -1L);
        course = Course.getCourseByID(termID, courseID);
        TextView courseTitle = findViewById(R.id.courseTitle);
        courseTitle.setText(course.getName());
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

    public void saveNewAssessment(View view) {
        //Write function that saves the assessment with the given information
        Long lastAssessmentId = 0L;
        if (course.assessments.size() > 0){
            lastAssessmentId = (long) course.assessments.size();
        }

        EditText AssessmentTitle = findViewById(R.id.AssessmentTitle);
        EditText AssessmentStart = findViewById(R.id.editStartDate);
        EditText AssessmentEnd = findViewById(R.id.editEndDate);
        EditText AssessmentStartTime = findViewById(R.id.editStartTime);
        EditText AssessmentEndTime = findViewById(R.id.editEndTime);
        EditText Notes = findViewById(R.id.assessmentNotes);
        if (lastAssessmentId != 0){


            MainActivity.assessmentStorage.addAssessment(
                    AssessmentTitle.getText().toString(),
                    AssessmentStart.getText().toString(),
                    AssessmentEnd.getText().toString(),
                    AssessmentStartTime.getText().toString(),
                    AssessmentEndTime.getText().toString(),
                    Notes.getText().toString(),
                    courseID);

            Intent detailCourseIntent = new Intent (this, detailedCourseView.class);
            detailCourseIntent.putExtra("termID", termID);
            detailCourseIntent.putExtra("courseID", courseID);
            startActivity(detailCourseIntent);
        }
        else {
            MainActivity.assessmentStorage.addAssessment(
                    AssessmentTitle.getText().toString(),
                    AssessmentStart.getText().toString(),
                    AssessmentEnd.getText().toString(),
                    AssessmentStartTime.getText().toString(),
                    AssessmentEndTime.getText().toString(),
                    Notes.getText().toString(),
                    courseID);
            Intent detailCourseIntent = new Intent (this, detailedCourseView.class);
            detailCourseIntent.putExtra("termID", termID);
            detailCourseIntent.putExtra("courseID", courseID);
            startActivity(detailCourseIntent);
        }
        MainActivity.assessmentStorage.populateAssesments();
    }
}