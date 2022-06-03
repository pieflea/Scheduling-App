package com.pieflea.classprojectfinal.UI;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pieflea.classprojectfinal.Database.DataHolder;
import com.pieflea.classprojectfinal.Database.TermStorage;
import com.pieflea.classprojectfinal.Models.Term;
import com.pieflea.classprojectfinal.R;

public class addTerm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_term);
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

    public void saveNewTerm(View view) {
        //Write function that saves the term with the given information
        Long lastTermId = 0L;
        if (DataHolder.terms.size() > 0){
            lastTermId = DataHolder.terms.get(DataHolder.terms.size() - 1).termID;
        }

        EditText termTitle = findViewById(R.id.courseTitle);
        EditText termStart = findViewById(R.id.editStartDate);
        EditText termEnd = findViewById(R.id.editEndDate);
        EditText notes = findViewById(R.id.termNotes);
        if (lastTermId != 0){
            DataHolder.terms.add(new Term (lastTermId,
                    termTitle.getText().toString(),
                    termStart.getText().toString(),
                    termEnd.getText().toString(),
                    notes.getText().toString()));

            MainActivity.termStorage.addTerm(
                    termTitle.getText().toString(),
                    termStart.getText().toString(),
                    termEnd.getText().toString(),
                    notes.getText().toString());

            Intent viewTermIntent = new Intent(this, viewTerms.class);

            startActivity(viewTermIntent);

        }
        else {
            DataHolder.terms.add(new Term (0L,
                    termTitle.getText().toString(),
                    termStart.getText().toString(),
                    termEnd.getText().toString(),
                    notes.getText().toString()));

            MainActivity.termStorage.addTerm(
                    termTitle.getText().toString(),
                    termStart.getText().toString(),
                    termEnd.getText().toString(),
                    notes.getText().toString());

            Intent viewTermIntent = new Intent(this, viewTerms.class);
            startActivity(viewTermIntent);

        }
        MainActivity.termStorage.populateTerms();
    }
}
