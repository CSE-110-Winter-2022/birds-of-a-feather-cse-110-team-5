package com.example.birdsoffeather_team5;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class BOFStudentListAdapter extends RecyclerView.Adapter<BOFStudentListAdapter.ViewHolder> {
    private List<Student> students;
    private List<SharedClasses> sharedClassesList;
    private Student mainStudent;
    private String sortBy;

    public BOFStudentListAdapter(List<Student> students, List<SharedClasses> sharedClassesList, Student mainStudent) {
        super();
        this.students = students;
        this.sharedClassesList = sharedClassesList;
        this.mainStudent = mainStudent;
        this.sortBy = "Default";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.student_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setNewStudentRow(sharedClassesList.get(position));
    }

    @Override
    public int getItemCount() {
        return sharedClassesList.size();
    }

    public List<Student> getBOFStudentList(){
        return students;
    }
    /*
    public void addNewStudent(SharedClasses sh) {
        if(students.contains(sh.getOtherStudent())) {
            return;
        }
        students.add(sh.getOtherStudent());
        sharedClassesList.add(sh);
        Collections.sort(sharedClassesList);
        Collections.reverse(sharedClassesList);
        this.notifyItemInserted(sharedClassesList.indexOf(sh));
    }
    */

    public void setSort(String sort) {
        this.sortBy = sort;
        switch(sort) {
            case "Default":
                Collections.sort(sharedClassesList);
                Collections.reverse(sharedClassesList);
                notifyDataSetChanged();
                break;
            case "SortBySmall":
                sharedClassesList = SortSmallClasses.sortBySmall(students, mainStudent);
                notifyDataSetChanged();
                break;
            case "SortByRecent":
                sharedClassesList = SortRecentClasses.sortByRecent(students, mainStudent);
                notifyDataSetChanged();
                break;
        }
    }

    public void addNewStudent(SharedClasses sh) {
        if(students.contains(sh.getOtherStudent())) {
            return;
        }
        students.add(sh.getOtherStudent());
        switch(sortBy) {
            case "Default":
                Collections.sort(sharedClassesList);
                Collections.reverse(sharedClassesList);
                break;
            case "SortBySmall":
                sharedClassesList = SortSmallClasses.sortBySmall(students, mainStudent);
                break;
            case "SortByRecent":
                sharedClassesList = SortRecentClasses.sortByRecent(students, mainStudent);
                break;
        }
        this.notifyItemInserted(sharedClassesList.indexOf(sh));
    }


    public void addStudent(SharedClasses sh) {

        students.add(sh.getOtherStudent());
        sharedClassesList.add(sh);
        this.notifyItemInserted(sharedClassesList.size()-1);
    }

    public void clear(){
        students.clear();
        sharedClassesList.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView pic;
        private final TextView name;
        private final TextView numShared;
        private SharedClasses sharedClasses;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pic = itemView.findViewById(R.id.imageView);
            this.name = itemView.findViewById(R.id.other_name);
            this.numShared = itemView.findViewById(R.id.num_class);
            itemView.setOnClickListener(this);
        }

        public void setNewStudentRow(SharedClasses sh){
            this.name.setText(sh.getOtherStudent().getName());
            this.numShared.setText("" + sh.getSharedClasses().size());
            sharedClasses = sh;
            Glide.with(itemView).load(sh.getOtherStudent().getURL()).error(R.drawable.ic_launcher_background).into(pic);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(itemView.getContext(), ProfileViewActivity.class);
            intent.putExtra("student_name", sharedClasses.getOtherStudent().getName());
            itemView.getContext().startActivity(intent);
        }
    }
}
