package com.example.testtask.model;

import android.media.Image;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
//сущностть для базы данных; модель контакта
@Entity
public class Contact implements Serializable {

    @PrimaryKey
    @NonNull
    private int id;
    private String name;
    private String surname;

    private String number;
    private String email;


    public Contact(int id, String name, String surname, String number, String email)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.email = email;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }



    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }



    public void setNumber(String number) {
        this.number = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
