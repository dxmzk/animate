package com.step3.animate

import android.app.Application
import androidx.room.Transaction
import com.step3.animate.modules.database.AppSqlHelper
import com.step3.animate.modules.room.AppDatabase
import com.step3.animate.utils.AppExecutors

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class App : Application() {
    private lateinit var executors: AppExecutors
    override fun onCreate() {
        super.onCreate()
        executors = AppExecutors()
        initDB()
    }

    private fun getDatabase(): AppDatabase? {
        return AppDatabase.initDB(this, executors)
    }

    @Transaction
    private fun initDB() {
        getDatabase()
//        AppSqlHelper(this.applicationContext)
    }
}