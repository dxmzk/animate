package com.step3.animate.modules.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class Sensor3 {

    companion object {

        fun getSensors(context: Context) {
            var sensorManager: SensorManager
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
            deviceSensors?.forEach { it ->
                sensorManager.registerListener(object : SensorEventListener {
                    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                        TODO("Not yet implemented")
                    }

                    override fun onSensorChanged(p0: SensorEvent?) {
                        TODO("Not yet implemented")
                    }
                }, it, SensorManager.SENSOR_DELAY_NORMAL)
            }
            // getDefaultSensor() 方法并传入特定传感器的类型常量，来确定设备上是否存在相关类型的传感器,
            // 如果设备上有多个特定类型的传感器，则必须将其中一个指定为默认传感器。如果没有指定默认传感器，则该方法调用会返回 null，这表示设备没有该类型的传感器
            if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
                // Success! There's a magnetometer.
            } else {
                // Failure! No magnetometer.
            }
        }
    }
}
