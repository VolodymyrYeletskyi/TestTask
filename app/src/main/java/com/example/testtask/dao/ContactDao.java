package com.example.testtask.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testtask.model.Contact;

import java.util.List;
//интерфейс по работе с базой данных
@Dao
public interface ContactDao {

    @Insert
    void insert(Contact contact);

    @Insert
    void insert(List<Contact> contacts);

    @Update
    void update(List<Contact> contacts);

    @Update
    void update(Contact contact);

    @Query("SELECT * FROM Contact")
    List<Contact> getAll();

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM Contact")
    void drop();
}
