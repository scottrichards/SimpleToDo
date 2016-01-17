package com.bitwyze.simpletodo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.bitwyze.simpletodo.R;
import com.bitwyze.simpletodo.models.ToDoItem;
import com.bitwyze.simpletodo.utils.DateFormatting;

/**
 * Created by scottrichards on 8/29/15.
 */
public class ToDoCursorAdapter extends CursorAdapter {

    public ToDoCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context,cursor,flags);
    }

    @Override
    public View newView(Context context, android.database.Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    // Binds data to the item_todo View for each item
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ToDoItem toDoItem = new ToDoItem();
        toDoItem.setFromCursor(cursor);
        final TextView tvItem = (TextView) view.findViewById(R.id.itemName);
//        tvItem.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                if (tvItem.isChecked()) {
//                    toDoItem.setComplete(1);
//                } else {
//                    toDoItem.setComplete(0);
//                }
//                ItemsReaderDbHelper.getInstance(v.getContext()).updateItem(toDoItem);
//            }
//        });
        final CheckBox cbComplete = (CheckBox) view.findViewById(R.id.completeCheckBox);
        TextView tvPriority = (TextView) view.findViewById(R.id.itemPriority);
        TextView tvDueDate = (TextView) view.findViewById(R.id.dueDateLabel);
        if (toDoItem.getDueDate() != null) {
            String localeDate = DateFormatting.getInstance().localeFormat(toDoItem.getDueDate());
            tvDueDate.setText(localeDate);
            tvDueDate.setVisibility(View.VISIBLE);
        } else {
            tvDueDate.setText("");
            tvDueDate.setVisibility(View.GONE);
        }
        tvItem.setText(toDoItem.getTitle());
        if (toDoItem.getPriority().equals("None"))   // Don't display "None"
            tvPriority.setText("");
        else
            tvPriority.setText(toDoItem.getPriority());
        if (toDoItem.getComplete() > 0) {
            cbComplete.setChecked(true);
        } else {
            cbComplete.setChecked(false);
        }
        switch (toDoItem.getPriority()) {
            case "Low" : tvPriority.setTextColor(context.getResources().getColor( R.color.priority_green_text));
                break;
            case "Medium" : tvPriority.setTextColor(context.getResources().getColor(R.color.priority_yellow_text));
                break;
            case "High" : tvPriority.setTextColor(context.getResources().getColor(R.color.priority_red_text));
                break;

        }
    }



}
