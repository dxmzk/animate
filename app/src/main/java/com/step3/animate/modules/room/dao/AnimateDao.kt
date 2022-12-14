package com.step3.animate.modules.room.dao

import androidx.room.*
import com.step3.animate.modules.room.entity.Animate

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */

@Dao
interface AnimateDao {
    @Query("SELECT * FROM animate ORDER BY id DESC")
    fun getAll(): List<Animate>

    @Query("SELECT * FROM animate WHERE id IN (:id)")
    fun findById(id: Int): Animate

    @Insert
    fun insert(anim: Animate): Long

//    @Insert
//    fun inserts(anim: Animate, anim2: Animate): Array<Long>

    @Update
    fun update(anim: Animate)

    @Delete
    fun delete(anim: Animate)
}