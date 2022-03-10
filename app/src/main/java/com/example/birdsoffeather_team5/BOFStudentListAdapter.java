package com.example.birdsoffeather_team5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;

public class BOFStudentListAdapter extends RecyclerView.Adapter<BOFStudentListAdapter.ViewHolder> {
    private List<Student> students;
    private List<SharedClasses> sharedClassesList;
    private Student mainStudent;
    private String sortBy;
    private Context context;

    public BOFStudentListAdapter(Context context, List<Student> students, List<SharedClasses> sharedClassesList, Student mainStudent) {
        super();
        this.students = students;
        this.sharedClassesList = sharedClassesList;
        this.mainStudent = mainStudent;
        this.sortBy = "Default";
        this.context = context;
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
        Log.i("BOFStudentListAdapter", "adding new student");
        if(students.contains(sh.getOtherStudent())) {
            Log.i("BOFStudentListAdapter", "new student exists");
            return;
        }
        students.add(sh.getOtherStudent());
        Log.i("BOFStudentListAdapter", "added to sh");
        switch(sortBy) {
            case "Default":
                sharedClassesList.add(sh);
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
        SessionSaver.updateCurrentSession(context, sharedClassesList);
    }

    public List<SharedClasses> getSharedClassesList() {
        return this.sharedClassesList;
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
            itemView.findViewById(R.id.favoriteStar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //((ImageButton)itemView.findViewById(R.id.favoriteStar)).setImageResource(R.drawable.btn_star_big_off);
                    SessionSaver.addFavoriteStudent(view.getContext(), sharedClasses);
                    Toast.makeText(view.getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                }
            });
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
            Gson gson = new Gson();
            String json = gson.toJson(sharedClasses);
            Log.i("BOFStudentListAdapter", "Made json with: " + sharedClasses.getOtherStudent().getName());
            Log.i("BOFStudentListAdapter", "Sharing: " + sharedClasses.getSharedClasses().get(0).getCourseNum());
            Log.i("BOFStudentListAdapter", "json: " + json);

            intent.putExtra("student_name", json);
            itemView.getContext().startActivity(intent);
        }
    }
}
