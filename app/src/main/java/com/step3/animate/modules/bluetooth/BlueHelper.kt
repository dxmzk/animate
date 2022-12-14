package com.bnq.pda3.modules.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

/**
 * Author: Meng
 * Date: 2022/09/05
 * Desc:
 */
class BlueHelper {

    companion object {
        val TAG = "BlueHelper"

        @RequiresApi(Build.VERSION_CODES.M)
        fun init(context: AppCompatActivity) {
            val bluetoothManager: BluetoothManager =
                context.getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
            if (bluetoothAdapter != null) {
                if (!bluetoothAdapter.isEnabled) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    context.startActivity(enableBtIntent)
                }
                val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
                pairedDevices?.forEach { device ->
                    val deviceName = device.name
                    val deviceAddress = device.address // MAC address
                    Log.i(TAG, "Bluetooth Device: name: ${deviceName}; address: $deviceAddress")
                }
            }

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    when (intent.action) {
                        BluetoothDevice.ACTION_FOUND -> {
                            val device: BluetoothDevice? =
                                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                            val code = ActivityCompat.checkSelfPermission(
                                context, Manifest.permission.BLUETOOTH_CONNECT
                            )
                            if (code == PackageManager.PERMISSION_GRANTED) {
                                val name = device?.name
                                val address = device?.address // MAC address
                                Log.i(TAG, "Bluetooth Device: name2: ${name}; address2: ${address}")
                            }
                        }
                    }
                }
            }
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            context.registerReceiver(receiver, filter)

            Log.i(TAG, "Bluetooth Manager init end")
        }
    }
}