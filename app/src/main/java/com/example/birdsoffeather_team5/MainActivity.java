package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView BOFStudentRecyclerView;
    private LinearLayoutManager BOFStudentLayoutManager;
    private BOFStudentListAdapter studentListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity", "MainActivity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences mainStudent = getSharedPreferences("mainStudent", MODE_PRIVATE);
        SharedPreferences pref = getSharedPreferences("sharedClasses", MODE_PRIVATE);
        //call other activities before this one
        List<Student> students = new ArrayList<>();
        List<SharedClasses> sharedClassesList = new ArrayList<>();

        BOFStudentRecyclerView = findViewById(R.id.student_list);
        BOFStudentLayoutManager = new LinearLayoutManager(this);

        BOFStudentRecyclerView.setLayoutManager(BOFStudentLayoutManager);
        studentListAdapter = new BOFStudentListAdapter(students, sharedClassesList);
        BOFStudentRecyclerView.setAdapter(studentListAdapter);
    }

    public void onEnterButtonClicked(View view) {
        Log.i("MainActivity", "Query started");
        ClassData c1 = new BOFClassData(2022, "FA", "CSE", "110");
        ClassData c2 = new BOFClassData(2020, "SP", "POLI", "28");
        ClassData c3 = new BOFClassData(2021, "WI", "CSE", "120");

        List<ClassData> student1List = new ArrayList<>();
        student1List.add(c1); student1List.add(c2);
        List<ClassData> student2List = new ArrayList<>();
        student2List.add(c2); student2List.add(c3);
        List<ClassData> student3List = new ArrayList<>();
        student3List.add(c1); student3List.add(c2); student3List.add(c3);


        Student student1 = new BOFStudent("Main", "Don't Worry", student1List);
        Student student2 = new BOFStudent("John",
                "https://cdn.discordapp.com/attachments/893362318958805032/941918058665095229/image.png", student2List);
        Student student3 = new BOFStudent("Darshan", "temp", student3List);
        BOFSharedClasses withS2 = new BOFSharedClasses(student1, student2);
        BOFSharedClasses withS3 = new BOFSharedClasses(student1, student3);

        SharedPreferences pref = getSharedPreferences("sharedClasses", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(student1.getName(), ((BOFStudent)student1).convertClassData());
        edit.putString(student2.getName(), ((BOFStudent)student2).convertClassData());
        edit.putString(student3.getName(), ((BOFStudent)student3).convertClassData());
        edit.apply();

        SharedPreferences pref2 = getSharedPreferences("mainStudent", MODE_PRIVATE);
        edit = pref2.edit();
        edit.putString("name","Main");
        edit.apply();

        studentListAdapter.addNewStudent(withS2);
        studentListAdapter.addNewStudent(withS3);
    }
}