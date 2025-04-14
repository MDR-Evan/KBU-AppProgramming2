package com.example.xml;

public class Student {
    private String hakbun;
    private String name;
    private String grade;

    public Student(String hakbun, String name, String grade) {
        this.hakbun = hakbun;
        this.name = name;
        this.grade = grade;
    }

    public String getHakbun() {
        return hakbun;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }
}
