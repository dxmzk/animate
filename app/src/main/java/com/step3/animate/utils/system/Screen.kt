package com.step3.animate.utils.system

import android.os.Build
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity

/**
 * Author: Meng
 * Date: 2022/12/16
 * Desc:
 */
class Screen {
    companion object {
        fun getSize(context: AppCompatActivity): Array<Int> {
//            val d: Display = context.windowManager.getDefaultDisplay()
            val metrics = DisplayMetrics()
            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.display
            } else {
                context.windowManager.defaultDisplay
            })?.getMetrics(metrics)

            var widthPixs = metrics.widthPixels
            var heightPixs = metrics.heightPixels

//            try {
//                val d: Display = context.windowManager.getDefaultDisplay()
//                widthPixs = Display::class.java.getMethod("getRawWidth").invoke(d) as Int
//                heightPixs = Display::class.java.getMethod("getRawHeight").invoke(d) as Int
//            } catch (e: Exception) {}
//            try {
//                val realSize = Point()
//                Display::class.java.getMethod("getRealSize", Point::class.java).invoke(d, realSize)
//                widthPixs = realSize.x
//                heightPixs = realSize.y
//            } catch (e: Exception) {}
            return arrayOf(widthPixs, heightPixs)
        }
    }
}