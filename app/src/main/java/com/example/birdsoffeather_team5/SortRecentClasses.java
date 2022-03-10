
package com.example.birdsoffeather_team5;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SortRecentClasses {
    public static ArrayList<SharedClasses> sortByRecent(List<Student> userProfiles, Student mainStudent) {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int sessionVal = -1; // "FA" = 0, "WI" = 1, "SP" = 2, all summer sessions = 3

        switch (month) {
            case 1: sessionVal = 1;
            case 2: sessionVal = 1;
            case 3: sessionVal = 1;
            case 4: sessionVal = 2;
            case 5: sessionVal = 2;
            case 6: sessionVal = 2;
            case 7: sessionVal = 3;
            case 8: sessionVal = 3;
            case 9: sessionVal = 3;
            case 10: sessionVal = 0;
            case 11: sessionVal = 0;
            case 12: sessionVal = 0;
        }

        ArrayList<Student> sortedStudents = new ArrayList<>();
        HashMap<Integer, Pair<Integer, String>> count = new HashMap<>();

        for(int i = 0; i < userProfiles.size(); i++) {
            BOFSharedClasses temp = new BOFSharedClasses(mainStudent, userProfiles.get(i));
            List<ClassData> sharedClasses = temp.getSharedClasses();
            int score = 0;
            int age = 0;
            for(ClassData classData : sharedClasses){

                if (classData.getYear() == 2021 && classData.getSession().equals("FA")){
                    age = 0;
                } else if (classData.getYear() == 2021 && (classData.getSession().equals("SS1") || classData.getSession().equals("SS2") || classData.getSession().equals("SSS"))){
                    age = 1;
                } else if (classData.getYear() == 2021 && classData.getSession().equals("SP")){
                    age = 2;
                }  else if (classData.getYear() == 2021 && classData.getSession().equals("WI")){
                    age = 3;
                } else {
                    age = 4;
                }

                if (age == 0){
                    score += 5;
                } else if (age == 1){
                    score += 4;
                } else if (age == 2){
                    score += 3;
                } else if (age == 3){
                    score += 2;
                } else if (age >= 4){
                    score += 1;
                }
            }
            Log.i("Calculating total scores based on ages", userProfiles.get(i).getName() + score);
            Pair<Integer, String> input = new Pair<>(sharedClasses.size(), String.valueOf(score));
            count.put(i, input);
        }

        ArrayList<Pair<Integer, String>> sortHelper = new ArrayList<>();
        for(int i = 0; i < userProfiles.size(); i++){
            sortedStudents.add(userProfiles.get(i));
            sortHelper.add(count.get(i));
        }

        for(int i = 0; i < sortedStudents.size(); i++){
            for(int j = 0; j < sortedStudents.size() - 1; j++){
                if(Double.parseDouble(sortHelper.get(j).second) == Double.parseDouble(sortHelper.get(j+1).second)){
                    if(sortHelper.get(j).first.intValue() < sortHelper.get(j+1).first.intValue()){
                        Student studentTemp = sortedStudents.get(j);
                        Pair<Integer, String> weightTemp = sortHelper.get(j);
                        sortedStudents.set(j, sortedStudents.get(j+1));
                        sortedStudents.set(j+1, studentTemp);
                        sortHelper.set(j, sortHelper.get(j+1));
                        sortHelper.set(j+1, weightTemp);
                    }
                }
                else if(Double.parseDouble(sortHelper.get(j).second) > Double.parseDouble(sortHelper.get(j+1).second)){
                    Student studentTemp = sortedStudents.get(j);
                    Pair<Integer, String> weightTemp = sortHelper.get(j);
                    sortedStudents.set(j, sortedStudents.get(j+1));
                    sortedStudents.set(j+1, studentTemp);
                    sortHelper.set(j, sortHelper.get(j+1));
                    sortHelper.set(j+1, weightTemp);
                }
            }
        }


        ArrayList<SharedClasses> sharedClasses = new ArrayList<>();
        for (Student student: sortedStudents){
            SharedClasses temp = new BOFSharedClasses(mainStudent, student);
            sharedClasses.add(temp);
        }
        Collections.reverse(sharedClasses);
        return sharedClasses;
    }
}