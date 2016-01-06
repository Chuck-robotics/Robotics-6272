package com.qualcomm.ftcrobotcontroller.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by root on 10/17/15.
 */
public class Accelerometer implements SensorEventListener {

    private SensorManager sensorManager;
    private long lastUpdate;
    private float xVal;
    private float yVal;
    private float zVal;
    private float accelationSquareRoot;

    public Accelerometer(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        xVal = values[0];
        yVal = values[1];
        zVal = values[2];

        accelationSquareRoot = (xVal * xVal + yVal * yVal + zVal * zVal)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;

        }
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public float getxVal() {
        return xVal;
    }

    public float getyVal() {
        return yVal;
    }

    public float getzVal() {
        return zVal;
    }

    public float getAccelationSquareRoot() {
        return accelationSquareRoot;
    }
}
