package com.step3.animate.modules.database

import android.provider.BaseColumns

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc: 数据
 * 列表：list 加个缓存
 * - id
 * - name
 * - path
 * - icon
 * - fps
 * - memo
 *
 * 项目：animate
 * - id
 * - name       -项目名
 * - path       -项目路径
 * - icon       -封面图
 * - fps        -帧率
 * - memo       -备注
 * - count      -图片输了
 * - tag        -标记
 * - date       -创建日期
 * - status     -项目状态
 * - ratio      -长宽比
 * - alpha      -参考照片透明读
 *
 * 图片路径：photo
 * - id
 * - name       -图片名称
 * - path       -图片地址
 * - ratio      -长宽比
 * - aid        -项目id
 */

const val SQL_CREATE_PHOTO =
    "CREATE TABLE ${SQLiteEntry.Photo.Table} (" +
            "${SQLiteEntry.Photo.Id} INTEGER PRIMARY KEY," +
            "${SQLiteEntry.Photo.Aid} INTEGER,${SQLiteEntry.Photo.Ratio} INTEGER," +
            "${SQLiteEntry.Photo.Name} TEXT,${SQLiteEntry.Photo.Path} TEXT)"
const val SQL_DELETE_PHOTO = "DROP TABLE IF EXISTS ${SQLiteEntry.Photo.Table}"

const val SQL_CREATE_ANIM =
    "CREATE TABLE ${SQLiteEntry.Animate.Table} (" +
            "${SQLiteEntry.Animate.Id} INTEGER PRIMARY KEY," +
            "${SQLiteEntry.Animate.Fps} INTEGER,${SQLiteEntry.Animate.Ratio} INTEGER," +
            "${SQLiteEntry.Animate.Count} INTEGER,${SQLiteEntry.Animate.Tag} INTEGER," +
            "${SQLiteEntry.Animate.Status} INTEGER,${SQLiteEntry.Animate.Create} TEXT," +
            "${SQLiteEntry.Animate.Icon} TEXT,${SQLiteEntry.Animate.Memo} TEXT," +
            "${SQLiteEntry.Animate.Name} TEXT,${SQLiteEntry.Animate.Path} TEXT)"
const val SQL_DELETE_ANIM = "DROP TABLE IF EXISTS ${SQLiteEntry.Animate.Table}"

class SQLiteEntry {
    object User : BaseColumns {
        const val TABLE_NAME = "user"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_TITLE = "title"
    }

    object Animate {
        const val Id = "id"
        const val Name = "name"
        const val Path = "path"
        const val Icon = "icon"
        const val Fps = "fps"
        const val Memo = "memo"
        const val Count = "count"
        const val Tag = "tag"
        const val Create = "date"
        const val Status = "status"
        const val Alpha = "alpha"
        const val Ratio = "ratio"
        const val Table = "animate"
    }

    object Photo {
        const val Id = "id"
        const val Aid = "aid"
        const val Name = "name"
        const val Path = "path"
        const val Ratio = "ratio"
        const val Table = "photo"
    }
}