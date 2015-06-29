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

    private Handler mHandler = new android.os.Handler();
    private CameraPreview mCameraPreview;
    private boolean mMyTaichiShown;
//    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG = "MyTaichiListner";
    private SpeechRecognizer mSpeechCommand;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(com.example.rlam.ckl8_250_finalproject.R.layout.activity_main);

        mCameraPreview = new CameraPreview(this);

        // setup voice input to get ready to take a snapshot
        mSpeechCommand = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechCommand.setRecognitionListener(new listener());
        if (savedInstanceState == null) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplication().getPackageName());
            mSpeechCommand.startListening(intent);
            FrameLayout fl = (FrameLayout) findViewById(R.id.taichi_camera_fragment);
            fl.addView(mCameraPreview);
            mCameraPreview.camera = Camera.open();
            // set up animation interpolation
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            InterpolatorFragment fragment = new InterpolatorFragment();
            transaction.replace(R.id.taichi_camera_fragment, fragment);
            transaction.commit();

            // fragment manager for showing the list of taichi moves in db
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new TaiChiMoveFragment())
//                    .commit();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.example.rlam.ckl8_250_finalproject.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem myTaichi = menu.findItem(com.example.rlam.ckl8_250_finalproject.R.id.menu_mytaichi);
        myTaichi.setVisible(findViewById(com.example.rlam.ckl8_250_finalproject.R.id.sample_output) instanceof ViewAnimator);
        myTaichi.setTitle(mMyTaichiShown ? com.example.rlam.ckl8_250_finalproject.R.string.hide_mytaichi : com.example.rlam.ckl8_250_finalproject.R.string.show_mytaichi);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        getSupportFragmentManager().beginTransaction()
                .add(com.example.rlam.ckl8_250_finalproject.R.id.container, new TaiChiMoveFragment())
                .commit();

        switch(item.getItemId()) {
            case com.example.rlam.ckl8_250_finalproject.R.id.menu_mytaichi:
                mMyTaichiShown = !mMyTaichiShown;
                ViewAnimator output = (ViewAnimator) findViewById(com.example.rlam.ckl8_250_finalproject.R.id.sample_output);
                if (mMyTaichiShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech(){

            mCameraPreview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
        }
        public void onError(int error)
        {
            Log.d(TAG, "error " + error);
        }
        public void onResults(Bundle results)
        {
            Log.d(TAG, "onResults " + results);
            ArrayList mVoiceInput = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < mVoiceInput.size(); i++)
            {
                Log.d(TAG, "result " + mVoiceInput.get(i));
            }
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
    /**
     * Release the resources when paused.
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (mCameraPreview.camera != null) {
            mCameraPreview.camera.release();
            mCameraPreview.camera = null;
        }
    }

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {

        public void onShutter() {
            Log.d(TAG, "shutterCallback:onShutter()");

            // Play a sound
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    MediaPlayer mp = MediaPlayer.create(MainActivity.this, com.example.rlam.ckl8_250_finalproject.R.raw.shutter);
                    mp.start();
                }
            };

            mHandler.postDelayed(r, 1000);
        }
    };

    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "rawCallback:onPictureTaken()");
        }
    };

    // PictureCallback to handle saving the picture to storage
    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "jpegCallback:onPictureTaken()");

            FileOutputStream fos = null;
            try {
                String fileName = String.format(Environment.getExternalStorageDirectory().getAbsolutePath() + "/%d.jpg", System.currentTimeMillis());
                fos = new FileOutputStream(fileName);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }

        }
    };

}
