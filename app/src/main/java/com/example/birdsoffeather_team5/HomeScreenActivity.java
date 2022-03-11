package com.example.birdsoffeather_team5;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("HomeScreenActivity", "HomeScreenActivity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        /*
        SharedPreferences name = getSharedPreferences("mainStudent", MODE_PRIVATE);
        SharedPreferences.Editor edit = name.edit();
        edit.clear().commit();*/
    }

    public void onLaunchRunQueryClicked(View view) {
        Log.i("HomeScreenActivity", "HomeScreenActivity entered");
        SharedPreferences mainStudent = getSharedPreferences("mainStudent", MODE_PRIVATE);
        if(mainStudent.getString("check","").length() == 0){
            Intent intent = new Intent(HomeScreenActivity.this, UsernameActivity.class);
            HomeScreenActivity.this.startActivity(intent);
        }
        else{
            Intent intent = new Intent(HomeScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}