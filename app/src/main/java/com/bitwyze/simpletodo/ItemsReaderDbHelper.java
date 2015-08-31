package com.bitwyze.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.bitwyze.simpletodo.ToDoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scottrichards on 8/26/15.
 */
public class ItemsReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final String TAG = "ItemsReaderDbHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static ItemsReaderDbHelper _instance;


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ToDoItemReaderContract.ToDoItemEntry.TABLE_NAME + " (" +
                    ToDoItemReaderContract.ToDoItemEntry._ID + " INTEGER PRIMARY KEY," +
                    ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_PRIORITY + TEXT_TYPE +
            " )";
    private static final String SQL_GET_RECORD =    "SELECT  * FROM " + ToDoItemReaderContract.ToDoItemEntry.TABLE_NAME +
                                                    " WHERE " + ToDoItemReaderContract.ToDoItemEntry._ID + " = ";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ToDoItemReaderContract.ToDoItemEntry.TABLE_NAME;


    private ItemsReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static synchronized ItemsReaderDbHelper getInstance(Context context)
    {
        if (_instance == null)
        {
            _instance = new ItemsReaderDbHelper(context);
        }
        return _instance;
    }

    public Cursor getCursor(Context context) {
        SQLiteDatabase db = ItemsReaderDbHelper.getInstance(context).getWritableDatabase();
        return db.rawQuery("SELECT  * FROM " + ToDoItemReaderContract.ToDoItemEntry.TABLE_NAME, null);
    }

    public ToDoItem getItem(Context context,Long itemId) {
        SQLiteDatabase db = ItemsReaderDbHelper.getInstance(context).getWritableDatabase();
        Log.d("ItemsReaderDbHelper", SQL_GET_RECORD + itemId);
        Cursor cursor =  db.rawQuery(SQL_GET_RECORD + itemId, null);
        cursor.moveToFirst();
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setFromCursor(cursor);
        return toDoItem;
    }

    // Insert a post into the database
    public void addItem(String title,String priority) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {


            ContentValues values = new ContentValues();
            values.put(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_TITLE, title);
            values.put(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_PRIORITY, priority);
            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insert(ToDoItemReaderContract.ToDoItemEntry.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteItem(Long itemId) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            String itemIdString = itemId.toString();
            db.delete(ToDoItemReaderContract.ToDoItemEntry.TABLE_NAME, ToDoItemReaderContract.ToDoItemEntry._ID + "= ?" ,new String[]{itemIdString});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete item from database");
        } finally {
            db.endTransaction();
        }
    }

    public long updateItem(ToDoItem item) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long itemId = -1;

        db.beginTransaction();
        try {
            ContentValues values = item.setValues();
            int rows = db.update(ToDoItemReaderContract.ToDoItemEntry.TABLE_NAME, values, ToDoItemReaderContract.ToDoItemEntry._ID + "= ?", new String[]{Long.toString(item.getId())});

            // Check if update succeeded
            if (rows == 0) {
                // user with this userName did not already exist, so insert new user
                itemId = db.insertOrThrow(ToDoItemReaderContract.ToDoItemEntry.TABLE_NAME, null, values);
                db.setTransactionSuccessful();
            } else {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return itemId;
    }



}
