package com.step3.animate.modules.room

import android.content.Context
import androidx.room.Room

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */

public class RoomHelper(private val context: Context) {

    companion object {
        @Volatile
        private lateinit var instantce: RoomHelper
        private const val TABLE = "animate3.db"

        fun getInstance(context: Context): RoomHelper {
            if (instantce == null) {
                synchronized(AppDatabase::class.java) {
                    if (instantce == null) {
                        instantce = create(context)
                    }
                }
            }
            return instantce
        }

        private fun create(context: Context): RoomHelper {
            return RoomHelper(context)
        }
    }

    fun getDb(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, TABLE)
            .allowMainThreadQueries()
            .build()
    }
}
