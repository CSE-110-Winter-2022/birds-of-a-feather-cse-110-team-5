package com.example.birdsoffeather_team5;

public class BOFClassData implements ClassData{
    private int year;
    private Session session;
    private String subject;
    private String courseNum;

    public int getYear() {return year;}
    public Session getSession() {return session;}
    public String getSubject() {return subject;}
    public String getCourseNum() {return courseNum;}

    public void setYear(int y) {year = y;}
    public void setSession(Session s) {session = s;}
    public void setSubject(String s) {subject = s;}
    public void setCourseNum(String c) {courseNum = c;}

    /**
     * Determines if two ClassData are the same (each variable is the same)
     * @param c1 first ClassData to compare
     * @param c2 second ClassData to compare
     * @return true if all variables are the same, false otherwise
     */
    public boolean equals(ClassData c1, ClassData c2) {
        return c1 != null && c2 != null && c1.getYear() == c2.getYear()
        && c1.getSession() == c2.getSession() && c1.getSubject().equals(c2.getSubject())
        && c1.getCourseNum().equals(c2.getCourseNum());
    }
}
