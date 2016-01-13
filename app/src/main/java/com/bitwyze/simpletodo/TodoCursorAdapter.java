package com.bitwyze.simpletodo;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by scottrichards on 8/29/15.
 */
public class TodoCursorAdapter extends CursorAdapter {
    public TodoCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context,cursor,flags);
    }

    @Override
    public View newView(Context context, android.database.Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ToDoItem  toDoItem = new ToDoItem();
        toDoItem.setFromCursor(cursor);
        TextView tvBody = (TextView) view.findViewById(R.id.itemName);
        TextView tvPriority = (TextView) view.findViewById(R.id.itemPriority);
        TextView tvDueDate = (TextView) view.findViewById(R.id.dueDate);
        if (toDoItem.getDueDate() != null) {
            tvDueDate.setText(toDoItem.getFormattedDate());
        } else {
            tvDueDate.setText("");
        }
        tvBody.setText(toDoItem.getTitle());
        tvPriority.setText(toDoItem.getPriority());
    }


}
