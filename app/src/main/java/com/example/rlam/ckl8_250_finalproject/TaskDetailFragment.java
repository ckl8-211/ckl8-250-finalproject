package com.example.rlam.ckl8_250_finalproject;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 * This Fragment shows the details of the taichi movement
 *
 * A simple {@link Fragment} subclass.
 * /
 *
/** Activities that contain this fragment must implement the*/
/*  {@link DetailFragment.OnFragmentInteractionListener} */

/**interface to handle interaction events.*/

public class TaskDetailFragment extends Fragment {

    private Uri mUri;
    private String version;
    private String taskID;
    private String taskName;
    private String taskDescription;

//    private OnFragmentInteractionListener mListener;

    public TaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle!=null) {
            mUri = bundle.getParcelable("uri"); // get URI
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.example.rlam.ckl8_250_finalproject.R.layout.fragment_detail, container, false);
        Cursor c = getActivity().getContentResolver().query(mUri,
                TaskListContract.TaskList.PROJECTION,
                null,
                null,
                null);
        if(c.moveToFirst()) {
            taskID = c.getString(c.getColumnIndexOrThrow(TaskListContract.TaskList.ID));
            taskName = c.getString(c.getColumnIndexOrThrow(TaskListContract.TaskList.TASK_NAME));
            taskDescription = c.getString(c.getColumnIndexOrThrow(TaskListContract.TaskList.TASK_DESCRIPTION));
        }
        //close the cursor
        c.close();

        if(taskID!=null && !TextUtils.isEmpty(taskID)) {
            ((TextView)view.findViewById(com.example.rlam.ckl8_250_finalproject.R.id.textViewTaskName)).setText(taskName);
            ((TextView)view.findViewById(com.example.rlam.ckl8_250_finalproject.R.id.textViewTaskDescription)).setText(taskDescription);
        }

        return view;
    }
}
