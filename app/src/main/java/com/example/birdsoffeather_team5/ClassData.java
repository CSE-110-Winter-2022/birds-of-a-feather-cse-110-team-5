package com.example.birdsoffeather_team5;

public interface ClassData{
    public int getYear();
    public Session getSession();
    public String getSubject();
    public String getCourseNum();

    public void setYear(int y);
    public void setSession(Session s);
    public void setSubject(String s);
    public void setCourseNum(String c);

    /**
     * Determines if two ClassData are the same (each variable is the same)
     * @param c1 first ClassData to compare
     * @param c2 second ClassData to compare
     * @return true if all variables are the same, false otherwise
     */
    public boolean equals(ClassData c1, ClassData c2);
}
