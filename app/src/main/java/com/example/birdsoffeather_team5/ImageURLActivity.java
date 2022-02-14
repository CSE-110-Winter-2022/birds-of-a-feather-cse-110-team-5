package com.example.birdsoffeather_team5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ImageURLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ImageURLActivity", "ImageURLActivity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_urlactivity);
    }

    public void onNextButtonClickedImage(View view) {
        Log.i("ImageURLActivity", "ImageURLActivity entered");
        Intent intent = new Intent(ImageURLActivity.this, ClassInputActivity.class);
        ImageURLActivity.this.startActivity(intent);
    }
}