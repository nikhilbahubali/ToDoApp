package com.yahoo.sports.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private EditText etAddItem;

    public void onAdd(View view) {
        // update listview
        itemsAdapter.add(etAddItem.getText().toString());
        etAddItem.setText("");

        // serialize
        writeItemsToDatabase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        etAddItem = (EditText)findViewById(R.id.etAddItem);

        // long click listener
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItemFromDatabase(items.get(position));
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        // click listener
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemText = items.get(position);

                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("item", itemText);
                intent.putExtra("position", position);

                startActivityForResult(intent, 0);
            }
        });

        // initialize
        populateListItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // extract returned result
        String modifiedItemText = data.getStringExtra("item");
        int itemPosition = data.getIntExtra("position", 0);

        // update UI to reflect action
        removeItemFromDatabase(items.get(itemPosition));
        items.remove(itemPosition);
        items.add(itemPosition, modifiedItemText);
        itemsAdapter.notifyDataSetChanged();

        // serialize
        writeItemsToDatabase();
    }

    private void populateListItems() {
        // deserialize
        readItemsFromDatabase();

        // update listview
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
    }

    private void removeItemFromDatabase(String itemText) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setItemName(itemText);
        toDoItem.delete();
    }

    private void readItemsFromDatabase() {
        // read from database
        items = new ArrayList<>();
        List<ToDoItem> todoItems = SQLite.select().from(ToDoItem.class).queryList();
        for (ToDoItem todoItem : todoItems) {
            items.add(todoItem.getItemName());
        }
    }

    private void writeItemsToDatabase() {
        // write to database
        int id = 0;
        for (String item: items) {
            ToDoItem todoItem = new ToDoItem();
            todoItem.setItemName(item);
            todoItem.save();
            id++;
        }
    }

    private void readItemsFromFile() {
        // read from file
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(file));
        } catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    private void writeItemsToFile() {
        // write to file
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
