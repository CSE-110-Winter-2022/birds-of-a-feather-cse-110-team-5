package com.example.birdsoffeather_team5;

import junit.framework.TestCase;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class BOFSharedClassesTest extends TestCase {
    ClassData c1;
    ClassData c1Copy;
    ClassData c2;
    ClassData c3;

    @Before
    public void setUp() {
        c1 = new BOFClassData(2022, "FA", "CSE", "110","Large");
        c1Copy = new BOFClassData(2022, "FA", "CSE", "110","Large");

        c2 = new BOFClassData(2020, "SP", "POLI", "28","Small");
        c3 = new BOFClassData(2021, "WI", "CSE", "120","Large");
    }

    public void testFindOneSharedClassDiffCopy() {
        List<ClassData> expectedShared = new ArrayList<ClassData>();
        expectedShared.add(c1Copy);

        List<ClassData> student1List = new ArrayList<>(); student1List.add(c1);
        List<ClassData> student2List = new ArrayList<>(); student2List.add(c1Copy);
        Student student1 = new BOFStudent("temp1", "temp1", student1List);
        Student student2 = new BOFStudent("temp2", "temp2", student2List);

        List<ClassData> actualShared = BOFSharedClasses.findSharedClasses(student1, student2);

        assertEquals(expectedShared, actualShared);
    }

    public void testFindOneSharedClassSameCopy() {
        List<ClassData> expectedShared = new ArrayList<ClassData>();
        expectedShared.add(c1Copy);

        List<ClassData> student1List = new ArrayList<>(); student1List.add(c1);
        List<ClassData> student2List = new ArrayList<>(); student2List.add(c1);
        Student student1 = new BOFStudent("temp1", "temp1", student1List);
        Student student2 = new BOFStudent("temp2", "temp2", student2List);

        List<ClassData> actualShared = BOFSharedClasses.findSharedClasses(student1, student2);

        assertEquals(expectedShared, actualShared);
    }

    public void testFindSharedClassesAll() {
        List<ClassData> expectedShared = new ArrayList<ClassData>();
        expectedShared.add(c1); expectedShared.add(c2);

        List<ClassData> student1List = new ArrayList<>();
        student1List.add(c1); student1List.add(c2);
        List<ClassData> student2List = new ArrayList<>();
        student2List.add(c1); student2List.add(c2);

        Student student1 = new BOFStudent("temp1", "temp1", student1List);
        Student student2 = new BOFStudent("temp2", "temp2", student2List);

        List<ClassData> actualShared = BOFSharedClasses.findSharedClasses(student1, student2);

        assertEquals(expectedShared, actualShared);
    }

    public void testFindSharedClassesNone() {
        List<ClassData> expectedShared = new ArrayList<ClassData>();

        List<ClassData> student1List = new ArrayList<>(); student1List.add(c1);
        List<ClassData> student2List = new ArrayList<>(); student2List.add(c2);

        Student student1 = new BOFStudent("temp1", "temp1", student1List);
        Student student2 = new BOFStudent("temp2", "temp2", student2List);

        List<ClassData> actualShared = BOFSharedClasses.findSharedClasses(student1, student2);

        assertEquals(expectedShared, actualShared);
    }

    public void testFindSharedClassFromMultiple() {
        List<ClassData> expectedShared = new ArrayList<ClassData>();
        expectedShared.add(c2);

        List<ClassData> student1List = new ArrayList<>();
        student1List.add(c1); student1List.add(c2);
        List<ClassData> student2List = new ArrayList<>();
        student2List.add(c2); student2List.add(c3);

        Student student1 = new BOFStudent("temp1", "temp1", student1List);
        Student student2 = new BOFStudent("temp2", "temp2", student2List);

        List<ClassData> actualShared = BOFSharedClasses.findSharedClasses(student1, student2);

        assertEquals(expectedShared, actualShared);
    }
}
