package com.step3.animate.modules.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */

@Entity(tableName = "animate")
data class Animate(
    val name: String,
    val path: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var fps = 12
    var count = 0
    var ratio = 0
    var status = 0
    var tag = 0
    var icon = ""
    var desc = ""
    var memo = ""
    var date = SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.CHINA)
        .format(System.currentTimeMillis())
}