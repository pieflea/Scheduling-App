package com.pieflea.classprojectfinal.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pieflea.classprojectfinal.Adapter.termAdapter;
import com.pieflea.classprojectfinal.Database.DataHolder;
import com.pieflea.classprojectfinal.Models.Term;
import com.pieflea.classprojectfinal.R;

import java.util.ArrayList;
import java.util.List;

public class viewTerms extends AppCompatActivity implements termAdapter.termClickListener {

    termAdapter adapter;
    ArrayList<Term> searchTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_terms);

        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        adapter = new termAdapter(viewTerms.this, DataHolder.terms, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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

    public void addNewTerm(View view) {
        Intent newTermIntent = new Intent(this, addTerm.class);
        startActivity(newTermIntent);
    }

    @Override
    public void onListItemClick(int position) {
        Intent detailTermIntent = new Intent (this, detailedTermView.class);
        detailTermIntent.putExtra("ID", DataHolder.terms.get(position).termID);
        startActivity(detailTermIntent);
    }

    public void SearchTerms(View view) {
        searchTerms = new ArrayList<>();
        MainActivity.termStorage.populateTerms();
        EditText searchBar = findViewById(R.id.termSearch);
        String criteria = searchBar.getText().toString();
        if (!criteria.isEmpty()) {
            for (Term t : DataHolder.terms) {
                if (t.getName().contains(criteria)) {
                    searchTerms.add(t);
                }
            }
            RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
            adapter = new termAdapter(viewTerms.this, searchTerms, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
        else{
            RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
            adapter = new termAdapter(viewTerms.this, DataHolder.terms, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}
