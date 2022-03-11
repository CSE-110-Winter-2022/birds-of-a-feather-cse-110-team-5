package com.example.birdsoffeather_team5;

import java.util.List;
import java.util.ArrayList;

public class BOFSharedClasses extends SharedClasses{
    private Student mainStudent;
    private Student otherStudent;
    private List<ClassData> sharedClasses;
    private boolean isOtherWaving;

    public BOFSharedClasses(Student mainStudent, Student otherStudent) {
        this.mainStudent = mainStudent;
        this.otherStudent = otherStudent;

        sharedClasses = findSharedClasses(mainStudent, otherStudent);
        isOtherWaving = otherStudent.getWaves().contains(mainStudent.getID());
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

    public Student getOtherStudent() {
        return otherStudent;
    }

    public boolean isOtherWaving() {return isOtherWaving;}


    @Override
    public int compareTo(SharedClasses sharedClasses) {
        return this.getSharedClasses().size() - sharedClasses.getSharedClasses().size();
    }


    public static List<ClassData> findSharedClasses(List<ClassData> c1, List<ClassData> c2)
    {
        List<ClassData> shared = new ArrayList<>(c1);

        shared.retainAll(c2);
        return shared;
    }
}
