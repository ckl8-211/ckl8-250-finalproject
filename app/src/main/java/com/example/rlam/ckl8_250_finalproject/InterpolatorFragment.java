/*
* Copyright 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.rlam.ckl8_250_finalproject;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This sample demonstrates the use of animation interpolators and path animations for
 * Material Design.
 * It shows how an {@link ObjectAnimator} is used to animate two properties of a
 * view (scale X and Y) along a path.
 */
public class InterpolatorFragment extends Fragment {

    private Handler mHandler = new android.os.Handler();
    private static final String TAG = "MyTaichiListner";
    /**
     * View that is animated.
     */
    private View mView;
    /**
     * Spinner for selection of interpolator.
     */
    /**
     * SeekBar for selection of duration of animation.
     */
    private SeekBar mDurationSeekbar;
    /**
     * TextView that shows animation selected in SeekBar.
     */
    private TextView mDurationLabel;

    /**
     * Interpolators used for animation.
     */
    private Interpolator mInterpolators[];
    /**
     * Path for in (shrinking) animation, from 100% scale to 20%.
     */
    private Path mPathIn;
    /**
     * Path for out (growing) animation, from 20% to 100%.
     */
    private Path mPathOut;

    /**
     * Set to true if View is animated out (is shrunk).
     */
//    private boolean mIsOut = false;

    /**
     * Default duration of animation in ms.
     */
    private static final int INITIAL_DURATION_MS = 750;

    /**
     * String used for logging.
     */


    private SpeechRecognizer mSpeechCommand;
    private CameraPreview mCameraPreview;

    public InterpolatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the fragment_animation layout
        View v = inflater.inflate(com.example.rlam.ckl8_250_finalproject.R.layout.interpolator_fragment, container, false);
       // Initialize Interpolators programmatically by loading them from their XML definitions
        // provided by the framework.
        mInterpolators = new Interpolator[]{
                new AnimationUtils().loadInterpolator(getActivity(),
                        android.R.interpolator.linear),
                new AnimationUtils().loadInterpolator(getActivity(),
                        android.R.interpolator.fast_out_linear_in),
                new AnimationUtils().loadInterpolator(getActivity(),
                        android.R.interpolator.fast_out_slow_in),
                new AnimationUtils().loadInterpolator(getActivity(),
                        android.R.interpolator.linear_out_slow_in)
        };
        mSpeechCommand = SpeechRecognizer.createSpeechRecognizer(getActivity());

        // camera preview view surface
        mCameraPreview = new CameraPreview(getActivity());
        FrameLayout fl = (FrameLayout) v.findViewById(R.id.taichi_preview);
        fl.addView(mCameraPreview);
        mCameraPreview.camera = Camera.open();
        // setup voice input to get ready to take a snapshot
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getActivity().getApplication().getPackageName());
        mSpeechCommand.startListening(intent);
        mSpeechCommand.setRecognitionListener(new listener());
        // Set up the 'animate' button, when it is clicked the view is animated with the options
        // selected: the Interpolator, duration and animation path
        Button button = (Button) v.findViewById(com.example.rlam.ckl8_250_finalproject.R.id.animateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                TaiChiMoveFragment fragment = new TaiChiMoveFragment();
                transaction.replace(R.id.taichi_interpolator_fragment, fragment);
                transaction.commit();
            }
        });

        // Get the label to display the selected duration
        mDurationLabel = (TextView) v.findViewById(com.example.rlam.ckl8_250_finalproject.R.id.durationLabel);


        // Load names of interpolators from a resource

        // Set up SeekBar that defines the duration of the animation
        mDurationSeekbar = (SeekBar) v.findViewById(com.example.rlam.ckl8_250_finalproject.R.id.durationSeek);

        // Register listener to update the text label when the SeekBar value is updated
        mDurationSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mDurationLabel.setText(getResources().getString(com.example.rlam.ckl8_250_finalproject.R.string.animation_duration, i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Set initial progress to trigger SeekBarChangeListener and update UI
        mDurationSeekbar.setProgress(INITIAL_DURATION_MS);

        // Get the view that will be animated
        mView = v.findViewById(com.example.rlam.ckl8_250_finalproject.R.id.purple_square);

        // The following Path definitions are used by the ObjectAnimator to scale the view.

        // Path for 'in' animation: growing from 20% to 100%
        mPathIn = new Path();
        mPathIn.moveTo(0.2f, 0.2f);
        mPathIn.lineTo(1f, 1f);

        // Path for 'out' animation: shrinking from 100% to 20%
        mPathOut = new Path();
        mPathOut.moveTo(1f, 1f);
        mPathOut.lineTo(0.2f, 0.2f);
        return v;
    }

    /**
     * Start an animation on the sample view.
     * The view is animated using an {@link ObjectAnimator} on the
     * {@link View#SCALE_X} and {@link View#SCALE_Y} properties, with its animation based on a path.
     * The only two paths defined here ({@link #mPathIn} and {@link #mPathOut}) scale the view
     * uniformly.
     *
     * @param interpolator The interpolator to use for the animation.
     * @param duration     Duration of the animation in ms.
     * @param path         Path of the animation
     * @return The ObjectAnimator used for this animation
     * @see ObjectAnimator#ofFloat(Object, String, String, Path)
     */
    public ObjectAnimator startAnimation(Interpolator interpolator, long duration, Path path) {
        // This ObjectAnimator uses the path to change the x and y scale of the mView object.
        ObjectAnimator animator = ObjectAnimator.ofFloat(mView, View.SCALE_X, View.SCALE_Y, path);

        // Set the duration and interpolator for this animation
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);

        animator.start();

        return animator;
    }

    /**
     * Return the array of loaded Interpolators available in this Fragment.
     *
     * @return Interpolators
     */
    public Interpolator[] getInterpolators() {
        return mInterpolators;
    }

    /**
     * Return the animation path for the 'in' (shrinking) animation.
     *
     * @return
     */
    public Path getPathIn() {
        return mPathIn;
    }

    /**
     * Return the animation path for the 'out' (growing) animation.
     *
     * @return
     */
    public Path getPathOut() {

        return mPathOut;
    }
    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech(){
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
//            mCameraPreview.camera.takePicture(shutterCallback, rawCallback, postviewCallback, jpegCallback);
            // start taking picture when speech ends
            // Duration selected in SeekBar
            long duration = mDurationSeekbar.getProgress();
            // Animation path is based on whether animating in or out
//                Path path = mIsOut ? mPathIn : mPathOut;

            // Start the animation with the selected options
            startAnimation(mInterpolators[1], duration, mPathIn);
//            mCameraPreview.camera.takePicture(shutterCallback, rawCallback, postviewCallback, jpegCallback);
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
    public void onPause() {
        super.onPause();

        if (mCameraPreview.camera != null) {
            mCameraPreview.camera.release();
            mCameraPreview.camera = null;
        }
    }

    public Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {

        public void onShutter() {
            Log.d(TAG, "shutterCallback:onShutter()");

            // Play a sound
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.shutter) ;
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
    // PictureCallback to handle postview
    Camera.PictureCallback postviewCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "postviewCallback:onPictureTaken()");

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