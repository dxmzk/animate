package com.step3.animate.modules.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.room.Transaction
import java.util.*

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class AppSqlHelper {
    private val TAG = "AppSqlHelper"
    private lateinit var dbHelper: SqlOpenHelper
    private lateinit var db: SQLiteDatabase
    private var mode = 0 // 0读；1取

    constructor(context: Context) {
        init(context)
    }

    fun init(context: Context) {
        dbHelper = SqlOpenHelper(context)
        db = dbHelper.writableDatabase

        testDb()
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

    @Transaction
    private fun testDb() {
        val values = ContentValues().apply {
            put(SQLiteEntry.Photo.Name, "title-${System.currentTimeMillis()}")
            put(SQLiteEntry.Photo.Path, "path-${System.currentTimeMillis()}")
            put(SQLiteEntry.Photo.Aid, Math.round(Math.random() * 100))
        }
        val newRowId = db.insert(SQLiteEntry.Photo.Table, "", values)
        Log.i(TAG, newRowId.toString())

        val projection =
            arrayOf(SQLiteEntry.Photo.Aid, SQLiteEntry.Photo.Name, SQLiteEntry.Photo.Path)
        val sort = "${SQLiteEntry.Photo.Name} DESC"
        val cursor = db.query(SQLiteEntry.Photo.Table, projection, "", arrayOf(), null, null, sort)
        with(cursor) {
            while (moveToNext()) {
                val aid = getString(getColumnIndexOrThrow(SQLiteEntry.Photo.Aid))
                val nameStr = getString(getColumnIndexOrThrow(SQLiteEntry.Photo.Name))
                val pathStr = getString(getColumnIndexOrThrow(SQLiteEntry.Photo.Path))
                Log.i(TAG, "aid: ${aid},name: ${nameStr},path: ${pathStr}")
            }
        }
        cursor.close()
    }

}