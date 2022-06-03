package com.pieflea.classprojectfinal.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pieflea.classprojectfinal.Adapter.courseAdapter;
import com.pieflea.classprojectfinal.Database.DataHolder;
import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.R;
import com.pieflea.classprojectfinal.UI.MainActivity;
import com.pieflea.classprojectfinal.UI.detailedCourseView;

import java.util.ArrayList;

public class viewCourseFragment extends Fragment implements courseAdapter.courseClickListener {

    courseAdapter adapter;
    ArrayList<Course> list = new ArrayList<>();
    RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.view_course_fragment, container, false);
        MainActivity.courseStorage.populateCourses();
        recyclerView = view.findViewById(R.id.courseFragmentView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            list = DataHolder.getTermByID(getActivity().getIntent().getLongExtra("ID", 0L)).courses;

        adapter = new courseAdapter(this.getContext(), list, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(int position) {
        Intent detailCourseIntent = new Intent (getContext(), detailedCourseView.class);
        detailCourseIntent.putExtra("courseID", list.get(position).courseID);
        detailCourseIntent.putExtra("termID", getActivity().getIntent().getLongExtra("ID", -1L));
        startActivity(detailCourseIntent);
    }
}