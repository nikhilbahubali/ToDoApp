package com.yahoo.sports.todoapp;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by nikhilba on 1/16/17.
 */


@Database(name = ToDoAppDatabase.NAME, version = ToDoAppDatabase.VERSION)

public class ToDoAppDatabase {
    public static final String NAME = "ToDoAppDatabaseV1";
    public static final int VERSION = 1;
}
