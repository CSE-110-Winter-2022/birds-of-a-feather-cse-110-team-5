package com.example.birdsoffeather_team5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BOFClassDataAdapter extends RecyclerView.Adapter<BOFClassDataAdapter.ViewHolder> {
    private List<BOFClassData> classes;

    public BOFClassDataAdapter(List<BOFClassData> classes){
        super();
        this.classes = classes;
    }


    @NonNull
    @Override
    public BOFClassDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.class_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BOFClassDataAdapter.ViewHolder holder, int position) {
        holder.setClass_row(classes.get(position));
    }

    @Override
    public int getItemCount() {
        return this.classes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView class_row;
        private BOFClassData classData;

        ViewHolder(View itemView){
            super(itemView);
            this.class_row = itemView.findViewById(R.id.class_row);
        }

        public void setClass_row(BOFClassData classData){
            this.classData = classData;
            this.class_row.setText(classData.getCourseNum() + ", " + classData.getSubject() + ", " + /*classData.getSession() */ ", " + classData.getYear());
        }
    }

    public void addClass(BOFClassData classData){
        this.classes.add(classData);
        this.notifyItemInserted(this.classes.size() - 1);
    }
}
