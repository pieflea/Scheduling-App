package com.pieflea.classprojectfinal.UI;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pieflea.classprojectfinal.Database.DataHolder;
import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.R;
import com.pieflea.classprojectfinal.UI.Fragments.viewAssesmentFragment;

public class detailedCourseView extends AppCompatActivity {

    Long courseID;
    Long termID;
    Course setCourse;
    EditText courseTitle;
    EditText courseStart;
    EditText courseEnd;
    TextView termTitle;
    EditText status;
    EditText contactName;
    EditText contactPhone;
    EditText contactEmail;
    EditText notes;

    //TODO: make function save course changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course_view);
        MainActivity.assessmentStorage.populateAssesments();
        courseID  = getIntent().getLongExtra("courseID", 0L);
        termID = getIntent().getLongExtra("termID", 0L);
        if (courseID > -1){

            setCourse = Course.getCourseByID(termID, courseID);

            courseTitle = findViewById(R.id.courseTitle);
            courseStart = findViewById(R.id.editStartDate);
            courseEnd = findViewById(R.id.editEndDate);
            termTitle = findViewById(R.id.courseTermTitle);
            status = findViewById(R.id.courseStatus);
            contactName = findViewById(R.id.contactName);
            contactPhone = findViewById(R.id.contactPhone);
            contactEmail = findViewById(R.id.contactEmail);
            notes = findViewById(R.id.courseDescription);

            courseTitle.setText(setCourse.name);
            courseStart.setText(setCourse.startDate);
            courseEnd.setText(setCourse.endDate);
            termTitle.setText(DataHolder.getTermByID(setCourse.termID).getName());
            status.setText(setCourse.status);
            contactName.setText(setCourse.professorName);
            contactPhone.setText(setCourse.professorPhone);
            contactEmail.setText(setCourse.professorEmail);
            notes.setText(setCourse.notes);
            Log.i("CourseID", setCourse.courseID.toString());
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

    public void addNewAssesment(View view) {
        Intent addAssessmentIntent = new Intent(this, addAssessment.class);
        addAssessmentIntent.putExtra("TermID", getIntent().getLongExtra("termID", -1L));
        addAssessmentIntent.putExtra("CourseID", getIntent().getLongExtra("courseID", -1L));
        startActivity(addAssessmentIntent);
    }

    public void removeCourse(View view) {
        MainActivity.courseStorage.removeCourse(termID, courseID);
        Intent detailTermIntent = new Intent (this, detailedTermView.class);
        detailTermIntent.putExtra("ID", termID);
        startActivity(detailTermIntent);
    }

    public void saveCourseChanges(View view) {
        MainActivity.courseStorage.saveCourse(courseID,
                courseTitle.getText().toString(),
                courseStart.getText().toString(),
                courseEnd.getText().toString(),
                status.getText().toString(),
                contactName.getText().toString(),
                contactPhone.getText().toString(),
                contactEmail.getText().toString(),
                notes.getText().toString());
        Intent detailTermIntent = new Intent (this, detailedTermView.class);
        detailTermIntent.putExtra("ID", termID);
        startActivity(detailTermIntent);
    }


    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.i("Permission Granted ", "True");
                }
                else {
                    Log.i("Permission Granted ", "False");
                    ActivityCompat.requestPermissions(detailedCourseView.this, new String[]{Manifest.permission.SEND_SMS}, 0);
                }
            });

    private void requestPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            //Permission is Granted
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    public void sendNotes(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("phone number requested");
            alert.setMessage("Who are we sending this to? (please enter 10 digits only)");

            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    String phoneNumber = "+" + value;
                    String smsText = "Notes on " + setCourse.name + ":" + "\n" + setCourse.notes;
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, smsText, null, null);
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });


            alert.show();

        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }
}