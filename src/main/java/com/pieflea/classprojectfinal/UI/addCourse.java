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
import android.widget.Toast;

import com.pieflea.classprojectfinal.Database.CourseStorage;
import com.pieflea.classprojectfinal.Database.DataHolder;
import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.Models.Term;
import com.pieflea.classprojectfinal.R;

public class addCourse extends AppCompatActivity {
    Long termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_course);
        termID = getIntent().getLongExtra("TermID", -1);
        if (termID > -1) {
            TextView termTitle = findViewById(R.id.TermTitle);
            termTitle.setText(DataHolder.getTermByID(termID).getName());
        }
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

    public void saveNewCourse(View view) {
        Long lastCourseId = 0L;
        if (DataHolder.getTermByID(termID).courses.size() > 0){
            lastCourseId = (long) DataHolder.getTermByID(termID).courses.size();
        }

        EditText courseTitle = findViewById(R.id.courseTitle);
        EditText courseStart = findViewById(R.id.editStartDate);
        EditText courseEnd = findViewById(R.id.editEndDate);
        EditText status = findViewById(R.id.courseStatus);
        EditText professorName = findViewById(R.id.contactName);
        EditText professorPhone = findViewById(R.id.contactPhone);
        EditText professorEmail = findViewById(R.id.contactEmail);
        EditText notes = findViewById(R.id.courseNotes);
        if (lastCourseId != 0){
            /*DataHolder.getTermByID(termID).courses.add(
                    new Course(lastCourseId,
                    courseTitle.getText().toString(),
                    courseStart.getText().toString(),
                    courseEnd.getText().toString(),
                    status.getText().toString(),
                    professorName.getText().toString(),
                    professorPhone.getText().toString(),
                    professorEmail.getText().toString(),
                    notes.getText().toString(),
                    termID));*/
            MainActivity.courseStorage.addCourse(
                    courseTitle.getText().toString(),
                    courseStart.getText().toString(),
                    courseEnd.getText().toString(),
                    status.getText().toString(),
                    professorName.getText().toString(),
                    professorPhone.getText().toString(),
                    professorEmail.getText().toString(),
                    notes.getText().toString(),
                    termID);
            Intent detailTermIntent = new Intent (this, detailedTermView.class);
            detailTermIntent.putExtra("ID", termID);
            startActivity(detailTermIntent);
        }
        else {
            /*DataHolder.getTermByID(termID).courses.add(
                    new Course(0L,
                            courseTitle.getText().toString(),
                            courseStart.getText().toString(),
                            courseEnd.getText().toString(),
                            status.getText().toString(),
                            professorName.getText().toString(),
                            professorPhone.getText().toString(),
                            professorEmail.getText().toString(),
                            notes.getText().toString(),
                            termID));*/
            MainActivity.courseStorage.addCourse(
                    courseTitle.getText().toString(),
                    courseStart.getText().toString(),
                    courseEnd.getText().toString(),
                    status.getText().toString(),
                    professorName.getText().toString(),
                    professorPhone.getText().toString(),
                    professorEmail.getText().toString(),
                    notes.getText().toString(),
                    termID);

            Intent detailTermIntent = new Intent(this, detailedTermView.class);
            detailTermIntent.putExtra("ID", termID);
            startActivity(detailTermIntent);
        }
        MainActivity.courseStorage.populateCourses();
    }
}