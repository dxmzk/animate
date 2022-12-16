package com.step3.animate.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.*
import java.util.*


/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class Files {

    companion object {
        fun getProjectPath(): String {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)%100
            val month = formatNum(calendar.get(Calendar.MONTH) + 1)
            val day = formatNum(calendar.get(Calendar.DAY_OF_MONTH))
            val second = Math.random() * calendar.get(Calendar.SECOND) * (calendar.get(Calendar.HOUR)+10) * calendar.get(Calendar.MINUTE)
            return "A${year}${month}${day}${second.toInt()}"
        }

        fun getImgName(): String {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)%100
            val month = formatNum(calendar.get(Calendar.MONTH) + 1)
            val day = formatNum(calendar.get(Calendar.DAY_OF_MONTH))
            val hour = formatNum(calendar.get(Calendar.HOUR))
            val min = formatNum(calendar.get(Calendar.MINUTE))
            val second = formatNum(calendar.get(Calendar.SECOND))
            return "P${year}${month}${day}${hour}${min}${second}.jpg"
        }

        private fun formatNum(num: Int): String {
            return "${if(num > 9) "" else "0"}${num}"
        }


        fun saveBitmap(bm: Bitmap, context: Context, name: String?): String? {
            try {
                val f: File = context.getFileStreamPath(name)
                if (f.exists()) {
                    f.delete()
                }
                val out = FileOutputStream(f)
                bm.compress(Bitmap.CompressFormat.PNG, 90, out)
                out.flush()
                out.close()
                return f.getPath()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        fun copyFile(oldPath: String?, newPath: String?): Boolean {
            return try {
                val newFile = File(newPath)
                val oldFile = File(oldPath)
                val files = oldFile.list()
                var temp: File
                val fileInputStream = FileInputStream(oldFile)
                val fileOutputStream = FileOutputStream(newFile)
                val buffer = ByteArray(1024)
                var byteRead: Int
                while (fileInputStream.read(buffer).also { byteRead = it } != -1) {
                    fileOutputStream.write(buffer, 0, byteRead)
                }
                fileInputStream.close()
                fileOutputStream.flush()
                fileOutputStream.close()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}