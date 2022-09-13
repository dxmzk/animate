package com.bnq.pda3.module.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

/**
 * Author: Meng
 * Date: 2022/09/05
 * Desc:
 *  TYPE_ACCELEROMETER	    	测量在所有三个物理轴向（x、y 和 z）上施加在设备上的加速力（包括重力），以 m/s2 为单位。	    动态检测（摇晃、倾斜等）。
    TYPE_AMBIENT_TEMPERATURE	硬件	以摄氏度 (°C) 为单位测量环境室温。请参见下面的备注。	监测气温。
    TYPE_GRAVITY	        	测量在所有三个物理轴向（x、y、z）上施加在设备上的重力，单位为 m/s2。	                    动态检测（摇晃、倾斜等）。
    TYPE_GYROSCOPE	        	测量设备在三个物理轴向（x、y 和 z）上的旋转速率，以 rad/s 为单位。	                    旋转检测（旋转、转动等）。
    TYPE_LIGHT	            	测量环境光级（照度），以 lx 为单位。	                                                控制屏幕亮度。
    TYPE_LINEAR_ACCELERATION	测量在所有三个物理轴向（x、y 和 z）上施加在设备上的加速力（不包括重力），以 m/s2 为单位。	监测单个轴向上的加速度。
    TYPE_MAGNETIC_FIELD	    	测量所有三个物理轴向（x、y、z）上的环境地磁场，以 μT 为单位。	                        创建罗盘。
    TYPE_ORIENTATION	    	测量设备围绕所有三个物理轴（x、y、z）旋转的度数。可以结合使用重力传感器、地磁场传感器和 getRotationMatrix() 方法来获取设备的倾角矩阵和旋转矩阵。	确定设备位置。
    TYPE_PRESSURE	        	测量环境气压，以 hPa 或 mbar 为单位。	                                            监测气压变化。
    TYPE_PROXIMITY	        	测量物体相对于设备显示屏幕的距离，以 cm 为单位。该传感器通常用于确定手机是否被举到人的耳边。	通话过程中手机的位置。
    TYPE_RELATIVE_HUMIDITY	   	测量环境的相对湿度，以百分比 (%) 表示。	                                            监测露点、绝对湿度和相对湿度。
    TYPE_ROTATION_VECTOR	   	通过提供设备旋转矢量的三个元素来检测设备的屏幕方向。                                  	动态检测和旋转检测。
    TYPE_TEMPERATURE	    	测量设备的温度，以摄氏度 (°C) 为单位。因设备而异。API 14 中，已被 TYPE_AMBIENT_TEMPERATURE 取代	监测温度。
 */
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
