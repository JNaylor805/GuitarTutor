package com.example.james.guitartutor;

import android.app.Activity;

import android.os.Bundle;
import android.widget.TextView;


import java.io.IOException;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class GameActivity extends Activity {
float Pitch = 0;
    // @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // example pitch detection code.
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final float pitchInHz = result.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //guitar tuner
                        TextView text = (TextView) findViewById(R.id.testView);
                        text.setText("" + pitchInHz);

                            if (pitchInHz >= 108.00 && pitchInHz <= 112.00 ){
                               TextView test = (TextView) findViewById(R.id.textView1);
                               test.setText(" A ");

                           } else {
                                TextView test = (TextView) findViewById(R.id.textView1);
                                test.setText(" ? ");
                            }
                    }





                });
            }
        };

        AudioProcessor proc = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(proc);
        new Thread(dispatcher, "Audio Dispatcher").start();


        // end of example code.

    }
}


