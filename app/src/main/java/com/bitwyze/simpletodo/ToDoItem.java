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
    private SimpleDateFormat iso8601Format = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public ToDoItem() {
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

    public String getFormattedDate() {
        return formattedDate;
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
   //     if (dueDate != null) {
            values.put(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_DUE_DATE, formatDate(dueDate));
        //    }
        return values;
    }

    public void setFromCursor(Cursor cursor) {
        setId(cursor.getLong(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry._ID)));
        setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_TITLE)));
        setPriority(cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_PRIORITY)));
        setDueDate(parseDateString(cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_DUE_DATE))));
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
            return iso8601Format.format(date);
        }
    }

    public Date parseDateString(String dateString) {
        try {
            if (dateString == null || dateString == "") {
                return null;
            }
            Date date = iso8601Format.parse(dateString);
            System.out.println(date);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


}
