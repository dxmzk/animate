package com.step3.animate.modules.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class RoomHelper(context: Context) {
    private var db: AppRoomDatabase
    init {
        db = Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java, "animate3"
        ).build()
    }

    fun getDB(): AppRoomDatabase {
        return db
    }
}