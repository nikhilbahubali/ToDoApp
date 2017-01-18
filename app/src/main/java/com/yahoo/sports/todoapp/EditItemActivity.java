package com.yahoo.sports.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private String itemText;
    private long itemId;
    private EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemText = getIntent().getStringExtra("item");
        itemId = getIntent().getLongExtra("id", 0);
        etEditItem = (EditText)findViewById(R.id.etEditItem);
        etEditItem.setText(itemText);
    }

    public void onSave(View view) {
        // create intent with result to return
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("olditem", itemText);
        intent.putExtra("modifieditem", etEditItem.getText().toString());
        intent.putExtra("id", itemId);

        // set the result for caller activity
        setResult(0, intent);
        finish();
    }
}
