package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        /*SharedPreferences pref = getSharedPreferences("sharedClasses", MODE_PRIVATE);
        String studentName = getIntent().getStringExtra("student_name");

        String classesString = pref.getString(studentName, "error_student_not_found");
        List<ClassData> bcd = BOFStudent.decodeClasses(classesString);*/

        //?? how to get sharedclasses?
        List<ClassData> l1 = new ArrayList<ClassData>();
        List<ClassData> l2 = new ArrayList<ClassData>();
        ClassData c1 = new BOFClassData(2022,"WI","CSE","110");
        l1.add(c1);
        l2.add(c1);
        ClassData c2 = new BOFClassData(2021,"FA","CSE","105");
        l1.add(c2);
        l2.add(c2);
        ClassData c3 = new BOFClassData(2020,"SP","MATH","20A");
        l1.add(c3);
        ClassData c4 = new BOFClassData(2021,"SP","MATH","20A");
        l2.add(c4);

        Student s1 = new BOFStudent("s1","a",l1);
        Student s2 = new BOFStudent("s2","b",l2);

        SharedClasses sc = new BOFSharedClasses(s1,s2);

        //change sharedclass data to be a list of BOFclassdata instead of list of classdata
        List<BOFClassData> bcd = new ArrayList<BOFClassData>();
        for (ClassData cd: sc.getSharedClasses())
            bcd.add((BOFClassData)cd);

        BOFClassRecyclerView = findViewById(R.id.common_classes);

        BOFClassLayoutManager = new LinearLayoutManager(this);
        BOFClassRecyclerView.setLayoutManager(BOFClassLayoutManager);

        classDataAdapter = new BOFClassDataAdapter(bcd);
        BOFClassRecyclerView.setAdapter(classDataAdapter);

        TextView nt = findViewById(R.id.name_text);
        nt.setText(sc.getMainStudent().getName());
    }

    public void onGoBackClicked(View view) {
        finish();
    }
}