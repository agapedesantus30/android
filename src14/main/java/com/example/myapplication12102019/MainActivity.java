package edu.ggc.lutz.findthebugandroid;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivityFindBug extends Activity implements SensorEventListener {

    SensorManager mgr;
    mgr = (SensorManager) getSystemService(SENSOR_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void resume() {
        super.onResume();
        Sensor sensor = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mgr.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void pause() {
        super.onPause();
        mgr.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        Log.wtf("ReadAccel", "event values = " +
                x + " " + y + " " + z);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}