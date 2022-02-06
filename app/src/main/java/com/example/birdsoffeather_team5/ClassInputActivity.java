package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

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
}