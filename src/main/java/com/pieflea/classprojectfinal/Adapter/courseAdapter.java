package com.pieflea.classprojectfinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.R;

import java.util.ArrayList;

//adapter for course recycler views used in the detailed term view
public class courseAdapter extends RecyclerView.Adapter<courseAdapter.myViewHolder> {

    Context context;
    ArrayList<Course> list;
    final public courseAdapter.courseClickListener myCourseClickListener;


    public courseAdapter(Context context, ArrayList<Course> Course, courseClickListener clickListener) {
        this.context = context;
        this.list = Course;
        this.myCourseClickListener = clickListener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item_layout, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final Course model = list.get(position);
        String setText = model.getName() + context.getString(R.string.spacer);
        holder.courseName.setText(setText);
        setText = model.getStartDate() + context.getString(R.string.spacer);
        holder.startDate.setText(setText);
        holder.endDate.setText(model.getEndDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView courseName;
        public TextView startDate;
        public TextView endDate;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.courseFragmentView);
            courseName = itemView.findViewById(R.id.course_item_name);
            startDate = itemView.findViewById(R.id.course_item_start);
            endDate = itemView.findViewById(R.id.course_item_end);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            myCourseClickListener.onListItemClick(position);
        }
    }

    public interface courseClickListener {
        void onListItemClick(int position);
    }
}