package com.example.birdsoffeather_team5;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void onLaunchRunQueryClicked(View view) {
        Intent intent = new Intent(HomeScreenActivity.this, UsernameActivity.class);
        HomeScreenActivity.this.startActivity(intent);
    }
}