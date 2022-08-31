package com.step3.animate.modules.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.step3.animate.modules.room.entity.Animate

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */

@Dao
interface AnimateDao {
    @Query("SELECT * FROM animate")
    fun getAll(): List<Animate>

    @Query("SELECT * FROM animate WHERE id IN (:id)")
    fun findById(id: Int): Animate

    @Insert
    fun insert(anim: Animate)

    @Delete
    fun delete(anim: Animate)
}