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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private RecyclerView BOFStudentRecyclerView;
    private LinearLayoutManager BOFStudentLayoutManager;
    private BOFStudentListAdapter studentListAdapter;
    private MessageListener messageListener;
    private Student mainStudent;
    private String mainStudentStr;
    private Gson gson;

    private String[] items = new String[]{"Default", "By Most Recent", "By Small Class Size", "Clear"};


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

        studentListAdapter = new BOFStudentListAdapter(students, sharedClassesList, mainStudent);
        BOFStudentRecyclerView.setAdapter(studentListAdapter);


        // the code below is for spinner sorting options
        Spinner dropdown = findViewById(R.id.sorting_option);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                Toast.makeText(getApplicationContext(),
                        items[0],
                        Toast.LENGTH_LONG)
                        .show();
                // do nothing for now

                studentListAdapter.setSort("Default");
                break;

            case 1:
                Toast.makeText(getApplicationContext(),
                        items[1],
                        Toast.LENGTH_LONG)
                        .show();


                studentListAdapter.setSort("SortByRecent");

                /*
                List<Student> studentLists = studentListAdapter.getBOFStudentList();
                List<Student> tempStudentList = new ArrayList<Student>();
                for(Student student: studentLists){
                    tempStudentList.add(student);
                }
                studentListAdapter.clear();

                studentListAdapter.notifyDataSetChanged();
                //update studentListAdapter for sorting by Prioritize Recent
                //get a list of BOFSharedClasses from sorting algorithm
                List<SharedClasses> sharedClassesList = SortRecentClasses.sortByRecent(tempStudentList, mainStudent);
                for (SharedClasses sharedClasses : sharedClassesList) {
                    Log.i("shared classes", sharedClasses.toString());
                    studentListAdapter.addStudent(sharedClasses);
                }

                */

                break;


            case 2:
                Toast.makeText(getApplicationContext(),
                        items[2],
                        Toast.LENGTH_LONG)
                        .show();

                studentListAdapter.setSort("SortBySmall");
                /*

                List<Student> studentListsForSmall = studentListAdapter.getBOFStudentList();

                List<Student> tempStudentListForSmall = new ArrayList<Student>();
                for(Student student: studentListsForSmall){
                    tempStudentListForSmall.add(student);
                }

                Log.i("studentList works", studentListsForSmall.toString());

                Log.i("tempStudentList", tempStudentListForSmall.toString());

                Log.i("cleared", "Student List Adapter is cleared");
                studentListAdapter.clear();


                Log.i("studentList works", studentListsForSmall.toString());

                Log.i("tempStudentList", tempStudentListForSmall.toString());

                Log.i("notifyDataSetChanged", "notifyDataSetChanged is working");
                studentListAdapter.notifyDataSetChanged();
                //update studentListAdapter for sorting by Prioritize Small Classes

                List<SharedClasses> sharedClassesListBySmall = SortSmallClasses.sortBySmall(tempStudentListForSmall, mainStudent);
                Log.i("shared classes", sharedClassesListBySmall.toString());

                for (SharedClasses sharedClasses: sharedClassesListBySmall) {
                    Log.i("shared classes", sharedClasses.toString());
                    studentListAdapter.addStudent(sharedClasses);
                }

                 */
                break;

            case 3:
                Toast.makeText(getApplicationContext(),
                        items[3],
                        Toast.LENGTH_LONG)
                        .show();
                studentListAdapter.clear();
                studentListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}