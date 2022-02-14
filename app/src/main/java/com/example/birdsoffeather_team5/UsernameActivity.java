package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UsernameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
    }

    public void onNextButtonClicked(View view) {
        TextView textView = findViewById(R.id.editTextTextPersonName);
        SharedPreferences mainStudent = getSharedPreferences("mainStudent", MODE_PRIVATE);
        SharedPreferences.Editor edit = mainStudent.edit();
        edit.putString(textView.getText().toString(),"");
        edit.putString("check", "done");
        edit.apply();
        Intent intent = new Intent(UsernameActivity.this, ImageURLActivity.class);
        UsernameActivity.this.startActivity(intent);
    }
}