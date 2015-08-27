package com.bitwyze.simpletodo;

import android.content.Context;
import android.content.Intent;
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
    ArrayAdapter<String> itemsAdapter;
    ArrayList<String> items;
    private static final String TAG = "MainActivity";
    private Boolean readFromDatabase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        if (!readFromDatabase) {
            readItems();
            readFromDatabase = true;
        }
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        String itemUpdatedData = getIntent().getStringExtra("itemUpdatedData");   // check if we are coming back from editing an item we will have itemData
        if (itemUpdatedData != null && itemUpdatedData.length() > 0) {    // if itemData update the item
            int itemPosition = getIntent().getIntExtra("itemPosition",0);
            String oldItemData = getIntent().getStringExtra("itemData");   // check if we are coming back from editing an item we will have itemData
            items.set(itemPosition, itemUpdatedData);
            itemsAdapter.notifyDataSetChanged();
            ItemsReaderDbHelper.getInstance(this).updateItem(oldItemData, itemUpdatedData);
        }
    }


    // add an item to To Do List
    public void onAddItem(View v)
    {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);    // dismiss the keyboard
        imm.hideSoftInputFromWindow(etNewItem.getWindowToken(), 0);
        String itemText = etNewItem.getText().toString();
        items.add(itemText);    // add the item
        ItemsReaderDbHelper.getInstance(this).addItem(itemText);
        itemsAdapter.notifyDataSetChanged();
        etNewItem.setText("");      // clear out the item
    }

    private void dismissKeyboard() {

    }

    private void setupListViewListener() {
        // long click indicates deleting an item
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        String itemString = itemsAdapter.getItem(position);
                        ItemsReaderDbHelper.getInstance(getApplicationContext()).deleteItem(itemString);
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
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
                        Intent editIntent = new Intent(MainActivity.this,EditTaskActivity.class);
                        if (position < itemsAdapter.getCount()) {
                            String itemString = itemsAdapter.getItem(position);
                            editIntent.putExtra("itemData", itemString);
                            editIntent.putExtra("itemPosition", position);
                        }
                        startActivity(editIntent);
                    }
                }
        );
    }

    // read the items from text file todo.txt
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = ItemsReaderDbHelper.getInstance(this).getAllItems();
        } catch (Exception e) {
            items = new ArrayList<String>();
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
