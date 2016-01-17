package com.bitwyze.simpletodo;

import android.provider.BaseColumns;

/**
 * Created by scottrichards on 8/26/15.
 */
public final class ToDoItemReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ToDoItemReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ToDoItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "todoitems";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PRIORITY = "priority";
        public static final String COLUMN_NAME_DUE_DATE = "duedate";
        public static final String COLUMN_NAME_COMPLETE = "complete";
    }


}
