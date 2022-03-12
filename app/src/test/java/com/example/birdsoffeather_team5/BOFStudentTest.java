package com.example.birdsoffeather_team5;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class BOFStudentTest extends TestCase {
    Student student;
    ClassData classData;
    List<ClassData> classDataList;
    List<String> waves;

    public void setUp() throws Exception {
        super.setUp();
        classData = new BOFClassData(1900, "FA", "AAA", "1", "Small");
        classDataList = new ArrayList<>();
        classDataList.add(classData);
        student = new BOFStudent("name", "url", classDataList, "id");
        waves = new ArrayList<>();
        waves.add("wave");
        student.setWaves(waves);
    }

    public void testTestGetName() {
        assertEquals("name", student.getName());
    }

    public void testGetURL() {
        assertEquals("url", student.getURL());
    }

    public void testGetClassData() {
        ClassData testClassData = new BOFClassData(1900, "FA", "AAA", "1", "Small");
        List<ClassData> testClassDataList = new ArrayList<>();
        testClassDataList.add(testClassData);
        assertEquals(testClassDataList, student.getClassData());
    }

    public void testGetID() {
        assertEquals("id", student.getID());
    }

    public void testGetWaves() {
        List<String> testWaves = new ArrayList<>();
        testWaves.add("wave");
        assertEquals(testWaves, student.getWaves());
    }

    public void testTestSetName() {
        student.setName("name2");
        assertEquals("name2", student.getName());
    }

    public void testSetURL() {
        student.setURL("url2");
        assertEquals("url2", student.getURL());
    }

    public void testSetID() {
        student.setID("id2");
        assertEquals("id2", student.getID());
    }
}