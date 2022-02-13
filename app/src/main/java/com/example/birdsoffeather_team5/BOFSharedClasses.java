package com.example.birdsoffeather_team5;

import java.util.List;
import java.util.ArrayList;

public class BOFSharedClasses implements SharedClasses{
    private Student mainStudent;
    private Student otherStudent;
    private List<ClassData> sharedClasses;

    public BOFSharedClasses(Student mainStudent, Student otherStudent) {
        this.mainStudent = mainStudent;
        this.otherStudent = otherStudent;

        sharedClasses = findSharedClasses(mainStudent, otherStudent);
    }

    public static List<ClassData> findSharedClasses(Student s1, Student s2) {
        List<ClassData> shared = new ArrayList<>(s1.getClassData());

        shared.retainAll(s2.getClassData());
        return shared;
    }

    @Override
    public List<ClassData> getSharedClasses() {
        return sharedClasses;
    }

    public Student getMainStudent() {
        return mainStudent;
    }
    public Student getOtherStudent() {
        return otherStudent;
    }
}
