package com.yahoo.sports.todoapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nikhilba on 1/17/17.
 */

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {
    MainActivity mainActivity;

    public ToDoItemAdapter(MainActivity mainActivity, List<ToDoItem> items) {
        super(mainActivity, 0, items);
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ToDoItem toDoItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }

        // populate views rows in listview
        TextView tvItemName = (TextView)convertView.findViewById(R.id.itemName);
        tvItemName.setText(toDoItem.getItemName());
        TextView tvItemPriority = (TextView)convertView.findViewById(R.id.itemPriority);
        tvItemPriority.setText(toDoItem.getPriority().toString());

        // set todoitem object for retrieval in click handler
        tvItemName.setTag(toDoItem);

        tvItemName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // get view's associated object, remove from db
                ToDoItem deleteItem = (ToDoItem) v.getTag();
                removeItemFromDatabase(deleteItem);

                // update UI to reflect action
                for (int i = 0; i < mainActivity.todoItemsAdapter.getCount(); i++) {
                    ToDoItem item = mainActivity.todoItemsAdapter.getItem(i);
                    if (item.getId() == deleteItem.getId()) {
                        mainActivity.todoItemsAdapter.remove(item);
                        mainActivity.todoItemsAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                return true;
            }
        });

        tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get view's associated object
                ToDoItem item = (ToDoItem) v.getTag();

                // fill intent data to pass to edit activity
                Intent intent = new Intent(getContext(), EditItemActivity.class);
                intent.putExtra("item", item.getItemName());
                intent.putExtra("id", item.getId());
                intent.putExtra("priority", item.getPriority().toString());

                mainActivity.startActivityForResult(intent, 0);
            }
        });

        return convertView;
    }

    private void removeItemFromDatabase(ToDoItem toDoItem) {
        toDoItem.delete();
    }

}
