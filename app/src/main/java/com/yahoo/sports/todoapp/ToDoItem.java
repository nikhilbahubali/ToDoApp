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
    public enum Priority {NONE, LOW, MEDIUM, HIGH};

    @Column
    @PrimaryKey
    public long id;

    @Column
    public String itemName;

    @Column
    public Priority priority;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
