package com.example.testtask.database;

import android.app.Application;

import androidx.room.Room;
//синглтон-класс для подключения к базе данных
public class App extends Application {

    private static App instance;
    private ContactDatabase contactDatabase;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        contactDatabase = Room.databaseBuilder(getApplicationContext(), ContactDatabase.class, "contactDatabase")
                .allowMainThreadQueries()
                .build();
    }

    public ContactDatabase getDatabaseInstance() {
        return contactDatabase;
    }
}
