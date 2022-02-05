package com.example.birdsoffeather_team5;

import java.util.List;
import java.util.ArrayList;

public class BOFStudent implements Student{
    private String name;
    private String url;
    private List<ClassData> classData;

    /**
     * Default constructor
     */
    public BOFStudent() {

    }

    public String getName() {return name;}
    public String getURL() {return url;}
    public List<ClassData> getClassData() {return classData;}

    public void setName(String n) {name = n;}
    public void setURL(String u) {url = u;}
    public void setClassData(List<ClassData> c) {classData = c;}
}
