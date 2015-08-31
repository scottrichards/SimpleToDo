package com.bitwyze.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;


public class EditTaskActivity extends ActionBarActivity implements OnItemSelectedListener  {
//    int itemPosition;
    Long itemId;
    ToDoItem toDoItem;
    private Spinner prioritySpinner;
    public String newPriority;

    // read in the data passed from the main view and set the edittext
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        itemId = getIntent().getLongExtra("id", 0L);
        EditText editText = (EditText)findViewById(R.id.editText);
        toDoItem = ItemsReaderDbHelper.getInstance(this).getItem(this, itemId);
        editText.setText(toDoItem.getTitle());
        newPriority = toDoItem.getPriority();
        addListenerOnSpinnerItemSelection();
        setPrioritySpinnerItem(newPriority);
    }

    public void addListenerOnSpinnerItemSelection() {
        prioritySpinner = (Spinner) findViewById(R.id.spinner);
        prioritySpinner.setOnItemSelectedListener(this);
    }

    private void setPrioritySpinnerItem(String priority)
    {
        prioritySpinner.setSelection(((ArrayAdapter)prioritySpinner.getAdapter()).getPosition(priority));
    }

    // User clicked save go back to Main view
    public void onSaveItem(View view) {
        Intent mainIntent = new Intent(EditTaskActivity.this,MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        toDoItem.setTitle(editText.getText().toString());
        toDoItem.setPriority(newPriority);
        ItemsReaderDbHelper.getInstance(this).updateItem(toDoItem);
        startActivity(mainIntent);
    }

    public void onCancel(View view)
    {
        Intent mainIntent = new Intent(EditTaskActivity.this,MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("onItemSelected", parent.getItemAtPosition(position).toString());
        newPriority = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
