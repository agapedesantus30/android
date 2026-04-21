package edu.ggc.lutz.updown;

import android.content.res.AssetFileDescriptor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    float[] gravity = new float[3];
    float[] accel = new float[3];
    private static final float ALPHA = 0.80f; // weighting factor used by the low pass filter
    private static final String TAG = "OMNI";
    private static final float VERTICAL_TOL = 0.3f;
    private SensorManager manager;
    private long lastUpdate;
    private MediaPlayer popPlayer;
    private MediaPlayer backgroundPlayer;
    private TextToSpeech tts;
    private TextView[] tvGravity;
    private TextView[] tvAcceleration;

    private boolean isDown = false;
    private boolean isUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        popPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("pop.ogg");
            popPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            popPlayer.prepare();
            afd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        backgroundPlayer = MediaPlayer.create(this, R.raw.mistsoftime4tmono);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
            }
        });

        tvGravity = new TextView[3];
        tvGravity[0] = findViewById(R.id.tvGravityX);
        tvGravity[1] = findViewById(R.id.tvGravityY);
        tvGravity[2] = findViewById(R.id.tvGravityZ);

        tvAcceleration = new TextView[3];
        tvAcceleration[0] = findViewById(R.id.tvAccelX);
        tvAcceleration[1] = findViewById(R.id.tvAccelY);
        tvAcceleration[2] = findViewById(R.id.tvAccelZ);

    }

    @Override
    protected void onResume() {
        super.onResume();

        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
        backgroundPlayer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
        backgroundPlayer.pause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        gravity[0] = lowPass(event.values[0], gravity[0]);
        gravity[1] = lowPass(event.values[1], gravity[1]);
        gravity[2] = lowPass(event.values[2], gravity[2]);

        accel[0] = highPass(event.values[0], gravity[0]);
        accel[1] = highPass(event.values[1], gravity[1]);
        accel[2] = highPass(event.values[2], gravity[2]);

        long actualTime = System.currentTimeMillis();

        if (actualTime - lastUpdate > 1000) {

            Log.i(TAG, "gravity[]=" + gravity[0] + ' ' + gravity[1] + ' ' + gravity[2]);
            tvGravity[0].setText(String.format("%.3f", gravity[0]));
            tvGravity[1].setText(String.format("%.3f", gravity[1]));
            tvGravity[2].setText(String.format("%.3f", gravity[2]));

            tvAcceleration[0].setText(String.format("%.3f", accel[0]));
            tvAcceleration[1].setText(String.format("%.3f", accel[1]));
            tvAcceleration[2].setText(String.format("%.3f", accel[2]));


            if (inRange(gravity[2], -9.81f, VERTICAL_TOL)) {

                Log.i(TAG, "Down");

                if (!isDown) {
                    backgroundPlayer.setVolume(0.1f, 0.1f);
                    popPlayer.start();
                    tts.speak("Hello I tech forty five fifty, I am pointing down", TextToSpeech.QUEUE_FLUSH, null, null);
                    backgroundPlayer.setVolume(1.0f, 1.0f);
                    isDown = true;
                    isUp = false;
                }

            } else if (inRange(gravity[2], 9.81f, VERTICAL_TOL)) {
                if (!isUp) {
                    backgroundPlayer.setVolume(0.1f, 0.1f);
                    Log.i(TAG, "Up");
                    tts.speak("up", TextToSpeech.QUEUE_FLUSH, null, null);
                    backgroundPlayer.setVolume(1.0f, 1.0f);
                    isUp = true;
                    isDown = false;
                }

            } else {
                Log.i(TAG, "In between");
                //isDown = false;  // Rubbish!
                //isUp = false;
            }
            lastUpdate = actualTime;
        }

    }

    private boolean inRange(float value, float target,  float tol) {
        return value >= target-tol && value <= target+tol;
    }

    // de-emphasize transient forces
    private float lowPass(float current, float gravity) {
        return current * (1-ALPHA) + gravity * ALPHA; // ALPHA indicates the influence of past observations
    }

    // de-emphasize constant forces
    private float highPass(float current, float gravity) {
        return current - gravity;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
