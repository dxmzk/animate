package com.step3.animate.modules.store

import android.content.Context
import android.util.Log
import com.step3.animate.modules.room.AppDatabase
import com.step3.animate.modules.room.dao.AnimateDao
import com.step3.animate.modules.room.entity.Animate

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class AnimateStore {
    private val TAG = "AnimateStore"
    private var animDao: AnimateDao? = null

    constructor(context: Context) {
        animDao = AppDatabase.getInstance()?.animateDao()
    }

    fun getAll(): List<Animate> {
        val list = animDao?.getAll()
        list?.forEach {
            Log.i(TAG, it.toString())
        }
        return list!!
    }

    fun findById(id: Int): Animate {
        TODO("Not yet implemented")
    }

    fun insert(anim: Animate) {
        TODO("Not yet implemented")
    }

    fun delete(anim: Animate) {
        TODO("Not yet implemented")
    }
}