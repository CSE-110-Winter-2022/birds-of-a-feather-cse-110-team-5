package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileViewActivity extends AppCompatActivity {

    private RecyclerView BOFClassRecyclerView;
    private LinearLayoutManager BOFClassLayoutManager;
    private BOFClassDataAdapter classDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ProfileViewActivity", "ProfileViewActivity created");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        SharedPreferences pref = getSharedPreferences("sharedClasses", MODE_PRIVATE);
        String studentName = getIntent().getStringExtra("student_name");

        String otherClassesString = pref.getString(studentName, "error_other_student_not_found");
        List<ClassData> otherStudentClasses = BOFStudent.decodeClassData(otherClassesString);

        SharedPreferences pref2 = getSharedPreferences("mainStudent", MODE_PRIVATE);
        String userName = pref2.getString("name","error_user_name_not_found");

        String userClassesString = pref.getString(userName, "error_main_student_not_found");
        List<ClassData> userStudentClasses = BOFStudent.decodeClassData(userClassesString);

        List<ClassData> sc = BOFSharedClasses.findSharedClasses(userStudentClasses, otherStudentClasses);

        //change sharedclass data to be a list of BOFclassdata instead of list of classdata
        List<BOFClassData> bcd = new ArrayList<BOFClassData>();
        for (ClassData cd: sc)
            bcd.add((BOFClassData)cd);

        BOFClassRecyclerView = findViewById(R.id.common_classes);

        BOFClassLayoutManager = new LinearLayoutManager(this);
        BOFClassRecyclerView.setLayoutManager(BOFClassLayoutManager);

        classDataAdapter = new BOFClassDataAdapter(bcd);
        BOFClassRecyclerView.setAdapter(classDataAdapter);

        TextView nt = findViewById(R.id.name_text);
        nt.setText(studentName);
    }

    public void onGoBackClicked(View view) {
        Log.i("ProfileViewActivity", "ProfileViewActivity closing");
        finish();
    }
}