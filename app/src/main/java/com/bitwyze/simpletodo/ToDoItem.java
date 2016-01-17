package com.bitwyze.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by scottrichards on 8/30/15.
 */
public class ToDoItem {
    private long id;
    private String title;
    private String priority;
    private Date dueDate;
    private String formattedDate;

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    private int complete;

    public ToDoItem()
    {

    }

    public ToDoItem(String title, String priority, Date date) {
        this.title = title;
        this.priority = priority;
        this.dueDate = date;
    }

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

    public String getLocaleDate() {
        if (dueDate != null) {
            return DateFormatting.getInstance().localeFormat(dueDate);
        } else {
            return "";
        }
    }


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        this.formattedDate = formatDate(dueDate);
    }

    public ContentValues setValues() {
        ContentValues values = new ContentValues();
        values.put(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_TITLE, title);
        values.put(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_PRIORITY, priority);
        values.put(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_DUE_DATE, formatDate(dueDate));
        values.put(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_COMPLETE, complete);
        return values;
    }

    public void setFromCursor(Cursor cursor) {
        setId(cursor.getLong(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry._ID)));
        setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_TITLE)));
        setPriority(cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_PRIORITY)));
        setDueDate(DateFormatting.getInstance().yearMonthDayFormat(cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_DUE_DATE))));
        setComplete(cursor.getInt(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_COMPLETE)));
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return title;
    }


    public String formatDate(Date date) {
        if (date == null) {
            return null;
        } else {
            return DateFormatting.getInstance().yearMonthDayFormat(date);
        }
    }

    public String formatDateTime(Date date) {
        if (date == null) {
            return null;
        } else {
            return DateFormatting.getInstance().iso8601Format(date);
        }
    }


}
