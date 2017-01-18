package com.yahoo.sports.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EditItemActivity extends AppCompatActivity {

    private String itemText;
    private long itemId;
    private ToDoItem.Priority priority;
    private EditText etEditItem;
    private Spinner spPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemText = getIntent().getStringExtra("item");
        itemId = getIntent().getLongExtra("id", 0);
        priority = ToDoItem.Priority.valueOf(getIntent().getStringExtra("priority"));

        etEditItem = (EditText)findViewById(R.id.etEditItem);
        etEditItem.setText(itemText);

        spPriority = (Spinner)findViewById(R.id.spPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priorities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);

        spPriority.setSelection(adapter.getPosition(priority.toString()));
    }

    public void onSave(View view) {
        // create intent with result to return
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", itemId);
        intent.putExtra("olditem", itemText);
        intent.putExtra("modifieditem", etEditItem.getText().toString());
        intent.putExtra("priority", spPriority.getSelectedItem().toString());

        // set the result for caller activity
        setResult(0, intent);
        finish();
    }
}
