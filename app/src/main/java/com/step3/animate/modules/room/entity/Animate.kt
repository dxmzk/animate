package com.step3.animate.modules.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */

@Entity(tableName = "animate")
data class Animate(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

)