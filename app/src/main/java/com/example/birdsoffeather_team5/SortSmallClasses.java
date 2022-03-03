package com.example.birdsoffeather_team5;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//SortSmallClasses.sortBySmall()

public class SortSmallClasses{
    public static ArrayList<SharedClasses> sortBySmall(List<Student> userProfiles, Student mainStudent){
        ArrayList<Student> sortedStudents = new ArrayList<>();
        HashMap<Integer, Pair<Integer, String>> count = new HashMap<>();
        for(int i = 0; i < userProfiles.size(); i++){
            BOFSharedClasses temp = new BOFSharedClasses(mainStudent, userProfiles.get(i));
            List<ClassData> sharedClasses = temp.getSharedClasses();
            double totalWeight = 0;
            for(int k = 0; k < sharedClasses.size(); k++) {
                Log.i("Checking class size", userProfiles.get(i).getName() + sharedClasses.get(k).getClassSize());
                if(Integer.parseInt(sharedClasses.get(k).getClassSize()) < 40){
                    totalWeight += 1;
                }
                else if(Integer.parseInt(sharedClasses.get(k).getClassSize()) < 75){
                    totalWeight += 0.33;
                }
                else if(Integer.parseInt(sharedClasses.get(k).getClassSize()) < 150){
                    totalWeight += 0.18;
                }
                else if(Integer.parseInt(sharedClasses.get(k).getClassSize()) < 250){
                    totalWeight += 0.10;
                }
                else if(Integer.parseInt(sharedClasses.get(k).getClassSize()) < 400){
                    totalWeight += 0.06;
                }
                else{
                    totalWeight += 0.03;
                }
            }
            Log.i("Checking weights", userProfiles.get(i).getName() + totalWeight);
            Pair<Integer, String> input = new Pair<>(sharedClasses.size(), String.valueOf(totalWeight));
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
        Log.i("Check the size of sortedStudent", String.valueOf(sharedClasses.size()));
        return sharedClasses;
    }
}
