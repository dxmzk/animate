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
        return list ?: ArrayList<Animate>()
    }

    fun findById(id: Int): Animate {
        val anim = animDao?.findById(id)
        return anim!!
    }

    fun insert(anim: Animate) {
        Log.i(TAG, anim.toString())
        animDao?.insert(anim)
    }

    fun update(anim: Animate) {
        Log.i(TAG, anim.toString())
        animDao?.update(anim)
    }

    fun delete(anim: Animate) {
        animDao?.delete(anim)
    }
}