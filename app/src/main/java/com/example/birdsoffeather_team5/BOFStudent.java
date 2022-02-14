package com.example.birdsoffeather_team5;

import java.util.List;
import java.util.ArrayList;

public class BOFStudent implements Student{
    private String name;
    private String url;
    private List<ClassData> classData;


    public BOFStudent(String name, String url, List<ClassData> classData) {
        this.name = name;
        this.url = url;
        this.classData = classData;
    }

    public String getName() {return name;}
    public String getURL() {return url;}
    public List<ClassData> getClassData() {return classData;}
    public String convertClassData(){
        String answer = "";
        for(ClassData i : this.classData){
            answer = answer.concat(i.getYear() + "," + i.getSession() + "," + i.getSubject() + "," + i.getCourseNum() + ",,");
        }
        return answer;
    }
    public static List<ClassData> decodeClassData(String classData){
        List<ClassData> answer = new ArrayList<>();
        BOFClassData temp = new BOFClassData(-1, "", "", "");
        String[] temp1 = classData.split(",,");
        for(int i = 0; i < temp1.length; i++){
            String[] holder = temp.decode(temp1[i]);
            BOFClassData hold = new BOFClassData(Integer.parseInt(holder[0]), holder[1], holder[2], holder[3]);
            answer.add(hold);
        }
        return answer;
    }

    public void setName(String n) {name = n;}
    public void setURL(String u) {url = u;}
    public void setClassData(List<ClassData> c) {classData = c;}
}
