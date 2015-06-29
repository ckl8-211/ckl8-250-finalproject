package com.example.rlam.ckl8_250_finalproject;

import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.speech.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <p/>
 * This sample app shows master/detail flow by using fragments
 * <p/>
 * Main activity representing a list of AndroidVersions. This activity
 * has different presentations for handset and tablet-size devices.
 * - On handsets, the activity presents a list, which when touched,
 * leads to a details screen {@link TaskDetailActivity}
 * - On tablets, the activity presents the list and item details side-by-side
 * using two vertical panes: {@link TaiChiMoveFragment} and {@link TaskDetailFragment}
 * <p/>
 * This activity also implements the required {@link TaiChiMoveFragment.Callbacks} interface
 * to listen for item selections.
 */
public class MainActivity extends FragmentActivity {

//    private Handler mHandler = new android.os.Handler();
//    private CameraPreview mCameraPreview;
//    private boolean mMyTaichiShown;
//    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG = MainActivity.class.getSimpleName();
//    private SpeechRecognizer mSpeechCommand;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(com.example.rlam.ckl8_250_finalproject.R.layout.activity_main);

        if (savedInstanceState == null) {
            // set up animation interpolation
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            InterpolatorFragment fragment = new InterpolatorFragment();
            transaction.replace(R.id.taichi_interpolator_fragment, fragment);
            transaction.commit();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.example.rlam.ckl8_250_finalproject.R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        getSupportFragmentManager().beginTransaction()
//                .add(com.example.rlam.ckl8_250_finalproject.R.id.container, new TaiChiMoveFragment())
//                .commit();
//
//        switch(item.getItemId()) {
//            case com.example.rlam.ckl8_250_finalproject.R.id.menu_mytaichi:
//                mMyTaichiShown = !mMyTaichiShown;
//                ViewAnimator output = (ViewAnimator) findViewById(com.example.rlam.ckl8_250_finalproject.R.id.sample_output);
//                if (mMyTaichiShown) {
//                    output.setDisplayedChild(1);
//                } else {
//                    output.setDisplayedChild(0);
//                }
//                supportInvalidateOptionsMenu();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
