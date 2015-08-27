package com.bitwyze.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditTaskActivity extends ActionBarActivity {
    int itemPosition;
    String oldItemData;

    // read in the data passed from the main view and set the edittext
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        oldItemData = getIntent().getStringExtra("itemData");
        EditText editText = (EditText)findViewById(R.id.editText);
        itemPosition = getIntent().getIntExtra("itemPosition",0);
        editText.setText(oldItemData);
    }


    // User clicked save go back to Main view
    public void onSaveItem(View view) {
        Intent mainIntent = new Intent(EditTaskActivity.this,MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        mainIntent.putExtra("itemUpdatedData", editText.getText().toString());
        mainIntent.putExtra("itemPosition", itemPosition);
        mainIntent.putExtra("itemData", oldItemData);
        startActivity(mainIntent);
    }
}
