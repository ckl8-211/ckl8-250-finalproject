package com.example.rlam.ckl8_250_finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Margaret on 4/21/2015.
 *
 * Helps create the database and its tables
 */
public class TaskListDbHelper extends SQLiteOpenHelper {

    private final static String LOG_TAG = TaskListDbHelper.class.getSimpleName();

    // Database name
    private final static String DB_NAME = "tasklist3.db";

    // Database version
    private final static int DB_VERSION = 1;

    // Version table
    private final static String TASK_LIST_TABLE_NAME = TaskListContract.TaskList.TABLE_NAME;
    private final static String TASK_LIST_ROW_ID = TaskListContract.TaskList.ID;
    private final static String TASK_LIST_ROW_TASK_NAME = TaskListContract.TaskList.TASK_NAME;
    private final static String TASK_LIST_ROW_TASK_DESCRIPTION =TaskListContract.TaskList.TASK_DESCRIPTION;

    // SQL statement to create the Version table
    private final static String TASK_LIST_TABLE_CREATE =
                    "CREATE TABLE " +
                    TASK_LIST_TABLE_NAME + " (" +
                    TASK_LIST_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    TASK_LIST_ROW_TASK_NAME + " TEXT, " +
                    TASK_LIST_ROW_TASK_DESCRIPTION + " TEXT" + ");";

    public TaskListDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create Version table
        db.execSQL(TASK_LIST_TABLE_CREATE);
        Log.i(LOG_TAG, "Creating table with query: " + TASK_LIST_TABLE_CREATE);

        // Create initial data
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_LIST_ROW_TASK_NAME, "Ba He Liang Zhi");
        contentValues.put(TASK_LIST_ROW_TASK_DESCRIPTION, "The left hand is too much to the side");

        db.insert(TASK_LIST_TABLE_NAME, // table name
                null,
                contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TASK_LIST_TABLE_NAME);
        onCreate(db);

    }

}
