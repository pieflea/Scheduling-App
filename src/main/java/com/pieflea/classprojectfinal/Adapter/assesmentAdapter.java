package com.pieflea.classprojectfinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pieflea.classprojectfinal.Models.Assessment;
import com.pieflea.classprojectfinal.R;

import java.util.ArrayList;


//Adapter for the assesment recycler views, implements the recycler view on the main page and each individual course view
//TODO: filter assessments based on assigned classes and date
public class assesmentAdapter extends RecyclerView.Adapter<assesmentAdapter.myViewHolder> {

    Context context;
    ArrayList<Assessment> list;
    final public assessmentClickListener myAssessmentClickListener;


    public assesmentAdapter(Context context, ArrayList<Assessment> assesment, assessmentClickListener clickListener){
        this.context = context;
        this.list = assesment;
        this.myAssessmentClickListener = clickListener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final Assessment model = list.get(position);
        String setText = model.getName() + context.getString(R.string.spacer);
        holder.assessmentViewName.setText(setText);
        setText = model.getStartDate() + context.getString(R.string.spacer);
        holder.assessmentViewStart.setText(setText);
        holder.assessmentViewEnd.setText(model.getEndDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //implements onclicklistener for the assesment recyclerview
    //TODO: Find out why this is relevant or delete it.
    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView assessmentViewName;
        public TextView assessmentViewStart;
        public TextView assessmentViewEnd;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.assesmentFragmentView);
            assessmentViewName = itemView.findViewById(R.id.assessment_item_name);
            assessmentViewStart = itemView.findViewById(R.id.assessment_item_start);
            assessmentViewEnd = itemView.findViewById(R.id.assessment_item_end);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int position = getAdapterPosition();
            myAssessmentClickListener.onListItemClick(position);
        }
    }
    public interface assessmentClickListener{
        void onListItemClick(int position);
    }
}