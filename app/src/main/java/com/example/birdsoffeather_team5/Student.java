package com.example.birdsoffeather_team5;

import java.util.List;

public interface Student {
    public String getName();
    public String getURL();
    public List<ClassData> getClassData();

    public void setName(String n);
    public void setURL(String u);
    public void setClassData(List<ClassData> c);

}
