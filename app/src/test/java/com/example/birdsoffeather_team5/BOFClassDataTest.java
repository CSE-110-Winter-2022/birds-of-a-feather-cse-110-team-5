package com.example.birdsoffeather_team5;

import junit.framework.TestCase;

public class BOFClassDataTest extends TestCase {

    public void testGetYear() {
        ClassData classData = new BOFClassData(1, "1", "1", "1","1");
        assertEquals(1, classData.getYear());
    }

    public void testGetSession() {
        ClassData classData = new BOFClassData(1, "1", "1", "1","1");
        assertEquals("1", classData.getSession());
    }

    public void testGetSubject() {
        ClassData classData = new BOFClassData(1, "1", "1", "1","1");
        assertEquals("1", classData.getSubject());
    }

    public void testGetCourseNum() {
        ClassData classData = new BOFClassData(1, "1", "1", "1","1");
        assertEquals("1", classData.getCourseNum());
    }

    public void testSetYear() {
        ClassData classData = new BOFClassData(1, "1", "1", "1","1");
        classData.setYear(2);
        assertEquals(2, classData.getYear());
    }

    public void testSetSession() {
        ClassData classData = new BOFClassData(1, "1", "1", "1","1");
        classData.setSession("2");
        assertEquals("2", classData.getSession());
    }

    public void testSetSubject() {
        ClassData classData = new BOFClassData(1, "1", "1", "1","1");
        classData.setSubject("2");
        assertEquals("2", classData.getSubject());
    }

    public void testSetCourseNum() {
        ClassData classData = new BOFClassData(1, "1", "1", "1","1");
        classData.setCourseNum("2");
        assertEquals("2", classData.getCourseNum());
    }

    public void testEquals() {
        ClassData classData1 = new BOFClassData(1, "1", "1", "1","1");
        ClassData classData2 = new BOFClassData(1, "1", "1", "1","1");
        assertEquals(true, classData1.equals(classData2));
    }

    public void testNotEqual() {
        ClassData classData1 = new BOFClassData(1, "1", "1", "1","1");
        ClassData classData2 = new BOFClassData(2, "2", "2", "2", "2");
        assertFalse(classData1.equals(classData2));
    }
}