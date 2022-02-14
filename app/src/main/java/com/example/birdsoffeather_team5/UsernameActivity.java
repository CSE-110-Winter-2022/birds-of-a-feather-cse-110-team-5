package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UsernameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
    }

    public void onNextButtonClicked(View view) {
        Intent intent = new Intent(UsernameActivity.this, ImageURLActivity.class);
        UsernameActivity.this.startActivity(intent);
    }
}