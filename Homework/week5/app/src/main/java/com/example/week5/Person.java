package com.example.week5;

public class Person {
    private String id;
    private String name;
    private String age;

    public Person(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAge() { return age; }
}
