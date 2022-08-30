package com.step3.animate

import android.app.Application
import com.step3.animate.modules.database.AppDbConnectHelper

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
//        initDB()
    }

    private fun initDB() {
       AppDbConnectHelper(this.applicationContext)
    }
}