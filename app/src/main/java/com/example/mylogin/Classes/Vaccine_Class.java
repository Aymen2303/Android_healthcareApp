package com.example.mylogin.Classes;

public class Vaccine_Class {
    private  int id;
    private  String name;
    private  int age;

    public Vaccine_Class(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public  int getId() {
        return id;
    }

    public  String getName() {
        return name;
    }

    public  int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return name; // Return the vaccine name as the string representation
    }

}

