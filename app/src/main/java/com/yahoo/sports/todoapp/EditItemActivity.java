package com.yahoo.sports.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private String itemText;
    private int itemPosition;
    private EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemText = getIntent().getStringExtra("item");
        itemPosition = getIntent().getIntExtra("position", 0);
        etEditItem = (EditText)findViewById(R.id.etEditItem);
        etEditItem.setText(itemText);
    }

    public void onSave(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("item", etEditItem.getText().toString());
        intent.putExtra("position", itemPosition);

        setResult(0, intent);
        finish();
    }
}
