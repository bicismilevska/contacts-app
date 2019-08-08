package com.example.biljanasapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id=0;
    @ColumnInfo(name="first_name")
    private String firstname;
    @ColumnInfo(name = "last_name")
    private String lastname;

    public User(String firstname, String lastname, String number) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.number = number;
    }

    @ColumnInfo(name = "phone_number")
    private String number;



    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getNumber() {
        return number;
    }
}
