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
    private Spinner dueDateSpinner;
    public String newPriority;
    public Date newDate;
    public Boolean setDate = false;
    String[] dateSpinnerArray = { "Today", "Select Date", "No Date"};
    private ArrayAdapter dateSpinnerArrayAdapter;

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

 //       dueDateTextView.setText(toDoItem.getFormattedDate());
        setPrioritySpinnerItem(newPriority);
        setupDateSpinner();
    }

    public void addListenerOnSpinnerItemSelection() {
        prioritySpinner = (Spinner) findViewById(R.id.spinner);
        prioritySpinner.setOnItemSelectedListener(this);
    }

    private void setupDateSpinner() {
        dueDateSpinner = (Spinner) findViewById(R.id.dateSpinner);
        dateSpinnerArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, dateSpinnerArray);
        dateSpinnerArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        dueDateSpinner.setAdapter(dateSpinnerArrayAdapter);
        dueDateSpinner.setOnItemSelectedListener(this);
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
//            dueDateTextView.setText(formattedDate);
        }
        Log.d("EditTaskActivity", "Selected Date: " + formattedDate);
    }


    public void onCancel(View view)
    {
        Intent mainIntent = new Intent(EditTaskActivity.this,MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("onItemSelected", parent.getItemAtPosition(position).toString());
        switch (parent.getId()) {
            case R.id.spinner :     newPriority = parent.getItemAtPosition(position).toString();
                                    break;
            case R.id.dateSpinner : Log.d("EditTaskActivity","dateSpinner selection: " + parent.getItemAtPosition(position).toString());
                                    setDateSpinnerItem(position,view);
                                    break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setDateSpinnerItem(int item,View view) {
        switch (item) {
            case 0 :    this.newDate = new Date();
                        break;
            case 1 :    onSetDueDate(view);
                        break;
            case 2 :    this.newDate = null;
                        break;
        }
    }
}
