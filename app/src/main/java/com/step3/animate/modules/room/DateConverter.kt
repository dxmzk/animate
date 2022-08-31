package com.step3.animate.modules.room

import androidx.room.TypeConverter
import java.util.*

/**
 * Author: Meng
 * Date: 2022/08/31
 * Desc:
 */


object DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
