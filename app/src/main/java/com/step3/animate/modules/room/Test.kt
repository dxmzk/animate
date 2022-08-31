package com.step3.animate.modules.room

import android.content.Context

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class Test {

    fun getPhoto(context: Context) {
        val list = getOpenDb(context).photoDao().getAll();
    }

    fun getAnimate(context: Context) {
        val list = getOpenDb(context).animateDao().getAll();
    }
}