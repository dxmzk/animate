package com.step3.animate.modules.store

import android.content.Context
import android.util.Log
import com.step3.animate.modules.room.AppDatabase
import com.step3.animate.modules.room.dao.PhotoDao
import com.step3.animate.modules.room.entity.Photo

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class PhotoStore {
    private val TAG = "PhotoStore"
    private var photoDao: PhotoDao? = null

    constructor(context: Context) {
        photoDao = AppDatabase.getInstance()?.photoDao()
    }

    fun getAll(): List<Photo> {
        val list = photoDao?.getAll()
        return list ?: ArrayList<Photo>()
    }

    fun findById(id: Int) {
        TODO("Not yet implemented")
    }

    fun findByAnimId(aid: Int): List<Photo>? {
        return photoDao?.findByAnimId(aid)
    }

    fun insert(photo: Photo) {
        photoDao?.insert(photo)
    }

    fun delete(photo: Photo) {
        TODO("Not yet implemented")
    }
}