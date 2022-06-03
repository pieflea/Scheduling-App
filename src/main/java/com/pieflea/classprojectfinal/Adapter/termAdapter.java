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
import com.pieflea.classprojectfinal.Models.Term;
import com.pieflea.classprojectfinal.R;

import java.util.ArrayList;

//Adapter implemented for recycler view to show a list of terms
public class termAdapter extends RecyclerView.Adapter<termAdapter.myViewHolder> {

    Context context;
    final public termAdapter.termClickListener myTermClickListener;


    public termAdapter (Context context, ArrayList<Term> Term, termClickListener clickListener){
        this.context = context;
        DataHolder.terms = Term;
        this.myTermClickListener = clickListener;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item_layout, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final Term model = DataHolder.terms.get(position);
        String setText = model.getName() + context.getString(R.string.spacer);
        holder.termName.setText(setText);
        setText = model.getStartDate() + context.getString(R.string.spacer);
        holder.termStart.setText(setText);
        holder.termEnd.setText(model.getEndDate());
    }

    @Override
    public int getItemCount() {
        return DataHolder.terms.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView termName;
        public TextView termStart;
        public TextView termEnd;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.termRecyclerView);
            termName = itemView.findViewById(R.id.course_item_name);
            termStart = itemView.findViewById(R.id.course_item_start);
            termEnd = itemView.findViewById(R.id.course_item_end);
            itemView.setOnClickListener(this);
        }

        //this passes an onclick method through an interface to (place where it goes to)
        @Override
        public void onClick(View v){
            int position = getAdapterPosition();
            myTermClickListener.onListItemClick(position);
        }
    }
    public interface termClickListener{
        void onListItemClick(int position);
    }
}
