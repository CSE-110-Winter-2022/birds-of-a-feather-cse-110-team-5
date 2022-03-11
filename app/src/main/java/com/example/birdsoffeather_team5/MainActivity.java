package com.example.birdsoffeather_team5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "Nearby Function";
    private RecyclerView BOFStudentRecyclerView;
    private LinearLayoutManager BOFStudentLayoutManager;
    private BOFStudentListAdapter studentListAdapter;
    private MessageListener messageListener;
    private Student mainStudent;
    private String mainStudentStr;
    private Gson gson;

    private final String[] items = new String[]{"Default", "By Most Recent", "By Small Class Size"};


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
        Log.d("MainActivity", "mainStudentSTR: " + mainStudentStr);
        mainStudent = gson.fromJson(mainStudentStr, BOFStudent.class);
        Log.d("MainActivity", "mainStudent made" + mainStudent.getClassData().toString());

        //call other activities before this one
        List<Student> students = new ArrayList<>();
        List<SharedClasses> sharedClassesList = new ArrayList<>();

        BOFStudentRecyclerView = findViewById(R.id.student_list);
        BOFStudentLayoutManager = new LinearLayoutManager(this);

        BOFStudentRecyclerView.setLayoutManager(BOFStudentLayoutManager);

        studentListAdapter = new BOFStudentListAdapter(this, students, sharedClassesList, mainStudent);
        BOFStudentRecyclerView.setAdapter(studentListAdapter);


        // the code below is for spinner sorting options
        Spinner dropdown = findViewById(R.id.sorting_option);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        setUpSessionLoader();

        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                Log.d("MainActivity", "we found /a/ message");
                String messageStr = new String(message.getContent());
                Log.d(TAG, "Found message: " + messageStr);
                Student stu = gson.fromJson(messageStr, BOFStudent.class);
                Log.d("MainActivity", "name: " + stu.getName() + " url: " + stu.getURL());
                Log.d("MainActivity", stu.getClassData().toString());
                BOFSharedClasses withStu = new BOFSharedClasses(mainStudent, stu);
                Log.d("MainActivity", "Created Shared with " + withStu.getOtherStudent().getName());
                Log.d("MainActivity", "Sharing: " + withStu.getSharedClasses().toString());
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

        //here
        Message message = new Message(mainStudentStr.getBytes(StandardCharsets.UTF_8));
        Nearby.getMessagesClient(this).unpublish(message);
        Log.d(this.getClass().getSimpleName(), new String(message.getContent()));

        Nearby.getMessagesClient(this).unsubscribe(messageListener);
        Log.d(this.getClass().getSimpleName(), messageListener.toString());
        super.onStop();
    }

    public void onEnterButtonClicked(View view) {
        Button buttonText = findViewById(R.id.start_btn);
        String startStop = buttonText.getText().toString();
        if(startStop.equals("Start")) {
            Log.i("MainActivity", "Query started");

            //here
            Message message = new Message(mainStudentStr.getBytes(StandardCharsets.UTF_8));
            Nearby.getMessagesClient(this).publish(message);
            Log.d(this.getClass().getSimpleName(), new String(message.getContent()));

            Nearby.getMessagesClient(this).subscribe(messageListener);
            Log.d(this.getClass().getSimpleName(), messageListener.toString());
            buttonText.setText("Stop");
            ((Spinner)findViewById(R.id.session_loader)).setVisibility(View.GONE);
            studentListAdapter.clear();

            // This line will bring up the input textbox for sending in a faked other person
            // Just remove this line to stop faking input; Must remove before app put out to public
            Message holder = new Message("mock".getBytes(StandardCharsets.UTF_8));
            messageListener.onFound(holder);
            Log.d("Query started onFound method called", new String(holder.getContent()));

        } else {
            Log.i("MainActivity", "Query stopped");

            //here
            Message message = new Message(mainStudentStr.getBytes(StandardCharsets.UTF_8));
            Nearby.getMessagesClient(this).unpublish(message);
            Log.d(this.getClass().getSimpleName(), new String(message.getContent()));

            Nearby.getMessagesClient(this).unsubscribe(messageListener);
            Log.d(this.getClass().getSimpleName(), messageListener.toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            input.setHint("Enter Session Name...");
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String sessionName = input.getText().toString();
                    SessionSaver.saveEntireSession(getApplicationContext(), sessionName, studentListAdapter.getSharedClassesList());
                    SessionSaver.writeToSessionNames(getApplicationContext(), sessionName);
                    setUpSessionLoader();
                }
            });
            builder.show();

            buttonText.setText("Start");
            setUpSessionLoader();
        }
    }

    private void setUpSessionLoader() {
        Spinner loaderDropdown = findViewById(R.id.session_loader);
        List<String> sessionNames = SessionSaver.retrieveSessionNames(getApplicationContext());
        sessionNames.add("Favorites");
        sessionNames.add(0, "Current Session");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sessionNames);
        loaderDropdown.setAdapter(adapter);
        loaderDropdown.setOnItemSelectedListener(new SessionSelectedListener(studentListAdapter));
        loaderDropdown.setVisibility(View.VISIBLE);
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

                break;


            case 2:
                Toast.makeText(getApplicationContext(),
                        items[2],
                        Toast.LENGTH_LONG)
                        .show();

                studentListAdapter.setSort("SortBySmall");

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

class SessionSelectedListener implements AdapterView.OnItemSelectedListener {
    BOFStudentListAdapter studentListAdapter;

    public SessionSelectedListener(BOFStudentListAdapter studentListAdapter){
        this.studentListAdapter = studentListAdapter;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selected = adapterView.getSelectedItem().toString();

        if(selected.equals("Favorites")) {
            //retrieve favorites list
            List<SharedClasses> sharedClasses = SessionSaver.getFavoriteStudents(adapterView.getContext());
            //load it into the sladapter
            studentListAdapter.loadSession(sharedClasses);
        }
        else if(selected.equals("Current Session")) {
            List<SharedClasses> sharedClasses = SessionSaver.retrieveCurrentSession(adapterView.getContext());
            //load it into the sladapter
            studentListAdapter.loadSession(sharedClasses);
        }
        else {
                //retrieve given session
                List<SharedClasses> sharedClasses = SessionSaver.retrieveSession(adapterView.getContext(), selected);
                //load it into the sladapter
                studentListAdapter.loadSession(sharedClasses);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}