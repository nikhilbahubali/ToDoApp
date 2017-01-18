package com.yahoo.sports.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;
import java.util.List;

import static com.yahoo.sports.todoapp.ToDoItem.Priority.None;

public class MainActivity extends AppCompatActivity {
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

    private void removeItemFromDatabase(ToDoItem toDoItem) {
        toDoItem.delete();
    }

    private void removeItemFromDatabase(String itemText) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setItemName(itemText);
        toDoItem.delete();
    }

    private void readItemsFromDatabase() {
        // read from database
        toDoItems = SQLite.select().from(ToDoItem.class).orderBy(ToDoItem_Table.id, true).queryList();
/*
        for (ToDoItem todoItem : toDoItems) {
            todoItemsAdapter.add(todoItem);
        }
*/
    }

    private void writeItemsToDatabase() {
/*
        // write to database
        int id = 0;
        for (String item: items) {
            ToDoItem todoItem = new ToDoItem();
            todoItem.setItemName(item);
            todoItem.save();
            id++;
        }
*/
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
/*
                removeItemFromDatabase(items.get(position));
                items.remove(position);
                todoItemsAdapter.notifyDataSetChanged();
*/
                return true;
            }
        });

        // click listener
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
                String itemText = items.get(position);

                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("item", itemText);
                intent.putExtra("position", position);

                startActivityForResult(intent, 0);
*/
            }
        });

        // initialize
        populateListItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public void onAdd(View view) {
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

/*
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
*/
}
