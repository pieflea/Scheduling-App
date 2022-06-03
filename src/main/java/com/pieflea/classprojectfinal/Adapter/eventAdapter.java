package com.pieflea.classprojectfinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pieflea.classprojectfinal.Database.DataHolder;
import com.pieflea.classprojectfinal.Models.Event;
import com.pieflea.classprojectfinal.Models.Term;
import com.pieflea.classprojectfinal.R;

import java.util.ArrayList;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.myViewHolder> {

    Context context;

    public eventAdapter(Context context, ArrayList<Event> events){
        this.context = context;
        DataHolder.events = events;
    }

    @NonNull
    @Override
    public eventAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item_layout, parent, false);
        return new eventAdapter.myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull eventAdapter.myViewHolder holder, int position) {
        final Event model = DataHolder.events.get(position);
        String setText = model.getDescription();
        holder.termName.setText(setText);
        setText = model.getUser();
        holder.user.setText(setText);
        holder.eventTime.setText(model.getNow().toString());
    }

    @Override
    public int getItemCount() {
        return DataHolder.events.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView termName;
        public TextView eventTime;
        public TextView user;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.termRecyclerView);
            termName = itemView.findViewById(R.id.course_item_name);
            eventTime = itemView.findViewById(R.id.course_item_end);
            user = itemView.findViewById(R.id.course_item_start);
        }
    }
}
