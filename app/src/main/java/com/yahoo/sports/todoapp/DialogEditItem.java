package com.yahoo.sports.todoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by nikhilba on 1/22/17.
 */

public class DialogEditItem extends DialogFragment {
    private Context context;
    private String itemText;
    private long itemId;
    private ToDoItem.Priority priority;
    private EditText etEditItem;
    private Spinner spPriority;

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface EditItemDialogListener {
        void OnFinishedEditItem(Bundle args);
    }

    public DialogEditItem() {

    }

    public static DialogEditItem newInstance(Context context) {
        Bundle args = new Bundle();
        DialogEditItem dialogEditItem = new DialogEditItem();
        dialogEditItem.setContext(context);
        dialogEditItem.setArguments(args);

        return dialogEditItem;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().setTitle("Edit An Item");
        return inflater.inflate(R.layout.dialog_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // fetch intent data
        Bundle args = getArguments();
        itemText = args.getString("item");
        itemId = args.getLong("id", 0);
        priority = ToDoItem.Priority.valueOf(args.getString("priority"));

        // populate edit UI
        etEditItem = (EditText)view.findViewById(R.id.etEditItem);
        etEditItem.setText(itemText);
        spPriority = (Spinner)view.findViewById(R.id.spPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priorities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);
        spPriority.setSelection(adapter.getPosition(priority.toString()));

        Button saveButton = (Button) view.findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave(v);
            }
        });
    }

    public void onSave(View view) {
        Bundle args = new Bundle();
        args.putLong("id", itemId);
        args.putString("olditem", itemText);
        args.putString("modifieditem", etEditItem.getText().toString());
        args.putString("priority", spPriority.getSelectedItem().toString());

        // set the result for caller activity
        EditItemDialogListener listener = (EditItemDialogListener) getActivity();
        listener.OnFinishedEditItem(args);

        // dismiss dialog
        dismiss();
    }
}

