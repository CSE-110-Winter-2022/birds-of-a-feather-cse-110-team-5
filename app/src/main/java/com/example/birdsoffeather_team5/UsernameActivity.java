package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class UsernameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("UsernameActivity", "UsernameActivity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        Log.d("creating content view","create username screen");
    }

    public void onNextButtonClicked(View view) {
        Log.i("UsernameActivity", "UsernameActivity entered");
        TextView textView = findViewById(R.id.editTextTextPersonName);
        SharedPreferences mainStudent = getSharedPreferences("mainStudent", MODE_PRIVATE);
        SharedPreferences.Editor edit = mainStudent.edit();
        edit.putString("name",textView.getText().toString());
        edit.putString("check", "done");
        edit.apply();
        Intent intent = new Intent(UsernameActivity.this, ImageURLActivity.class);
        Log.d("creating intent","used to start activity");
        UsernameActivity.this.startActivity(intent);
    }
}