package com.step3.animate.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import androidx.appcompat.app.AppCompatActivity

/**
 * Author: Meng
 * Date: 2022/12/14
 * Desc:
 */
object USBUtil {
    private var mUsbReceiver: USBReceiver? = null

    private var usbDeviceAttachedListener: ((usbDevice: UsbDevice?, hasHidKeyBoardDevice: Boolean) -> Unit)? = null

    fun getUSBDeviceList(context: Context): HashMap<String, UsbDevice> {
        val usbManager: UsbManager =
            context.getSystemService(AppCompatActivity.USB_SERVICE) as UsbManager
        return usbManager.deviceList
    }

    fun hasHidKeyBoard(context: Context): Boolean {
        val deviceList = getUSBDeviceList(context)
        deviceList.forEach {
            if (it.value.productName == "HidKeyBoard") {
                return true
            }
        }
        return false
    }

    fun setUsbDeviceAttachedListener(l: (usbDevice: UsbDevice?, hasHidKeyBoardDevice: Boolean) -> Unit) {
        this.usbDeviceAttachedListener = l
    }

    fun registerReceiver(context: Context) {
        val filter = IntentFilter()
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        mUsbReceiver = USBReceiver()
        context.registerReceiver(mUsbReceiver, filter)
    }

    fun unregisterReceiver(context: Context) {
        if (mUsbReceiver != null) {
            context.unregisterReceiver(mUsbReceiver)
        }
    }

    private class USBReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // 这里可以拿到插入的USB设备对象
            val usbDevice: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
            when (intent.action) {
                UsbManager.ACTION_USB_DEVICE_ATTACHED,
                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    usbDeviceAttachedListener?.invoke(usbDevice, hasHidKeyBoard(context))
                }
            }
        }
    }

}