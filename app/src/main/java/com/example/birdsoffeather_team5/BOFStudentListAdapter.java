package com.example.birdsoffeather_team5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class BOFStudentListAdapter extends RecyclerView.Adapter<BOFStudentListAdapter.ViewHolder> {
    private final List<Student> students;
    private final List<SharedClasses> sharedClassesList;

    public BOFStudentListAdapter(List<Student> students, List<SharedClasses> sharedClassesList) {
        super();
        this.students = students;
        this.sharedClassesList = sharedClassesList;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView pic;
        private final TextView name;
        private final TextView numShared;
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
            Glide.with(itemView).load(sh.getOtherStudent().getURL()).error(R.drawable.ic_launcher_background).into(pic);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
