package com.step3.animate.utils

import android.content.Context
import android.content.Intent
import android.hardware.Camera
import android.hardware.Camera.ACTION_NEW_PICTURE
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.net.toFile

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class Photo {
    private val TAG = "Photo"
    fun updateGallery(context: Context, imgUri: Uri) {

        // so if you only target API level 24+ you can remove this statement
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            context.sendBroadcast(Intent(Camera.ACTION_NEW_PICTURE, imgUri))
        }

        // If the folder selected is an external media directory, this is
        // unnecessary but otherwise other apps will not be able to access our
        // images unless we scan them using [MediaScannerConnection]
        val mimeType = MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(imgUri.toFile().extension)
        MediaScannerConnection.scanFile(
            context,
            arrayOf(imgUri.toFile().absolutePath),
            arrayOf(mimeType)
        ) { _, uri ->
            Log.d(TAG, "Image capture scanned into media store: $uri")
        }
    }
}