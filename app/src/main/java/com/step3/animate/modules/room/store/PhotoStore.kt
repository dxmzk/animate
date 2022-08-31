package com.step3.animate.modules.room.store

import android.content.Context
import android.util.Log
import com.step3.animate.modules.room.AppDatabase
import com.step3.animate.modules.room.RoomHelper
import com.step3.animate.modules.room.entity.Photo

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class PhotoStore {
    private val TAG = "PhotoStore"
    private var db: AppDatabase? = null

    constructor(context: Context) {
        db = AppDatabase.getInstance()
    }

    fun getAll() {
        val list = db?.photoDao()?.getAll()
        list?.forEach {
            Log.i(TAG, it.toString())
        }
    }

    fun findById(id: Int) {
        TODO("Not yet implemented")
    }

    fun findByAnimId(aid: Int) {
        TODO("Not yet implemented")
    }

    fun insert(photo: Photo) {

    }

    fun delete(photo: Photo) {
        TODO("Not yet implemented")
    }
}