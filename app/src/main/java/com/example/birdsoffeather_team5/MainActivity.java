package com.example.birdsoffeather_team5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Nearby Function";
    private RecyclerView BOFStudentRecyclerView;
    private LinearLayoutManager BOFStudentLayoutManager;
    private BOFStudentListAdapter studentListAdapter;
    private MessageListener messageListener;
    private Student mainStudent;
    private String mainStudentStr;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity", "MainActivity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences mainStudentPref = getSharedPreferences("mainStudent", MODE_PRIVATE);
        gson = new GsonBuilder()
                .registerTypeAdapter(ClassData.class, new Deserializers.ClassDataDeserializer())
                .create();
        mainStudentStr = mainStudentPref.getString("studentObject", "");
        Log.i("MainActivity", "mainStudentSTR: " + mainStudentStr);
        mainStudent = gson.fromJson(mainStudentStr, BOFStudent.class);
        Log.i("MainActivity", "mainStudent made" + mainStudent.getClassData().toString());

        //call other activities before this one
        List<Student> students = new ArrayList<>();
        List<SharedClasses> sharedClassesList = new ArrayList<>();

        BOFStudentRecyclerView = findViewById(R.id.student_list);
        BOFStudentLayoutManager = new LinearLayoutManager(this);

        BOFStudentRecyclerView.setLayoutManager(BOFStudentLayoutManager);
        studentListAdapter = new BOFStudentListAdapter(students, sharedClassesList);
        BOFStudentRecyclerView.setAdapter(studentListAdapter);


        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                Log.i("MainActivity", "we found /a/ message");
                String messageStr = new String(message.getContent());
                Log.d(TAG, "Found message: " + messageStr);
                Student stu = gson.fromJson(messageStr, BOFStudent.class);
                Log.i("MainActivity", "name: " + stu.getName() + " url: " + stu.getURL());
                Log.i("MainActivity", stu.getClassData().toString());
                BOFSharedClasses withStu = new BOFSharedClasses(mainStudent, stu);
                Log.i("MainActivity", "Created Shared with " + withStu.getOtherStudent().getName());
                Log.i("MainActivity", "Sharing: " + withStu.getSharedClasses().toString());
                studentListAdapter.addNewStudent(withStu);
            }

            @Override
            public void onLost(@NonNull Message message) {
                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
            }
        };
        messageListener = new MockMessageListener(realListener, this);
    }

    @Override
    protected void onStop() {
        Nearby.getMessagesClient(this).unpublish(new Message(mainStudentStr.getBytes(StandardCharsets.UTF_8)));
        Nearby.getMessagesClient(this).unsubscribe(messageListener);
        super.onStop();
    }

    public void onEnterButtonClicked(View view) {
        Button buttonText = findViewById(R.id.start_btn);
        String startStop = buttonText.getText().toString();
        if(startStop.equals("Start")) {
            Log.i("MainActivity", "Query started");
            Nearby.getMessagesClient(this).publish(new Message(mainStudentStr.getBytes(StandardCharsets.UTF_8)));
            Nearby.getMessagesClient(this).subscribe(messageListener);
            buttonText.setText("Stop");

            // This line will bring up the input textbox for sending in a faked other person
            // Just remove this line to stop faking input; Must remove before app put out to public
            messageListener.onFound(new Message("mock".getBytes(StandardCharsets.UTF_8)));
        } else {
            Log.i("MainActivity", "Query stopped");
            Nearby.getMessagesClient(this).unpublish(new Message(mainStudentStr.getBytes(StandardCharsets.UTF_8)));
            Nearby.getMessagesClient(this).unsubscribe(messageListener);
            buttonText.setText("Start");
        }
    }
}