package com.example.birdsoffeather_team5;

import java.util.List;

public abstract class SharedClasses implements Comparable<SharedClasses>{
    public abstract List<ClassData> getSharedClasses();
    public abstract Student getOtherStudent();
    public abstract Student getMainStudent();
}
