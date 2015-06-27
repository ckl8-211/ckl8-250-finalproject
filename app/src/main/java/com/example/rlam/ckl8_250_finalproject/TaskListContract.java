package com.example.rlam.ckl8_250_finalproject;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Margaret on 2/19/2015.
 *
 * Contains static classes to define table schema etc.
 */
public class TaskListContract {

    // Name of the Content Provider, use package name by convention so that it's unique on device
    public static final String CONTENT_AUTHORITY = "com.example.rlam.ckl8_250_finalproject";

    // A path that points to the version table
    public static final String PATH_TASK_LIST = "task_list";

    // Construct the Base Content Uri
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Define the Version table
     */
    public static final class TaskList implements BaseColumns {

        // Content Uri = Content Authority + Path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASK_LIST).build();

        // Use MIME type prefix android.cursor.dir/ for returning multiple items
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/com.example.rlam.ckl8_250_finalproject.task_lists";
        // Use MIME type prefix android.cursor.item/ for returning a single item
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/com.example.rlam.ckl8_250_finalproject.task_list";

        // Define table name
        public static final String TABLE_NAME = "task_list";

        // Define table columns
        public static final String ID = BaseColumns._ID;
        public static final String TASK_NAME = "task_name";
        public static final String TASK_DESCRIPTION = "task_description";

        // Define projection for Version table
        public static final String[] PROJECTION = new String[] {
                /*0*/ TaskList.ID,
                /*1*/ TaskList.TASK_NAME,
                /*2*/ TaskList.TASK_DESCRIPTION
        };
    }

}
