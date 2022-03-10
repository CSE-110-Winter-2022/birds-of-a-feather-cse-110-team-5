package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.widget.Toast;

public class ProfileViewActivity extends AppCompatActivity {

    private RecyclerView BOFClassRecyclerView;
    private LinearLayoutManager BOFClassLayoutManager;
    private BOFClassDataAdapter classDataAdapter;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ProfileViewActivity", "ProfileViewActivity created");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        /*
        SharedPreferences pref = getSharedPreferences("sharedClasses", MODE_PRIVATE);
        String studentName = getIntent().getStringExtra("student_name");

        String otherClassesString = pref.getString(studentName, "error_other_student_not_found");
        List<ClassData> otherStudentClasses = BOFStudent.decodeClassData(otherClassesString);

        SharedPreferences pref2 = getSharedPreferences("mainStudent", MODE_PRIVATE);
        String userName = pref2.getString("name","error_user_name_not_found");

        String userClassesString = pref.getString(userName, "error_main_student_not_found");
        List<ClassData> userStudentClasses = BOFStudent.decodeClassData(userClassesString);
         */

        String sharedJson = getIntent().getStringExtra("student_name");
        Log.i("ProfileViewActivity", "Getting this Json: " + sharedJson);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ClassData.class, new Deserializers.ClassDataDeserializer())
                .registerTypeAdapter(Student.class, new Deserializers.StudentDeserializer())
                .create();
        SharedClasses shared = gson.fromJson(sharedJson, BOFSharedClasses.class);
        Log.i("ProfileViewActivity", "What Gson did: " + shared.getOtherStudent().getName());

        student = shared.getOtherStudent();


        List<ClassData> sc = shared.getSharedClasses();


        List<BOFClassData> bcd = new ArrayList<BOFClassData>();
        for (ClassData cd: sc)
            bcd.add((BOFClassData)cd);

        BOFClassRecyclerView = findViewById(R.id.common_classes);

        BOFClassLayoutManager = new LinearLayoutManager(this);
        BOFClassRecyclerView.setLayoutManager(BOFClassLayoutManager);

        classDataAdapter = new BOFClassDataAdapter(bcd);
        BOFClassRecyclerView.setAdapter(classDataAdapter);

        TextView nt = findViewById(R.id.name_text);
        nt.setText(shared.getOtherStudent().getName());
    }

    public void onGoBackClicked(View view) {
        Log.i("ProfileViewActivity", "ProfileViewActivity closing");
        finish();
    }

    public void onWaveClicked(View view) {
        Log.i("ProfileViewActivity", "Waved to " + student.getName());

        //toast notification
        Toast.makeText(view.getContext(), "Sent Wave", Toast.LENGTH_SHORT).show();

        //determine the main student from shared preferences
        SharedPreferences mainStudentPref = getSharedPreferences("mainStudent", MODE_PRIVATE);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ClassData.class, new Deserializers.ClassDataDeserializer())
                .create();
        String mainStudentStr = mainStudentPref.getString("studentObject", "");
        Log.i("ProfileViewActivity", "mainStudentSTR: " + mainStudentStr);
        Student mainStudent = gson.fromJson(mainStudentStr, BOFStudent.class);
        Log.i("ProfileViewActivity", "mainStudent made" + mainStudent.getClassData().toString());

        //add the viewed student's id to the main student's wave list
        mainStudent.getWaves().add(student.getID());
    }
}