package com.bitwyze.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.Date;


/**
 * Created by scottrichards on 8/30/15.
 */
public class ToDoItem {
    private long id;
    private String title;
    private String priority;
    private Date dueDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public ContentValues setValues() {
        ContentValues values = new ContentValues();
        values.put(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_TITLE, title);
        values.put(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_PRIORITY, priority);
   //     if (dueDate != null) {
            values.put(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_DUE_DATE, persistDate(dueDate));
    //    }
        return values;
    }

    public void setFromCursor(Cursor cursor)
    {
        setId(cursor.getLong(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry._ID)));
        setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_TITLE)));
        setPriority(cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_PRIORITY)));
        Long longDate;
        Date convertedDate;
        int dueDateColumnIndex = cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_DUE_DATE);
        longDate = cursor.getLong(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_DUE_DATE));
        setDueDate(loadDate(cursor,cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_DUE_DATE)));
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public static Long persistDate(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public static Date loadDate(Cursor cursor, int index) {
        if (cursor.isNull(index)) {
            return null;
        }
        return new Date(cursor.getLong(index));
    }
}
