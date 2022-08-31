package com.step3.animate.modules.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */

@Entity(tableName = "photo")
data class Photo(
    val aid: Int,
    val name: String,
    val path: String,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
