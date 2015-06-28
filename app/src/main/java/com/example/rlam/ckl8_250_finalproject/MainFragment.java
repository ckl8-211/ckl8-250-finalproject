package com.example.rlam.ckl8_250_finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 *
 * A fragment that shows a list of taichi movements.
 * Use CursorLoader to load data
 *
 */

public class MainFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor>
{
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
//    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    private SimpleCursorAdapter mAdapter;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.example.rlam.ckl8_250_finalproject.R.layout.fragment_main, container, false);
        String[] fromFields = new String[] {TaskListContract.TaskList.TASK_NAME};
        int[] toFields = new int[] {android.R.id.text1};

        getLoaderManager().initLoader(0, null, this);

        mAdapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                null,
                fromFields,
                toFields,
                0
        );

        setListAdapter(mAdapter);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        // Start a new or restarts an existing loader
        getLoaderManager().restartLoader(0, null, this);
    }

    /**
     * Called when a new Loader needs to be created
     *
     * @param i
     * @param bundle
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                TaskListContract.TaskList.CONTENT_URI,
                TaskListContract.TaskList.PROJECTION,
                null,
                null,
                null
                );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(com.example.rlam.ckl8_250_finalproject.R.menu.menu_mainfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case com.example.rlam.ckl8_250_finalproject.R.id.action_add:
                //TODO: add data
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(intent);
//                Toast.makeText(getActivity(), "To be implemented", Toast.LENGTH_LONG).show();
                break;
            case com.example.rlam.ckl8_250_finalproject.R.id.action_delete:
                //TODO: delete data
                Toast.makeText(getActivity(), "To be implemented", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
        Uri uri = Uri.parse(TaskListContract.TaskList.CONTENT_URI + "/" + id);
        intent.putExtra("uri", uri);
        startActivity(intent);

        super.onListItemClick(l, v, position, id);
    }
}