package com.bitwyze.simpletodo;

import android.app.DialogFragment;
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
import android.widget.TextView;

import java.util.Date;


public class EditTaskActivity extends ActionBarActivity implements OnItemSelectedListener, DatePickerFragment.EditNameDialogListener {
//    int itemPosition;
    Long itemId;
    ToDoItem toDoItem;
    private Spinner prioritySpinner;
    private TextView dueDateText;
    public String newPriority;
    public Date newDate;
    public Boolean setDate = false;

    // read in the data passed from the main view and set the edittext
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setDate = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        itemId = getIntent().getLongExtra("id", 0L);
        EditText editText = (EditText)findViewById(R.id.editText);
        toDoItem = ItemsReaderDbHelper.getInstance(this).getItem(this, itemId);
        editText.setText(toDoItem.getTitle());
        newPriority = toDoItem.getPriority();
        addListenerOnSpinnerItemSelection();
        dueDateText = (TextView) findViewById(R.id.displayDate);
        setPrioritySpinnerItem(newPriority);
    }

    public void addListenerOnSpinnerItemSelection() {
        prioritySpinner = (Spinner) findViewById(R.id.spinner);
        prioritySpinner.setOnItemSelectedListener(this);
    }

    private void setPrioritySpinnerItem(String priority)
    {
        prioritySpinner.setSelection(((ArrayAdapter) prioritySpinner.getAdapter()).getPosition(priority));
    }

    // Handlers
    // User clicked save go back to Main view
    public void onSaveItem(View view) {
        Intent mainIntent = new Intent(EditTaskActivity.this,MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        toDoItem.setTitle(editText.getText().toString());
        toDoItem.setPriority(newPriority);
        if (setDate) {
            toDoItem.setDueDate(newDate);
        }
        ItemsReaderDbHelper.getInstance(this).updateItem(toDoItem);
        startActivity(mainIntent);
    }

    public void onSetDueDate(View view) {
        Log.d("EditTaskActivity", "onSetDueDate: ");
        DialogFragment picker = new DatePickerFragment();
        picker.show(getFragmentManager(), "datePicker");
    }

    // Handle receiving date info from DatePicker DialogFragment
    public void onFinishEditDialog(Boolean setDate,String formattedDate,Date selectedDate) {
        if (setDate) {
            this.setDate = setDate;
            this.newDate = selectedDate;
            dueDateText.setText(formattedDate);
        }
        Log.d("EditTaskActivity","Selected Date: " + formattedDate);
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
