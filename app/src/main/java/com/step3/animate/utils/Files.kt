package com.step3.animate.utils

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
            return "A${year}${month}${day}"
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
    }
}