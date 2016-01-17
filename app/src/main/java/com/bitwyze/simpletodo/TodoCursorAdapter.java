package com.bitwyze.simpletodo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.DateFormat;

/**
 * Created by scottrichards on 8/29/15.
 */
public class ToDoCursorAdapter extends CursorAdapter {
//    private DateFormat dateFormat;

    public ToDoCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context,cursor,flags);
 //       dateFormat = DateFormat.getDateInstance();
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
        TextView tvDueDate = (TextView) view.findViewById(R.id.dueDateLabel);
        if (toDoItem.getDueDate() != null) {
            String localeDate = DateFormatting.getInstance().localeFormat(toDoItem.getDueDate());
            tvDueDate.setText(localeDate);
            tvDueDate.setVisibility(View.VISIBLE);
        } else {
            tvDueDate.setText("");
            tvDueDate.setVisibility(View.GONE);
        }
        tvBody.setText(toDoItem.getTitle());
        tvPriority.setText(toDoItem.getPriority());
    }


}
