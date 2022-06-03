package com.pieflea.classprojectfinal.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pieflea.classprojectfinal.Models.Assessment;
import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.R;

public class detailedAssessmentView extends AppCompatActivity {
    Long termID;
    Long courseID;
    Long assessmentID;
    Assessment setAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.assessmentStorage.populateAssesments();
        setContentView(R.layout.activity_detailed_assessment_view);
        termID = getIntent().getLongExtra("TermID", -1L);
        courseID = getIntent().getLongExtra("CourseID", -1L);
        assessmentID = getIntent().getLongExtra("AssessmentID", -1L);
        setAssessment = Assessment.getAssessmentByID(termID, courseID, assessmentID);

        EditText assessmentTitle = findViewById(R.id.AssessmentTitle);
        EditText startDate = findViewById(R.id.editStartDate);
        EditText endDate = findViewById(R.id.editEndDate);
        EditText startTime = findViewById(R.id.editStartTime);
        EditText endTime = findViewById(R.id.editEndTime);
        EditText notes =  findViewById(R.id.assessmentNotes);
        TextView courseTitle = findViewById(R.id.courseTitle);

        assessmentTitle.setText(setAssessment.name);
        startDate.setText(setAssessment.startDate);
        endDate.setText(setAssessment.endDate);
        startTime.setText(setAssessment.startTime);
        endTime.setText(setAssessment.endTime);
        notes.setText(setAssessment.notes);
        courseTitle.setText(Course.getCourseByID(termID, courseID).getName());
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

    public void saveAssessmentChanges(View view) {
        MainActivity.assessmentStorage.saveAssessment(
                assessmentID,
                setAssessment.name,
                setAssessment.startDate,
                setAssessment.endDate,
                setAssessment.startTime,
                setAssessment.endTime,
                setAssessment.notes,
                courseID);
        Intent detailCourseIntent = new Intent (this, detailedCourseView.class);
        detailCourseIntent.putExtra("termID", termID);
        detailCourseIntent.putExtra("courseID", courseID);
        startActivity(detailCourseIntent);
    }

    public void deleteAssessment(View view) {
        MainActivity.assessmentStorage.removeAssessment(termID, courseID, assessmentID);
        Intent detailCourseIntent = new Intent (this, detailedCourseView.class);
        detailCourseIntent.putExtra("termID", termID);
        detailCourseIntent.putExtra("courseID", courseID);
        startActivity(detailCourseIntent);
    }
}
