package com.yahoo.sports.todoapp;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by nikhilba on 1/16/17.
 */

@Table(database = ToDoAppDatabase.class)
public class ToDoItem extends BaseModel {

    @Column
    @PrimaryKey
    public String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}