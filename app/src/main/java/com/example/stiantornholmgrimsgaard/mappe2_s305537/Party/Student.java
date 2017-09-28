package com.example.stiantornholmgrimsgaard.mappe2_s305537.Party;

/**
 * Created by stiantornholmgrimsgaard on 25.09.2017.
 */

public class Student {

    private Long _id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Student() {

    }

    public Student(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public Student(Long _id, String firstName, String lastName, String phoneNumber) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long get_id() {
        return _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
