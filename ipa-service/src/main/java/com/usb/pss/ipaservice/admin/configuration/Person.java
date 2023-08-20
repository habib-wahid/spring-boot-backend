package com.usb.pss.ipaservice.admin.configuration;

public class Person {
    private String name;

    Person(String name) {
        this.name = name;
    }

    public String getName() {
        System.out.println("Name " + this.name);
        return this.name;
    }
}
