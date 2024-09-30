package com.example.mfit.services

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.mfit.utils.Helper
import com.example.mfit.utils.Prefs
import com.example.mfit.interfaces.stepsCallback
import kotlin.math.roundToInt

class StepDetectorService : Service(), SensorEventListener {

    companion object {
        var callback: stepsCallback? = null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val sensorManager: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(countSensor != null){
            Toast.makeText(this, "Step Detecting Start", Toast.LENGTH_SHORT).show()
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL)

            callback?.subscribeSteps(Prefs["FSteps"])

        }else{
            Toast.makeText(this, "Sensor Not Detected", Toast.LENGTH_SHORT).show()
        }

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (Prefs["TodayDate", ""] != Helper.getDisplayDate()) {
            Prefs["Steps"] = p0!!.values[0].roundToInt()
            Prefs["TodayDate"] = Helper.getDisplayDate()
        } else {
            val storeSteps = Prefs["Steps", 0];
            val sensorSteps = p0!!.values[0].roundToInt()
            val finalSteps = sensorSteps - storeSteps
            if (finalSteps > 0) {
                Prefs["FSteps"] = finalSteps
                callback?.subscribeSteps(finalSteps)
            }
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.d("SERVICE", p0.toString())
    }

    object subscribe {
        fun register(activity: Activity) {
            callback = activity as stepsCallback
        }
    }

}