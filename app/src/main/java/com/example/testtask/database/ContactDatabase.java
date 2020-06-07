package com.example.testtask.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.testtask.dao.ContactDao;
import com.example.testtask.model.Contact;
//абстрактный класс базы данных
@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
