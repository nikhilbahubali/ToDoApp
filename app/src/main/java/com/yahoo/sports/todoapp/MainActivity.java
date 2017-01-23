package com.yahoo.sports.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;
import java.util.List;

import static com.yahoo.sports.todoapp.ToDoItem.Priority.None;

public class MainActivity extends AppCompatActivity implements DialogEditItem.EditItemDialogListener {
    private List<ToDoItem> toDoItems;
    public ToDoItemAdapter todoItemsAdapter;
    private ListView lvItems;
    private EditText etAddItem;

    private void populateListItems() {
        // deserialize
        readItemsFromDatabase();

        // update listview
        todoItemsAdapter = new ToDoItemAdapter(this, toDoItems);
        lvItems.setAdapter(todoItemsAdapter);
    }

    private void readItemsFromDatabase() {
        // read from database
        toDoItems = SQLite.select().from(ToDoItem.class).orderBy(ToDoItem_Table.id, true).queryList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);
        etAddItem = (EditText)findViewById(R.id.etAddItem);

        // initialize
        populateListItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/*
        // extract returned result
        long itemId = data.getLongExtra("id", 0);
        String oldItemText = data.getStringExtra("olditem");
        String modifiedItemText = data.getStringExtra("modifieditem");
        String priority = data.getStringExtra("priority");

        // write to database
        ToDoItem modifiedItem = new ToDoItem();
        modifiedItem.setId(itemId);
        modifiedItem.setItemName(modifiedItemText);
        modifiedItem.setPriority(ToDoItem.Priority.valueOf(priority));
        modifiedItem.save();

        // old item to replace with modified
        ToDoItem oldItem = new ToDoItem();
        oldItem.setId(itemId);
        oldItem.setItemName(oldItemText);

        // update UI to reflect action
        for (int i = 0; i < todoItemsAdapter.getCount(); i++) {
            ToDoItem item = todoItemsAdapter.getItem(i);
            if (item.getId() == itemId) {
                todoItemsAdapter.remove(item);
                todoItemsAdapter.insert(modifiedItem, i);
                todoItemsAdapter.notifyDataSetChanged();
                break;
            }
        }
*/
    }

    public void onAdd(View view) {
        // build object from UI
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setId(new Date().getTime());
        toDoItem.setItemName(etAddItem.getText().toString());
        toDoItem.setPriority(None);

        // update listview
        todoItemsAdapter.add(toDoItem);
        etAddItem.setText("");

        // serialize
        toDoItem.save();
    }

    @Override
    public void OnFinishedEditItem(Bundle args) {
        // extract returned result
        long itemId = args.getLong("id", 0);
        String oldItemText = args.getString("olditem");
        String modifiedItemText = args.getString("modifieditem");
        String priority = args.getString("priority");

        // write to database
        ToDoItem modifiedItem = new ToDoItem();
        modifiedItem.setId(itemId);
        modifiedItem.setItemName(modifiedItemText);
        modifiedItem.setPriority(ToDoItem.Priority.valueOf(priority));
        modifiedItem.save();

        // old item to replace with modified
        ToDoItem oldItem = new ToDoItem();
        oldItem.setId(itemId);
        oldItem.setItemName(oldItemText);

        // update UI to reflect action
        for (int i = 0; i < todoItemsAdapter.getCount(); i++) {
            ToDoItem item = todoItemsAdapter.getItem(i);
            if (item.getId() == itemId) {
                todoItemsAdapter.remove(item);
                todoItemsAdapter.insert(modifiedItem, i);
                todoItemsAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
