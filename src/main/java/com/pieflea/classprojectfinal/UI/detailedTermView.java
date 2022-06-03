package com.pieflea.classprojectfinal.UI;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pieflea.classprojectfinal.Adapter.courseAdapter;
import com.pieflea.classprojectfinal.Database.DataHolder;
import com.pieflea.classprojectfinal.Database.TermStorage;
import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.Models.Term;
import com.pieflea.classprojectfinal.R;

import java.util.ArrayList;


//TODO: make view save term changes
public class detailedTermView extends AppCompatActivity {
    Long termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term_view);
        termID = getIntent().getLongExtra("ID", -1L);
        MainActivity.courseStorage.populateCourses();
        if (termID > -1){

            EditText title = findViewById(R.id.termTitle);
            EditText startDate = findViewById(R.id.editStartDate);
            EditText endDate = findViewById(R.id.editEndDate);
            EditText notes = findViewById(R.id.termNotes);

            title.setText(DataHolder.getTermByID(termID).getName());
            startDate.setText(DataHolder.getTermByID(termID).getStartDate());
            endDate.setText(DataHolder.getTermByID(termID).getEndDate());
            notes.setText(DataHolder.getTermByID(termID).getNotes());

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

    public void saveTermChanges(View view) {
        EditText termTitle = findViewById(R.id.termTitle);
        EditText termStart = findViewById(R.id.editStartDate);
        EditText termEnd = findViewById(R.id.editEndDate);
        EditText notes = findViewById(R.id.termNotes);

        Term currentTerm = DataHolder.getTermByID(termID);

        currentTerm.setName(termTitle.getText().toString());
        currentTerm.setStartDate(termStart.getText().toString());
        currentTerm.setEndDate(termEnd.getText().toString());
        currentTerm.setNotes(notes.getText().toString());

        MainActivity.termStorage.saveTerms(
                termID,
                termTitle.getText().toString(),
                termStart.getText().toString(),
                termEnd.getText().toString(),
                notes.getText().toString());

        Intent viewTermIntent = new Intent(this, viewTerms.class);
        startActivity(viewTermIntent);
    }

    public void removeTerm(View view) {
        if (DataHolder.getTermByID(termID).courses.size() > 0) {
            Toast.makeText(this, "Cannot delete term with courses assigned", Toast.LENGTH_LONG).show();
        }
        else{
            MainActivity.termStorage.removeTerm(termID);
            Intent viewTermIntent = new Intent(this, viewTerms.class);
            startActivity(viewTermIntent);
        }
    }

    public void addCourse(View view) {
        MainActivity.courseStorage.populateCourses();
        Intent newCourseIntent = new Intent(this, addCourse.class);
        newCourseIntent.putExtra("TermID", termID );
        startActivity(newCourseIntent);
    }
}