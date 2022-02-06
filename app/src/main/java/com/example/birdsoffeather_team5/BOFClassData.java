package com.example.birdsoffeather_team5;

public class BOFClassData implements ClassData{
    private int year;
    private Session session;
    private String subject;
    private String courseNum;

    //TODO: Create Constructor

    public int getYear() {return year;}
    public Session getSession() {return session;}
    public String getSubject() {return subject;}
    public String getCourseNum() {return courseNum;}

    public void setYear(int y) {year = y;}
    public void setSession(Session s) {session = s;}
    public void setSubject(String s) {subject = s;}
    public void setCourseNum(String c) {courseNum = c;}

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
        && this.getSession() == other.getSession() && this.getSubject().equals(other.getSubject())
        && this.getCourseNum().equals(other.getCourseNum());
    }
}
