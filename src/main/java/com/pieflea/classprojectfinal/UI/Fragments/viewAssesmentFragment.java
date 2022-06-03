package com.pieflea.classprojectfinal.UI.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pieflea.classprojectfinal.Adapter.assesmentAdapter;
import com.pieflea.classprojectfinal.Database.DataHolder;
import com.pieflea.classprojectfinal.Models.Assessment;
import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.R;
import com.pieflea.classprojectfinal.UI.MainActivity;
import com.pieflea.classprojectfinal.UI.detailedAssessmentView;

import java.util.ArrayList;

public class viewAssesmentFragment extends Fragment implements assesmentAdapter.assessmentClickListener {

    assesmentAdapter adapter;
    public ArrayList<Assessment> list = new ArrayList<>();
    RecyclerView recyclerView;
    Course course;
    Long termID;
    Long courseID;

    public ArrayList<Assessment> getList() {
        return list;
    }

    public void setList(ArrayList<Assessment> list) {
        this.list = list;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.view_assesment_fragment, container, false);
        recyclerView = view.findViewById(R.id.assesmentFragmentView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        termID = getActivity().getIntent().getLongExtra("termID", -1L);
        courseID = getActivity().getIntent().getLongExtra("courseID", -1l);
        course = Course.getCourseByID(termID, courseID);
        list = course.getAssessments();
        adapter = new assesmentAdapter(this.getContext(), list, this);
        recyclerView.setAdapter(adapter);


        return view;
    }
    @Override
    public void onListItemClick(int position) {
        Intent detailAssessmentViewIntent = new Intent(getContext(), detailedAssessmentView.class);
        detailAssessmentViewIntent.putExtra("TermID", list.get(position).termID);
        detailAssessmentViewIntent.putExtra("CourseID", list.get(position).courseID);
        detailAssessmentViewIntent.putExtra("AssessmentID", list.get(position).ID);
        startActivity(detailAssessmentViewIntent);
    }
}