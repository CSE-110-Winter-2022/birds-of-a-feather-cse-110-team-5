package com.example.birdsoffeather_team5;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private RecyclerView BOFStudentRecyclerView;
    private LinearLayoutManager BOFStudentLayoutManager;
    private BOFStudentListAdapter studentListAdapter;

    private String[] items = new String[]{"Default", "By Most Recent", "By Small Class Size", "Clear"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity", "MainActivity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences mainStudent = getSharedPreferences("mainStudent", MODE_PRIVATE);
        SharedPreferences pref = getSharedPreferences("sharedClasses", MODE_PRIVATE);

        //call other activities before this one
        List<Student> students = new ArrayList<>();
        List<SharedClasses> sharedClassesList = new ArrayList<>();

        BOFStudentRecyclerView = findViewById(R.id.student_list);
        BOFStudentLayoutManager = new LinearLayoutManager(this);

        BOFStudentRecyclerView.setLayoutManager(BOFStudentLayoutManager);
        studentListAdapter = new BOFStudentListAdapter(students, sharedClassesList);
        BOFStudentRecyclerView.setAdapter(studentListAdapter);


        // the code below is for spinner sorting options
        Spinner dropdown = findViewById(R.id.sorting_option);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    /*
        Temporarily acting as the bluetooth manager for demo purposes
     */
    public void onEnterButtonClicked(View view) {
        Log.i("MainActivity", "Query started");


        ClassData c1 = new BOFClassData(2022, "FA", "CSE", "110","Large");
        ClassData c2 = new BOFClassData(2020, "SP", "POLI", "28","Small");
        ClassData c3 = new BOFClassData(2021, "WI", "CSE", "120","Large");
        ClassData c4 = new BOFClassData(2022, "WI", "CSE","120","Large");
        ClassData c5 = new BOFClassData(2021, "SS1", "CSE","121","Small");


        List<ClassData> student1List = new ArrayList<>();
        student1List.add(c1); student1List.add(c2); student1List.add(c3); student1List.add(c4); student1List.add(c5);
        List<ClassData> student2List = new ArrayList<>();
        student2List.add(c2);
        List<ClassData> student3List = new ArrayList<>();
        student3List.add(c3);
        List<ClassData> student4List = new ArrayList<>();
        student4List.add(c4);
        List<ClassData> student5List = new ArrayList<>();
        student5List.add(c5);


        Student student1 = new BOFStudent("Main", "Don't Worry", student1List);
        Student student2 = new BOFStudent("student2",
                "https://cdn.discordapp.com/attachments/893362318958805032/941918058665095229/image.png", student2List);
        Student student3 = new BOFStudent("student3", "temp", student3List);
        Student student4 = new BOFStudent("student4", "temp", student4List);
        Student student5 = new BOFStudent("student5", "temp", student5List);

        BOFSharedClasses withS2 = new BOFSharedClasses(student1, student2);
        BOFSharedClasses withS3 = new BOFSharedClasses(student1, student3);
        BOFSharedClasses withS4 = new BOFSharedClasses(student1, student4);
        BOFSharedClasses withS5 = new BOFSharedClasses(student1, student5);



        SharedPreferences pref = getSharedPreferences("sharedClasses", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(student1.getName(), ((BOFStudent)student1).convertClassData());
        edit.putString(student2.getName(), ((BOFStudent)student2).convertClassData());
        edit.putString(student3.getName(), ((BOFStudent)student3).convertClassData());
        edit.putString(student4.getName(), ((BOFStudent)student3).convertClassData());
        edit.putString(student5.getName(), ((BOFStudent)student3).convertClassData());
        edit.apply();

        SharedPreferences pref2 = getSharedPreferences("mainStudent", MODE_PRIVATE);
        edit = pref2.edit();
        edit.putString("name","Main");
        edit.apply();

        studentListAdapter.addNewStudent(withS4);
        studentListAdapter.addNewStudent(withS5);
        studentListAdapter.addNewStudent(withS2);
        studentListAdapter.addNewStudent(withS3);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // code below for testing
        ClassData c1 = new BOFClassData(2022, "FA", "CSE", "110","Large");
        ClassData c2 = new BOFClassData(2020, "SP", "POLI", "28","Small");
        ClassData c3 = new BOFClassData(2021, "WI", "CSE", "120","Large");
        ClassData c4 = new BOFClassData(2022, "WI", "CSE","120","Large");
        ClassData c5 = new BOFClassData(2021, "SS1", "CSE","121","Small");


        List<ClassData> student1List = new ArrayList<>();
        student1List.add(c1); student1List.add(c2); student1List.add(c3); student1List.add(c4); student1List.add(c5);


        Student mainStudent = new BOFStudent("Main", "Don't Worry", student1List);
        // code above for testing

        switch (i){
            case 0:
                Toast.makeText(getApplicationContext(),
                        items[0],
                        Toast.LENGTH_LONG)
                        .show();
                // do nothing for now

                break;

            case 1:
                Toast.makeText(getApplicationContext(),
                        items[1],
                        Toast.LENGTH_LONG)
                        .show();
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

                break;


            case 2:
                Toast.makeText(getApplicationContext(),
                        items[2],
                        Toast.LENGTH_LONG)
                        .show();

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