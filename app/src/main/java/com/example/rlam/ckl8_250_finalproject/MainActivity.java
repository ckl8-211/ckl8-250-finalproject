package com.example.rlam.ckl8_250_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.speech.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;
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
 * using two vertical panes: {@link MainFragment} and {@link TaskDetailFragment}
 * <p/>
 * This activity also implements the required {@link MainFragment.Callbacks} interface
 * to listen for item selections.
 */
public class MainActivity extends FragmentActivity {

    private boolean mMyTaichiShown;
    private static final String TAG = "MyTaichiListner";
    private SpeechRecognizer mSpeechCommand;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpeechCommand = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechCommand.setRecognitionListener(new listener());
        if (savedInstanceState == null) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplication().getPackageName());
            mSpeechCommand.startListening(intent);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            InterpolatorFragment fragment = new InterpolatorFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();

//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new MainFragment())
//                    .commit();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem myTaichi = menu.findItem(R.id.menu_mytaichi);
        myTaichi.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        myTaichi.setTitle(mMyTaichiShown ? R.string.hide_mytaichi : R.string.show_mytaichi);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new MainFragment())
                .commit();

        switch(item.getItemId()) {
            case R.id.menu_mytaichi:
                mMyTaichiShown = !mMyTaichiShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
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
        public void onBeginningOfSpeech()
        {
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
}
