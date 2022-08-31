package com.step3.animate.modules.room

import android.content.Context

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class Test {

    fun getPhoto(context: Context) {
        val list = AppDatabase.getInstance()!!.photoDao().getAll();
    }

    fun getAnimate(context: Context) {
        val list = AppDatabase.getInstance()!!.animateDao().getAll();
    }
}