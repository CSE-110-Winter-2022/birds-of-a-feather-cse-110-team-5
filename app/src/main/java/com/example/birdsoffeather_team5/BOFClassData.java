package com.example.birdsoffeather_team5;

import android.util.Log;

public class BOFClassData implements ClassData{
    private int year;
    private String session;
    private int sessionNum;
    private String subject;
    private String courseNum;
    private String classSize;

    public BOFClassData(int year, String session, String subject, String courseNum, String classSize){
        this.year = year;
        this.session = session;
        this.subject = subject;
        this.courseNum = courseNum;
        this.classSize = classSize;
    }


    public int getYear() {return year;}
    public String getSession() {return session;}
    public int getSessionNum(){
        switch(session) {
            case "FA":
                return 0;
            case "WI":
                return 1;
            case "SP":
                return 2;
            case "SS1":
                return 3;
            case "SS2":
                return 3;
            case "SSS":
                return 3;
            default:
                Log.i("BOFClassData", "getSessionNum fails");
                return -1;
        }
    }

    public String getSubject() {return subject;}
    public String getCourseNum() {return courseNum;}
    public String getClassSize() {return classSize;}
    public static String[] decode(String classData){
        String[] temp = classData.split(",");
        return temp;
    }

    public void setYear(int y) {year = y;}
    public void setSession(String s) {session = s;}
    public void setSubject(String s) {subject = s;}
    public void setCourseNum(String c) {courseNum = c;}
    public void setClassSize(String c) {classSize = c;}

    /**
     * Overrides Object's equal method to check if this ClassData
     * object has the same class information as the other given
     * ClassData object
     * @param classData other class data  to compare this class data to
     * @return true if given class is the same as this class, false otherwise
     */
    @Override
    public boolean equals(Object classData) {
        if(classData == null) return false;

        if(!(classData instanceof ClassData)) {
            return false;
        }

        final ClassData other = (ClassData) classData;

        return this.getYear() == other.getYear()
        && this.getSession().equals(other.getSession()) && this.getSubject().equals(other.getSubject())
        && this.getCourseNum().equals(other.getCourseNum()) && this.getClassSize().equals(other.getClassSize());
    }
}
