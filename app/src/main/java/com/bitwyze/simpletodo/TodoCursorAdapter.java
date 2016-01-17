package com.bitwyze.simpletodo;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
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

    public ToDoCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context,cursor,flags);
    }

    @Override
    public View newView(Context context, android.database.Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    // Binds data to the item_todo View for each item
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
        if (toDoItem.getPriority().equals("None"))   // Don't display "None"
            tvPriority.setText("");
        else
            tvPriority.setText(toDoItem.getPriority());
        switch (toDoItem.getPriority()) {
            case "Low" : tvPriority.setTextColor(context.getResources().getColor( R.color.priority_green_text));
                break;
            case "Medium" : tvPriority.setTextColor(context.getResources().getColor(R.color.priority_yellow_text));
                break;
            case "High" : tvPriority.setTextColor(context.getResources().getColor(R.color.priority_red_text));
                break;

        }
    }



}
