package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ClassInputActivity extends AppCompatActivity {

    private RecyclerView BOFClassRecyclerView;
    private LinearLayoutManager BOFClassLayoutManager;
    private BOFClassDataAdapter classDataAdapter;
    private List<ClassData> main_user_classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ClassInputActivity", "ClassInputActivity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_input);
        Spinner session_spinner = (Spinner) findViewById(R.id.session_spinner);

        main_user_classes = new ArrayList<>();
        //session_spinner.setOnItemSelectedListener(this);

        ArrayList<String> categories = new ArrayList<String>();
        categories.add("FA");
        categories.add("WI");
        categories.add("SP");
        categories.add("SS1");
        categories.add("SS2");
        categories.add("SSS");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        session_spinner.setAdapter(dataAdapter);


        List<BOFClassData> classes = new ArrayList<BOFClassData>();
        setTitle("Classes");

        BOFClassRecyclerView = findViewById(R.id.class_recyclerview);

        BOFClassLayoutManager = new LinearLayoutManager(this);
        BOFClassRecyclerView.setLayoutManager(BOFClassLayoutManager);


        classDataAdapter = new BOFClassDataAdapter(classes);
        BOFClassRecyclerView.setAdapter(classDataAdapter);
    }

    // returns true if all parameters are valid for class data
    private boolean isValidClass(int year, String session, String subject, String courseNum){
        return isValidYear(year) &&
                isValidSession(session) &&
                isValidSubject(subject) &&
                isValidCourseNum(courseNum);
    }

    // returns true if year is valid (any int from 1900 to 2100)
    private boolean isValidYear(int year){
        return (year >= 1900) &&
                (year <= 2100);
    }

    // returns true if session is valid (session is chosen from a dropdown, so any non-null value is valid)
    private boolean isValidSession(String session){
        return session != null;
    }

    // returns true if subject is valid (subjects are 2-4 capital letters)
    private boolean isValidSubject(String subject){
        return subject != null &&
                Pattern.matches("[A-Z]{2,4}", subject);
    }

    // returns true if course number is valid (course nums are 1-3 digits and 0-3 capital letters)
    private boolean isValidCourseNum(String courseNum){
        return courseNum != null &&
                Pattern.matches("\\d{1,3}[A-Z]{0,3}", courseNum);
    }

    public void onEnterButtonClicked(View view) {
        //get the year
        TextView yearView = findViewById(R.id.year_input);
        String yearText = yearView.getText().toString();
        Optional<Integer> maybeYear = parseCount(yearText);
        if(!maybeYear.isPresent()) {
            //add alert
            return;
        }
        int year = Integer.parseInt(yearText);
        if(!isValidYear(year)) {
            //add alert
            return;
        }

        //get session?
        Spinner sessionView = findViewById(R.id.session_spinner);
        String session = sessionView.getSelectedItem().toString();
        if(!isValidSession(session)) {
            //alert
            return;
        }

        //get subject
        TextView subjectView = findViewById(R.id.subject_input);
        String subject = subjectView.getText().toString();
        if(!isValidSubject(subject)) {
            //alert
            return;
        }

        //get course number
        TextView cnView = findViewById(R.id.course_number_input);
        String courseNum = cnView.getText().toString();
        if(!isValidCourseNum(courseNum)) {
            //alert
            return;
        }

        BOFClassData newClassData = new BOFClassData(year, session, subject, courseNum,"todo");

        //need to use ClassDataAdapter add method
        classDataAdapter.addClass(newClassData);
        main_user_classes.add(newClassData);
    }

    //add to a utilities class
    public static Optional<Integer> parseCount(String str) {
        try {
            int maxCount = Integer.parseInt(str);
            return Optional.of(maxCount);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public void onDoneButtonClicked(View view) {
        if(main_user_classes.size() <= 0) {
            return;
        }
        SharedPreferences mainStudent = getSharedPreferences("mainStudent", MODE_PRIVATE);
        SharedPreferences.Editor edit = mainStudent.edit();
        Student temp = new BOFStudent("temp", "temp", main_user_classes);
        //String mainUserClassString = temp.convertClassData();
        Gson gson = new Gson();
        String mainUser = gson.toJson(temp);
        edit.putString("studentObject", mainUser);
        edit.apply();
        Log.i("ClassInputActivity", mainUser);
        Intent intent = new Intent(ClassInputActivity.this, MainActivity.class);
        ClassInputActivity.this.startActivity(intent);
    }
}