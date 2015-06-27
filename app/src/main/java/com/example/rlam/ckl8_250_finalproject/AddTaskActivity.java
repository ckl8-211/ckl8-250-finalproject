package com.example.rlam.ckl8_250_finalproject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddTaskActivity extends ActionBarActivity {

    private EditText mEditTextTaskName;
    private EditText mEditTextTaskDesc;
    ContentResolver contentResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mEditTextTaskName = (EditText)findViewById(R.id.editTextTaskName);
        mEditTextTaskDesc = (EditText)findViewById(R.id.editTextTaskDescription);
        Button buttonOk = (Button)findViewById(R.id.editTextButtonOK);
        buttonOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                insertTaskToDb();
            }
        });
    }
    private void insertTaskToDb() {
        contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(TaskListContract.TaskList.TASK_NAME, getInput(mEditTextTaskName));
        values.put(TaskListContract.TaskList.TASK_DESCRIPTION, getInput(mEditTextTaskDesc));
        contentResolver.insert(TaskListContract.TaskList.CONTENT_URI, values);
        finish(); // go back to main screen
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
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

    /**
     * Get the user input in EditText
     * @param editText
     * @return
     */
    private String getInput(EditText editText) {
        return editText.getText().toString().trim();
    }

}
