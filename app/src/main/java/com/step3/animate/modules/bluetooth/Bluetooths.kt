package com.bnq.pda3.module.bluetooth

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
import java.lang.reflect.Method

/**
 * Author: Meng
 * Date: 2022/09/05
 * Desc:
 *
 * //搜索开始的过滤器
IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//搜索结束的过滤器
IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//寻找到设备的过滤器
IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//绑定状态改变
IntentFilter filer4 = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//配对请求
IntentFilter filter5 = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);

获取绑定设备
BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
 */
class Bluetooths {

    companion object {
        val REQUEST_ENABLE_BT = 678

        @RequiresApi(Build.VERSION_CODES.M)
        fun init(context: AppCompatActivity) {
            val bluetoothManager: BluetoothManager =
                context.getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
            if (bluetoothAdapter != null) {
                if (!bluetoothAdapter.isEnabled) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    context.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)

                } else {
                    scanDevice(context, bluetoothAdapter)
                }
            }

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    when (intent.action) {
                        BluetoothDevice.ACTION_FOUND -> {
                            val device: BluetoothDevice? =
                                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                            val code = ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.BLUETOOTH_CONNECT
                            )
                            if (code == PackageManager.PERMISSION_GRANTED) {
                                val name = device?.name
                                val address = device?.address // MAC address
                                Log.i("Bluetooth List: ", "name: ${name}; address: ${address}")
                            }
                        }
                    }
                }
            }
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            context.registerReceiver(receiver, filter)

            Log.i("Bluetooth ", "end asda")
        }

        fun getDeviceList(bluetoothAdapter: BluetoothAdapter) {
            bluetoothAdapter.bluetoothLeScanner;
        }

        // 获取已配对蓝牙
        @RequiresApi(Build.VERSION_CODES.M)
        fun scanDevice(context: Context, bluetoothAdapter: BluetoothAdapter) {
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            val devices: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices

            if (devices.isNotEmpty()) { //存在已配对过的设备
                //利用for循环读取每一个设备的信息
                devices.forEach { device ->
                    val deviceName = device.name
                    val deviceAddress = device.address // MAC address
                    Log.i("Bluetooth Device: ", "name: ${deviceName}; address: $deviceAddress")
                }
            } else {
                //不存在已经配对的蓝牙设备
            }
        }

        /**
         * 配对蓝牙设备
         */
        private fun pinTargetDevice(context: Context, position: Int) {
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            //在配对之前，停止搜索
//            BluetoothAdapter.cancelDiscovery()
            //获取要匹配的BluetoothDevice对象，后边的deviceList是你本地存的所有对象
//            val device: BluetoothDevice = deviceList.get(position)
//            if (device.bondState != BluetoothDevice.BOND_BONDED) { //没配对才配对
//                try {
//                    Log.d("TAG", "开始配对...")
//                    val createBondMethod: Method =
//                        BluetoothDevice::class.java.getMethod("createBond")
//                    val returnValue = createBondMethod.invoke(device) as Boolean
//                    if (returnValue) {
//                        Log.d("TAG", "配对成功...")
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
        }

        /**
         * 取消配对（取消配对成功与失败通过广播返回 也就是配对失败）
         * @param device
         */
        fun cancelPinBule(device: BluetoothDevice) {
            Log.d("TAG", "attemp to cancel bond:" + device.name)
            try {
                val removeBondMethod = device.javaClass.getMethod("removeBond")
                val returnValue = removeBondMethod.invoke(device) as Boolean
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("TAG", "attemp to cancel bond fail!")
            }
        }
    }
}