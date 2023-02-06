package com.step3.animate.modules.bluetooth

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.util.*

/**
 * Author: Meng
 * Date: 2022/09/05
 * Desc:
 */
class ScanBlueTask : AsyncTask<BluetoothDevice?, Int?, BluetoothSocket?>() {
    private var bluetoothDevice: BluetoothDevice? = null
    private var callBack: ConnectBlueCallBack? = null
    private var TAG = ""
    fun ConnectBlueTask(callBack: ConnectBlueCallBack?) {
        this.callBack = callBack
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun doInBackground(vararg bluetoothDevices: BluetoothDevice?): BluetoothSocket? {
        bluetoothDevice = bluetoothDevices[0]
        var socket: BluetoothSocket? = null
        try {
            Log.d(TAG, "开始连接socket,uuid:00001101-0000-1000-8000-00805F9B34FB")
            socket =
                bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-1000-8000-00805F9B34FB"))
            if (socket != null && !socket.isConnected()) {
                socket.connect()
            }
        } catch (e: Exception) {
            Log.e(TAG, "socket连接失败")
            try {
                socket.close()
            } catch (e1: Exception) {
                e1.printStackTrace()
                Log.e(TAG, "socket关闭失败")
            }
        }
        return socket
    }

    override fun onPreExecute() {
        Log.d(TAG, "开始连接")
        if (callBack != null) callBack?.onStartConnect()
    }

    override fun onPostExecute(bluetoothSocket: BluetoothSocket?) {
        if (bluetoothSocket != null && bluetoothSocket.isConnected()) {
            Log.d(TAG, "连接成功")
            if (callBack != null) callBack?.onConnectSuccess(bluetoothDevice, bluetoothSocket)
        } else {
            Log.d(TAG, "连接失败")
            if (callBack != null) callBack?.onConnectFail(bluetoothDevice, "连接失败")
        }
    }
}

interface ConnectBlueCallBack {
    fun onStartConnect()

    fun onConnectFail(bluetoothDevice: BluetoothDevice?, msg: String)

    fun onConnectSuccess(bluetoothDevice: BluetoothDevice?, bluetoothSocket: BluetoothSocket)
}