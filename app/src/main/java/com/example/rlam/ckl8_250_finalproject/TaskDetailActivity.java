package com.example.rlam.ckl8_250_finalproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class TaskDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle args = new Bundle();

        Uri uri;
        uri = getIntent().getExtras().getParcelable("uri");

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            TaskDetailFragment fragment = new TaskDetailFragment();
            FragmentTransaction ft = fm.beginTransaction();
            args.putParcelable("uri", uri);
            fragment.setArguments(args);
            ft.add(R.id.task_detail_container, fragment);
            ft.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
}
