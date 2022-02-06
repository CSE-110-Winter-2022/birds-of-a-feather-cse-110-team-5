package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ClassInputActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_input);

        Spinner session_spinner = (Spinner) findViewById(R.id.session_spinner);

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
    }

    // returns true if all parameters are valid for class data
    private boolean isValidClass(int year, Session session, String subject, String courseNum){
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
    private boolean isValidSession(Session session){
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
}