package com.example.birdsoffeather_team5;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.View;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void onLaunchRunQueryClicked(View view) {
        SharedPreferences mainStudent = getSharedPreferences("mainStudent", MODE_PRIVATE);
        if(mainStudent.getString("name","").equals("")){
            Intent intent = new Intent(HomeScreenActivity.this, UsernameActivity.class);
            HomeScreenActivity.this.startActivity(intent);
        }
        else{
            Intent intent = new Intent(HomeScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}