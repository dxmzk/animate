package com.step3.animate.modules.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.step3.animate.modules.room.dao.AnimateDao
import com.step3.animate.modules.room.dao.PhotoDao
import com.step3.animate.modules.room.entity.Animate
import com.step3.animate.modules.room.entity.Photo

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */

@Database(entities = [Photo::class, Animate::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun animateDao(): AnimateDao
}