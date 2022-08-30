package com.step3.animate.modules.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class AppDbConnectHelper {
    private lateinit var dbHelper: DbOpenHelper
    private lateinit var db: SQLiteDatabase
    private var mode = 0 // 0读；1取

    constructor(context: Context){
        init(context)
    }

    fun init(context: Context) {
        dbHelper = DbOpenHelper(context)
        db = dbHelper.writableDatabase
    }

    private fun _rend() {
        db = dbHelper.readableDatabase
        changeMode(1)
    }

    private fun _writa() {
        db = dbHelper.readableDatabase
        changeMode(0)
    }

    private fun changeMode(num: Int) {
        mode = num
    }

    public fun close() {
        dbHelper.close()
    }

}