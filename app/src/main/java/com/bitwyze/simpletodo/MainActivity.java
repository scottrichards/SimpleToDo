package com.bitwyze.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ListView lvItems;
    private static final String TAG = "MainActivity";
    private Boolean readFromDatabase = false;
    private Cursor todoCursor;
    TodoCursorAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!readFromDatabase) {
            readItems();
            readFromDatabase = true;

        }

        setupListViewListener();

    }


    // add an item to To Do List
    public void onAddItem(View v)
    {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);    // dismiss the keyboard
        imm.hideSoftInputFromWindow(etNewItem.getWindowToken(), 0);
        String itemText = etNewItem.getText().toString();
        ItemsReaderDbHelper.getInstance(this).addItem(itemText,"Medium");
        etNewItem.setText("");      // clear out the item
        readItems();
    }

    private void dismissKeyboard() {

    }

    private void setupListViewListener() {
        // long click indicates deleting an item
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Cursor cursor = (Cursor) lvItems.getItemAtPosition(position);

                        // Get the state's capital from this row in the database.
                        Long itemId =
                                cursor.getLong(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry._ID));
                        ItemsReaderDbHelper.getInstance(getApplicationContext()).deleteItem(itemId);
                        todoAdapter.notifyDataSetChanged();
                        readItems();
                        return true;
                    }
                }
        );

        lvItems.setClickable(true);
        // when click on item open up EditTaskActivity
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Cursor cursor = (Cursor) lvItems.getItemAtPosition(position);

                        // Get the state's capital from this row in the database.
                        Long itemId =
                                cursor.getLong(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry._ID));
                        String itemTitle =
                                cursor.getString(cursor.getColumnIndexOrThrow(ToDoItemReaderContract.ToDoItemEntry.COLUMN_NAME_TITLE));
                        Log.d(this.getClass().getSimpleName(),"Item: " + itemTitle);
                        Intent editIntent = new Intent(MainActivity.this,EditTaskActivity.class);
                        editIntent.putExtra("id", itemId);
                        startActivity(editIntent);
                    }
                }
        );
    }

    // read the items from text file todo.txt
    private void readItems() {
        try {
            todoCursor = ItemsReaderDbHelper.getInstance(this).getCursor(this);
            lvItems = (ListView)findViewById(R.id.lvItems);
            // Setup cursor adapter using cursor from last step
            todoAdapter = new TodoCursorAdapter(this, todoCursor, 0);
            // Attach cursor adapter to the ListView
            lvItems.setAdapter(todoAdapter);
        } catch (Exception e) {
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
