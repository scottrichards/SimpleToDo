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
// Find fields to populate in inflated template
        ToDoItem  toDoItem = new ToDoItem();
        // Extract properties from cursor
        toDoItem.setFromCursor(cursor);
        TextView tvBody = (TextView) view.findViewById(R.id.itemName);
        TextView tvPriority = (TextView) view.findViewById(R.id.itemPriority);

        if (toDoItem.getDueDate() != null) {
            TextView tvDueDate = (TextView) view.findViewById(R.id.dueDate);
            String dateString = DateFormat.format("yyyy-MM-dd", toDoItem.getDueDate()).toString();
            tvDueDate.setText(dateString);
        }
        String body = cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_TITLE));
        String priority = cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_PRIORITY));
 //       Long dueDate = new Date(cursor.getLong(ToDoItemReaderContract.ToDoItemEntry.COLUMN_DUE_DATE));
        // Populate fields with extracted properties
        tvBody.setText(body);
        tvPriority.setText(String.valueOf(priority));
    }


}
